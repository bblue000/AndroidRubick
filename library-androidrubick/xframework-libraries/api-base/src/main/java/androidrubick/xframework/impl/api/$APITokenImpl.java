package androidrubick.xframework.impl.api;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.google.gson.internal.$Gson$Types;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import org.apache.http.HttpStatus;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.util.Map;

import androidrubick.net.HttpMethod;
import androidrubick.xbase.annotation.Configurable;
import androidrubick.xbase.util.JsonParser;
import androidrubick.xframework.api.APICallback;
import androidrubick.xframework.api.APIConstants;
import androidrubick.xframework.api.APIToken;
import androidrubick.xframework.impl.api.annotation.NoneAPIResponse;
import androidrubick.xframework.impl.api.param.$APIParamParser;
import androidrubick.xframework.impl.api.result.APITransformer;
import androidrubick.xframework.impl.api.result.BaseResult;
import androidrubick.xframework.impl.api.result.TransformException;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/15.
 */
public class $APITokenImpl implements APIToken {

    private final OkHttpClient mClient;
    private final Class mResultClz;
    private final APICallback mCallback;
    private final Request mRequest;
    private CallbackImpl mInitErrCallback;

    private Call mCall;
    public $APITokenImpl(String url, HttpMethod method,
                         Object param, Map<String, String> extraHeaders,
                         Class<?> resultClz,
                         APICallback<?> callback) {
        this.mClient = APIConstants.CLIENT;
        this.mResultClz = resultClz;
        this.mCallback = callback;

        // 创建时的错误
        Request request = null;
        try {
            request = $APIParamParser.parse(url, method, param, extraHeaders);
        } catch (Throwable e) {
            mInitErrCallback = new CallbackImpl(mCallback, null);
            mInitErrCallback.status = createFailAPIStatus(null, e, $APIStatusImpl.E_CLIENT);
        }
        this.mRequest = request;
    }

    /**
     * 是否是HTTP请求成功的状态
     */
    @Configurable
    private boolean isSuccessHttpResponse(Response response) {
        // 判断是不是200
        return HttpStatus.SC_OK == response.code();
    }

    /**
     * 是否是API请求成功的状态
     */
    @Configurable
    private boolean isSuccessAPIResponse(BaseResult<?> result) {
        return null != result && HttpStatus.SC_OK == result.code;
    }

    /**
     * 该处为了避免重复请求，如果正在处理请求则直接返回
     */
    @Override
    public synchronized boolean execute() {
        if (isIdle()) {
            executeInner();
            return true;
        }
        return false;
    }

    @Override
    public synchronized boolean isIdle() {
        return null == mCall || mCall.isCanceled();
    }

    @Override
    public synchronized boolean cancel(boolean ignoreCallback) {
        if (null == mCall) {
            return true;
        }
        mCall.cancel();
        restJob(mCall);
        return true;
    }

    private void executeInner() {
        if (null != mInitErrCallback) {
            sHandler.obtainMessage(FAILED, mInitErrCallback).sendToTarget();
            return;
        }
        final Call call = mCall = mClient.newCall(mRequest);
        call.enqueue(new CallbackImpl(mCallback, call));
    }

    // 成功请求到API结果，下一步转换数据
    private synchronized void performTransform(CallbackImpl callback, Response response) {
        Object data;
        APITransformer transformer = APITransformer.getTransformer(mResultClz);
        if (null != transformer) {
            try {
                data = transformer.transform(response);
            } catch (Throwable e) {
                performFailResult(callback, new TransformException("transform err", e));
                return;
            }
        } else if (mResultClz.isAnnotationPresent(NoneAPIResponse.class)) {
            try {
                ResponseBody body = response.body();
                data = JsonParser.toObject(body.string(), mResultClz);
            } catch (Throwable e) {
                performFailResult(callback, new TransformException("transform err", e));
                return;
            }
        } else {
            Type type = $Gson$Types.newParameterizedTypeWithOwner(null, BaseResult.class, mResultClz);
            try {
                ResponseBody body = response.body();
                data = JsonParser.toObject(body.string(), type);
            } catch (Throwable e) {
                performFailResult(callback, new TransformException("transform err", e));
                return;
            }
            BaseResult<?> result = (BaseResult<?>) data;
            if (null != result && !isSuccessAPIResponse(result)) {
                callback.status = new $APIStatusImpl(result.code, response.code(), result.msg);
                sHandler.obtainMessage(FAILED, callback).sendToTarget();
                return;
            }
        }

        callback.data = data;
        sHandler.obtainMessage(SUCCESS, callback).sendToTarget();
    }

    /**
     * 处理失败的API响应，区分类型
     */
    private void performErrResponse(CallbackImpl callback, Response response) {
        int code = response.code();
        int detailCode = code;
        String msg = response.message();
        if (code < 0) {
            code = $APIStatusImpl.E_BAD_NETWORK;
        } else if (code == HttpStatus.SC_FORBIDDEN || code == HttpStatus.SC_UNAUTHORIZED) {
            code = $APIStatusImpl.E_AUTH;
        } else if (code >= 500) {
            code = $APIStatusImpl.E_SERVER;
        }
        callback.status = new $APIStatusImpl(code, detailCode, msg);
        sHandler.obtainMessage(FAILED, callback).sendToTarget();
    }

    private void performFailResult(CallbackImpl callback, Throwable e) {
        callback.status = createFailAPIStatus(callback.call, e, $APIStatusImpl.E_BAD_NETWORK);
        sHandler.obtainMessage(FAILED, callback).sendToTarget();
    }

    /**
     * @param defCode 默认的错误码
     */
    private $APIStatusImpl createFailAPIStatus(Call call, Throwable e, int defCode) {
        int code = defCode;
        String message = e.getMessage();
        // 不是IO 异常，那么就归结为客户端的错误
        if (null == mRequest || !(e instanceof IOException)
                || (e instanceof MalformedURLException)
                || e instanceof TransformException) {
            code = $APIStatusImpl.E_CLIENT;
        }

        // TODO 考虑一下是否要打开此处代码
//        if (e instanceof SocketTimeoutException || e instanceof ConnectTimeoutException) {
//            code = $APIStatusImpl.E_BAD_NETWORK;
//        }

        if (null != call && call.isCanceled()) {
            code = $APIStatusImpl.E_CANCEL;
        }
        return new $APIStatusImpl(code, message);
    }

    private synchronized void restJob(Call call) {
        if (call == mCall) {
            mCall = null;
        }
    }

    private class CallbackImpl implements Callback {
        public APICallback callback;
        public Call call;
        public $APIStatusImpl status;
        public Object data;

        private CallbackImpl(APICallback callback, Call call) {
            this.callback = callback;
            this.call = call;
        }

        @Override
        public void onFailure(Request request, IOException e) {
            performFailResult(this, e);
            restJob(call);
        }

        @Override
        public void onResponse(Response response) throws IOException {
            if (isSuccessHttpResponse(response)) {
                // 如果请求成功，则转换数据
                performTransform(this, response);
            } else {
                performErrResponse(this, response);
            }
            restJob(this.call);
        }
    }

    // internal
    private static final int SUCCESS = 1;
    private static final int FAILED = 2;
    private static final Handler sHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            CallbackImpl token = (CallbackImpl) msg.obj;
            switch (msg.what) {
                case SUCCESS:
                    if (null != token.callback) {
                        token.callback.onSuccess(token.data);
                    }
                    break;
                case FAILED:
                    if (null != token.callback) {
                        token.callback.onFailed(token.status);
                    }
                    break;
            }
        }
    };

}
