package androidrubick.xframework.impl.api.internal;

import androidrubick.net.HttpMethod;
import androidrubick.utils.Objects;
import androidrubick.xframework.api.XAPICallback;
import androidrubick.xframework.api.XAPIError;
import androidrubick.xframework.api.XAPIHolder;
import androidrubick.xframework.impl.api.internal.param.XAPIParamParser;
import androidrubick.xframework.impl.api.internal.result.XAPIResultParser;
import androidrubick.xframework.net.http.XHttpJob;
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
            mJob = new XAPIJob();
            mJob.execute(mRequest);
            return true;
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


    protected class XAPIJob extends XHttpJob<Object, XAPIStatusImpl> {

        public XAPIJob() {

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
            if (Objects.isNull(mCallback)) {
                return;
            }
            if (xapiStatus.successMark) {
                mCallback.onSuccess(xapiStatus.data, xapiStatus);
            } else {
                mCallback.onFailed(xapiStatus);
            }
        }

        @Override
        protected void onCancelled(XAPIStatusImpl xapiStatus) {
            super.onCancelled(xapiStatus);
            if (!Objects.isNull(mCallback)) {
                Object resultData = null;
                if (!Objects.isNull(xapiStatus) && !xapiStatus.successMark) {
                    resultData = xapiStatus.data;
                }
                mCallback.onCanceled(resultData);
            }
        }

        @Override
        protected XAPIStatusImpl onHttpExc(XHttpRequest request, XHttpError exception) {
            super.onHttpExc(request, exception);
            switch (exception.getType()) {
                case Timeout:
                    return new XAPIStatusImpl(XAPIError.ERR_TIMEOUT, exception.getMessage());
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
