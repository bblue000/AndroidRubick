package androidrubick.xframework.api.internal;

import org.apache.http.StatusLine;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import androidrubick.io.IOUtils;
import androidrubick.net.HttpMethod;
import androidrubick.xframework.api.XAPI;
import androidrubick.xframework.api.XAPICallback;
import androidrubick.xframework.api.param.XAPIParamParser;
import androidrubick.xframework.api.param.XParamable;
import androidrubick.xframework.api.result.XAPIResultParser;
import androidrubick.xframework.api.result.XResultable;
import androidrubick.xframework.net.http.request.XHttpRequest;
import androidrubick.xframework.net.http.response.XHttpResultHolder;
import androidrubick.xframework.task.XJob;
import androidrubick.xframework.xbase.annotation.Configurable;

/**
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/7/13.
 *
 * @since 1.0
 */
public class XAPIJob extends XJob<XParamable, Object, XAPIStatusImpl> {

    private String mBaseUrl;
    private HttpMethod mHttpMethod;
    private Class<?> mClz;
    private XAPICallback mCallback;
    public XAPIJob(String url, HttpMethod method, Class<?> resultClz, XAPICallback callback) {
        this.mBaseUrl = url;
        this.mHttpMethod = method;
        this.mClz = resultClz;
        this.mCallback = callback;
    }

    @Configurable
    @Override
    protected XAPIStatusImpl doInBackground(XParamable[] params) {
        final XParamable param = params[0];
        XHttpRequest request = null;
        XHttpResultHolder resultHolder = null;
        try {
            request = generateHttpRequest(mBaseUrl, mHttpMethod, param);
            resultHolder = request.performRequest();

            // 1、获取状态栏
            final StatusLine statusLine = resultHolder.getStatusLine();
            // TODO 判断是否是成功状态
            if (!isSuccessHttpStatus(statusLine)) {
                return new XAPIStatusImpl(statusLine.getStatusCode(), statusLine.getReasonPhrase());
            }

            // 2、获取内容
            Object parsedData = XAPIResultParser.parse(resultHolder, this.mClz);
            if (null == parsedData || !XResultable.class.isAssignableFrom(parsedData.getClass())) {
                return new XAPIStatusImpl(statusLine.getStatusCode(), statusLine.getReasonPhrase(), parsedData);
            }

            XResultable data = (XResultable) parsedData;
            if (!isSuccessAPIStatus(data)) {
                return new XAPIStatusImpl(data.code, data.msg);
            }
            return new XAPIStatusImpl(data.code, data.msg, data.data);
        } catch (MalformedURLException e) {
            return new XAPIStatusImpl(XAPI.ERR_BAD_URL, e.getMessage());
        } catch (SocketTimeoutException e) {
            return new XAPIStatusImpl(XAPI.ERR_TIMEOUT, e.getMessage());
        } catch (ConnectTimeoutException e) {
            return new XAPIStatusImpl(XAPI.ERR_TIMEOUT, e.getMessage());
        } catch (HttpHostConnectException e) {
            return new XAPIStatusImpl(XAPI.ERR_NETWORK, e.getMessage());
        } catch (UnknownHostException e) {
            return new XAPIStatusImpl(XAPI.ERR_NETWORK, e.getMessage());
        } catch (IOException e) {
            return new XAPIStatusImpl(XAPI.ERR_UNKNOWN, e.getMessage());
        } catch (RuntimeException e) {
            return new XAPIStatusImpl(XAPI.ERR_CLIENT, e.getMessage());
        } finally {
            IOUtils.close(resultHolder);
        }
    }

    @Override
    protected void onPostExecute(XAPIStatusImpl xapiStatus) {
        if (xapiStatus.successMark) {
            mCallback.onSuccess(xapiStatus.data, xapiStatus);
        } else {
            mCallback.onFailed(xapiStatus);
        }
    }

    @Override
    protected void onCancelled(XAPIStatusImpl xapiStatus) {
        mCallback.onCanceled(xapiStatus);
        super.onCancelled(xapiStatus);
    }

    protected XHttpRequest generateHttpRequest(String baseUrl, HttpMethod method, XParamable param) {
        return XAPIParamParser.parseParamsAndHeaders(baseUrl, method, param);
    }

    @Configurable
    protected boolean isSuccessHttpStatus(StatusLine statusLine) {
        return statusLine.getStatusCode() == 200;
    }

    @Configurable
    protected boolean isSuccessAPIStatus(XResultable resultable) {
        return resultable.code == 200;
    }

    @Override
    protected int getJobType() {
        return UI_JOB;
    }
}
