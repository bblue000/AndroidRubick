package androidrubick.xframework.impl.http;

import android.os.Build;

import androidrubick.xbase.util.DeviceInfos;
import androidrubick.xframework.impl.http.internal.XHttpRequestServiceAfterG;
import androidrubick.xframework.impl.http.internal.XHttpRequestServicePreG;
import androidrubick.xframework.net.http.request.XHttpRequest;
import androidrubick.xframework.net.http.response.XHttpError;
import androidrubick.xframework.net.http.response.XHttpResponse;
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
            mBase = new XHttpRequestServicePreG();
        } else {
            mBase = new XHttpRequestServiceAfterG();
        }
    }

    @Override
    public XHttpResponse performRequest(XHttpRequest request) throws XHttpError {
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
