package androidrubick.xframework.impl.http;

import android.os.Build;

import androidrubick.xbase.util.DeviceInfos;
import androidrubick.xframework.net.http.request.XHttpReq;
import androidrubick.xframework.net.http.request.XHttpRequestAfterG;
import androidrubick.xframework.net.http.response.XHttpError;
import androidrubick.xframework.net.http.response.XHttpRes;
import androidrubick.xframework.net.http.spi.XHttpRequestService;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/10.
 */
public class XHttpRequestServiceImpl implements XHttpRequestService {

    private XHttpRequestService mBase;
    public XHttpRequestServiceImpl() {
        int version = DeviceInfos.getAndroidSDKVersion();
        if (version <= Build.VERSION_CODES.GINGERBREAD) {
            mBase = new XHttpRequestPreG();
        } else {
            mBase = new XHttpRequestAfterG(url, mMethod, mHeaders, mBody, mConnectionTimeout, mSocketTimeout);
        }
    }

    @Override
    public XHttpRes performRequest(XHttpReq request) throws XHttpError {
        return mBase.performRequest(request);
    }

    @Override
    public void trimMemory() {
        mBase.trimMemory();
    }

    @Override
    public boolean multiInstance() {
        return mBase.multiInstance();
    }
}
