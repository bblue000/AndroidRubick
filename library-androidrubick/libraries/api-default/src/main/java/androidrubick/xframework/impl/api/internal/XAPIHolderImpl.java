package androidrubick.xframework.impl.api.internal;

import androidrubick.net.HttpMethod;
import androidrubick.utils.Objects;
import androidrubick.xbase.util.XLog;
import androidrubick.xframework.api.XAPICallback;
import androidrubick.xframework.api.XAPIError;
import androidrubick.xframework.api.XAPIHolder;
import androidrubick.xframework.app.XGlobals;
import androidrubick.xframework.impl.api.XAPIConstants;
import androidrubick.xframework.impl.api.internal.param.XAPIParamParser;
import androidrubick.xframework.impl.api.internal.result.XAPIResultParser;
import androidrubick.xframework.net.http.XHttpRetryJob;
import androidrubick.xframework.net.http.request.XHttpRequest;
import androidrubick.xframework.net.http.response.XHttpError;
import androidrubick.xframework.net.http.response.XHttpResponse;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/15.
 */
public class XAPIHolderImpl implements XAPIHolder {

    private XHttpRequest mRequest;
    private Class mResultClz;
    private XAPICallback mCallback;
    private XAPIJob mJob;
    public XAPIHolderImpl(String url, HttpMethod method,
                          Object param, Class<?> resultClz,
                          XAPICallback<?> callback) {
        this.mRequest = XAPIParamParser.parseParamsAndHeaders(url, method, param);
        this.mResultClz = resultClz;
        this.mCallback = callback;
    }

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
            XLog.d(getClass(), "XAPIHolder#execute not idle");
        }
        return false;
    }

    @Override
    public boolean isIdle() {
        return Objects.isNull(mJob) || mJob.isFinished();
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        if (Objects.isNull(mJob)) {
            return true;
        }
        return mJob.cancel(mayInterruptIfRunning);
    }

    protected class XAPIJob extends XHttpRetryJob<Object, XAPIStatusImpl> {

        public XAPIJob() {
            super(XAPIConstants.RETRY_COUNT);
        }

        @Override
        protected XAPIStatusImpl parseResponse(XHttpRequest request, XHttpResponse response) {
            try {
                return XAPIResultParser.parse(request, response, mResultClz);
            } catch (Throwable e) {
                return new XAPIStatusImpl(XAPIError.ERR_CLIENT, e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(XAPIStatusImpl xapiStatus) {
            try {
                if (Objects.isNull(mCallback)) {
                    return;
                }
                if (xapiStatus.successMark) {
                    mCallback.onSuccess(xapiStatus.data, xapiStatus);
                } else {
                    mCallback.onFailed(xapiStatus);
                }
            } finally {
                // clear mJob
                XAPIHolderImpl.this.mJob = null;
            }
        }

        @Override
        protected void onCancelled(XAPIStatusImpl xapiStatus) {
            try {
                super.onCancelled(xapiStatus);
                if (!Objects.isNull(mCallback)) {
                    Object resultData = null;
                    if (!Objects.isNull(xapiStatus) && !xapiStatus.successMark) {
                        resultData = xapiStatus.data;
                    }
                    mCallback.onCanceled(resultData);
                }
            } finally {
                // clear mJob
                XAPIHolderImpl.this.mJob = null;
            }
        }

        @Override
        protected XAPIStatusImpl onNoMoreRetryOnHttpExc(XHttpRequest request, XHttpError exception) {
            switch (exception.getType()) {
                case Timeout:
                    return new XAPIStatusImpl(XAPIError.ERR_TIMEOUT, exception.getMessage());
                case Auth:
                case Server:
                    return new XAPIStatusImpl(exception.getStatusCode(), exception.getMessage());
                case Other:
                default:
                    return new XAPIStatusImpl(XAPIError.ERR_CLIENT, exception.getMessage());
            }
        }

        @Override
        protected XAPIStatusImpl onOtherExc(XHttpRequest request, XHttpResponse response, Throwable exception) {
            super.onOtherExc(request, response, exception);
            return new XAPIStatusImpl(XAPIError.ERR_CLIENT, exception.getMessage());
        }

    }
}
