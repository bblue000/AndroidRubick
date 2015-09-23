package androidrubick.xframework.impl.api;

import java.util.Map;

import androidrubick.net.HttpMethod;
import androidrubick.text.Strings;
import androidrubick.utils.Objects;
import androidrubick.xbase.util.XLog;
import androidrubick.xframework.api.XAPICallback;
import androidrubick.xframework.api.XAPIError;
import androidrubick.xframework.api.XAPIHolder;
import androidrubick.xframework.app.XGlobals;
import androidrubick.xframework.impl.api.param.$APIParamParser;
import androidrubick.xframework.impl.api.result.$APIResultParser;
import androidrubick.xframework.net.http.XHttpRetryJob;
import androidrubick.xframework.net.http.request.XHttpRequest;
import androidrubick.xframework.net.http.response.XHttpError;
import androidrubick.xframework.net.http.response.XHttpResponse;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/15.
 */
/*package*/ class $APIHolderImpl implements XAPIHolder {

    private static final String TAG = $APIHolderImpl.class.getSimpleName();

    private XHttpRequest mRequest;
    private Class mResultClz;
    private XAPICallback mCallback;
    private XAPIJob mJob;
    public $APIHolderImpl(String url, HttpMethod method,
                          Object param, Map<String, String> extraHeaders,
                          Class<?> resultClz,
                          XAPICallback<?> callback) {
        this.mRequest = $APIParamParser.parse(url, method, param, extraHeaders);
        this.mResultClz = resultClz;
        this.mCallback = callback;
    }

    /**
     * 该处为了避免重复请求，如果正在处理请求则直接返回
     */
    @Override
    public boolean execute() {
        if (isIdle()) {
            // lazy create a new job
            synchronized (this) {
                if (isIdle()) {
                    mJob = new XAPIJob();
                    mJob.execute(mRequest);
                    return true;
                }
            }
        }
        if (XGlobals.DEBUG) {
            XLog.d(TAG, "XAPIHolder@" + Integer.toHexString(hashCode()) + "#execute not idle");
        }
        return false;
    }

    @Override
    public boolean isIdle() {
        return Objects.isNull(mJob) || mJob.isFinished();
    }

    @Override
    public boolean cancel() {
        if (Objects.isNull(mJob)) {
            return true;
        }
        return mJob.cancel(true);
    }

    protected void restJob() {
        synchronized (this) {
            mJob = null;
        }
    }

    /**
     * 执行并处理API请求的job
     *
     */
    private class XAPIJob extends XHttpRetryJob<Void, $APIStatusImpl> {

        public XAPIJob() {
            super(APIConstants.RETRY_COUNT);
        }

        @Override
        protected $APIStatusImpl parseResponse(XHttpRequest request, XHttpResponse response)
                throws Throwable {
            return $APIResultParser.parse(request, response, mResultClz);
        }

        @Override
        protected void onPostExecute($APIStatusImpl apiStatus) {
            if (XGlobals.DEBUG) {
                XLog.d(TAG, "XAPIHolder@" + Integer.toHexString(hashCode()) + "#onPostExecute");
            }
            try {
                if (!Objects.isNull(mCallback)) {
                    if (!Objects.isNull(apiStatus) && apiStatus.successMark) {
                        mCallback.onSuccess(apiStatus.data, apiStatus);
                    } else {
                        mCallback.onFailed(apiStatus);
                    }
                }
            } finally {
                // clear mJob
                restJob();
            }
        }

        @Override
        protected void onCancelled($APIStatusImpl apiStatus) {
            if (XGlobals.DEBUG) {
                XLog.d(TAG, "XAPIHolder@" + Integer.toHexString(hashCode()) + "#onCancelled");
            }
            try {
                super.onCancelled(apiStatus);
                if (!Objects.isNull(mCallback)) {
                    if (!Objects.isNull(apiStatus) && apiStatus.successMark) {
                        mCallback.onCanceled(apiStatus.data, apiStatus);
                    } else {
                        mCallback.onCanceled(null, apiStatus);
                    }
                }
            } finally {
                // clear mJob
                restJob();
            }
        }

        @Override
        protected $APIStatusImpl onNoMoreRetryOnHttpExc(XHttpRequest request, XHttpError exception) {
            switch (exception.getType()) {
                case Timeout:
                    return new $APIStatusImpl(XAPIError.ERR_TIMEOUT, exception.getMessage());
                case Auth:
                case Server:
                    return new $APIStatusImpl(exception.getStatusCode(), exception.getMessage());
                case Network:
                case NoConnection:
                    return new $APIStatusImpl(XAPIError.ERR_NETWORK, exception.getMessage());
                case Other:
                default:
                    return new $APIStatusImpl(XAPIError.ERR_CLIENT, exception.getMessage());
            }
        }

        @Override
        protected $APIStatusImpl onOtherExc(XHttpRequest request, XHttpResponse response, Throwable exception) {
            super.onOtherExc(request, response, exception);
            // TODO get message
            String message = exception.getMessage();
            if (!Objects.isNull(response) && !Strings.isEmpty(response.getStatusMessage())) {
                message = response.getStatusMessage();
            }
            return new $APIStatusImpl(XAPIError.ERR_CLIENT, message);
        }

    }
}
