package androidrubick.xframework.impl.api;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.google.gson.internal.$Gson$Types;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import org.apache.http.HttpStatus;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.Map;

import androidrubick.net.HttpMethod;
import androidrubick.xbase.annotation.Configurable;
import androidrubick.xbase.util.JsonParser;
import androidrubick.xbase.util.XLog;
import androidrubick.xframework.api.APIConstants;
import androidrubick.xframework.api.XAPICallback;
import androidrubick.xframework.api.XAPIHolder;
import androidrubick.xframework.app.XGlobals;
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
public class $APIHolderImpl implements XAPIHolder {

    private static final String TAG = $APIHolderImpl.class.getSimpleName();

    private Request mRequest;
    private Class mResultClz;
    private XAPICallback mCallback;
    private Throwable mInitErr;
    private Call mCall;
    public $APIHolderImpl(String url, HttpMethod method,
                          Object param, Map<String, String> extraHeaders,
                          Class<?> resultClz,
                          XAPICallback<?> callback) {
        this.mResultClz = resultClz;
        this.mCallback = callback;

        // 创建时的错误
        try {
            this.mRequest = $APIParamParser.parse(url, method, param, extraHeaders);
        } catch (Throwable e) {
            mInitErr = e;
        }
    }

    /**
     * 是否是HTTP请求成功的状态
     */
    @Configurable
    private boolean isSuccessHttpResponse(Response response) {
        // 判断是不是200
        return HttpURLConnection.HTTP_OK == response.code();
    }

    /**
     * 是否是API请求成功的状态
     */
    @Configurable
    private boolean isSuccessAPIResponse(BaseResult<?> result) {
        return null != result && HttpURLConnection.HTTP_OK == result.code;
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
        if (XGlobals.DEBUG) {
            XLog.d(TAG, "XAPIHolder@" + Integer.toHexString(hashCode()) + "#execute not idle");
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
        if (null != mInitErr) {
            performResult(null, null, mInitErr);
            return;
        }
        final Call call = mCall = APIConstants.CLIENT.newCall(mRequest);
        call.enqueue(new CallbackImpl(mCallback, call));
    }

    // 转换数据
    private synchronized void performTransform(CallbackImpl callback, Response response) {
        Object data;
        APITransformer transformer = APITransformer.getTransformer(mResultClz);
        if (null != transformer) {
            try {
                data = transformer.transform(response);
            } catch (Throwable e) {
                performResult(callback, response, new TransformException("transform err", e));
                return;
            }
        } else if (mResultClz.isAnnotationPresent(NoneAPIResponse.class)) {
            try {
                ResponseBody body = response.body();
                data = JsonParser.toObject(body.string(), mResultClz);
            } catch (Throwable e) {
                performResult(callback, response, new TransformException("transform err", e));
                return;
            }
        } else {
            Type type = $Gson$Types.newParameterizedTypeWithOwner(null, BaseResult.class, mResultClz);
            try {
                ResponseBody body = response.body();
                data = JsonParser.toObject(body.string(), type);
            } catch (Throwable e) {
                performResult(callback, response, new TransformException("transform err", e));
                return;
            }
            BaseResult<?> result = (BaseResult<?>) data;
            if (null != result && !isSuccessAPIResponse(result)) {
                callback.status = new $APIStatusImpl(result.code, result.msg);
                sHandler.obtainMessage(FAILED, callback).sendToTarget();
                return;
            }
        }

        callback.data = data;
        sHandler.obtainMessage(SUCCESS, callback).sendToTarget();
    }

    private synchronized void performResult(CallbackImpl callback, Response response, Throwable e) {
        int code = $APIStatusImpl.E_BAD_NETWORK;
        String message = e.getMessage();
        // 不是IO 异常，那么就归结为客户端的错误
        if (null == mRequest || !(e instanceof IOException)
                || (e instanceof MalformedURLException)
                || e instanceof TransformException) {
            code = $APIStatusImpl.E_CLIENT;
        }

//            if (e instanceof SocketTimeoutException || e instanceof ConnectTimeoutException) {
//                code = $APIStatusImpl.E_BAD_NETWORK;
//            }

        if (callback.call.isCanceled()) {
            code = $APIStatusImpl.E_CANCEL;
        }

        callback.status = new $APIStatusImpl(code, message);
        sHandler.obtainMessage(FAILED, callback).sendToTarget();
    }

    private void performErrResponse(CallbackImpl callback, Response response) {
        int code = response.code();
        if (code < 0) {
            code = $APIStatusImpl.E_BAD_NETWORK;
        } else if (code == HttpStatus.SC_FORBIDDEN || code == HttpStatus.SC_UNAUTHORIZED) {
            code = $APIStatusImpl.E_AUTH;
        } else if (code >= 500) {
            code = $APIStatusImpl.E_SERVER;
        }
        callback.status = new $APIStatusImpl(code, response.message());
        sHandler.obtainMessage(FAILED, callback).sendToTarget();
    }

    private synchronized void restJob(Call call) {
        if (call == mCall) {
            mCall = null;
        }
    }

    private class CallbackImpl implements Callback {
        public XAPICallback callback;
        public Call call;
        public $APIStatusImpl status;
        public Object data;

        private CallbackImpl(XAPICallback callback, Call call) {
            this.callback = callback;
            this.call = call;
        }

        @Override
        public void onFailure(Request request, IOException e) {
            performResult(this, null, e);
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
