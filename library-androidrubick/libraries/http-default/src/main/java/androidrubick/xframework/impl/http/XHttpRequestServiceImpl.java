package androidrubick.xframework.impl.http;

import androidrubick.xframework.net.http.request.XHttpReq;
import androidrubick.xframework.net.http.response.XHttpError;
import androidrubick.xframework.net.http.response.XHttpRes;
import androidrubick.xframework.net.http.spi.XHttpRequestService;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/10.
 */
public class XHttpRequestServiceImpl implements XHttpRequestService {
    @Override
    public XHttpRes performRequest(XHttpReq request) throws XHttpError {
        return null;
    }

    @Override
    public void trimMemory() {

    }

    @Override
    public boolean multiInstance() {
        return false;
    }
}
