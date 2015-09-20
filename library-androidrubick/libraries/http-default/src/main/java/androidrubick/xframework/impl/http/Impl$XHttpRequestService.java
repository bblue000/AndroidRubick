package androidrubick.xframework.impl.http;

import android.os.Build;

import androidrubick.xbase.util.DeviceInfos;
import androidrubick.xframework.impl.http.internal.HttpRequestServiceAfterG;
import androidrubick.xframework.impl.http.internal.HttpRequestServicePreG;
import androidrubick.xframework.net.http.request.XHttpRequest;
import androidrubick.xframework.net.http.response.XHttpError;
import androidrubick.xframework.net.http.response.XHttpResponse;
import androidrubick.xframework.net.http.spi.XHttpRequestService;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/10.
 */
public class Impl$XHttpRequestService implements XHttpRequestService {

    private XHttpRequestService mBase;
    public Impl$XHttpRequestService() {
        int version = DeviceInfos.getAndroidSDKVersion();
        if (version <= Build.VERSION_CODES.GINGERBREAD) {
            mBase = new HttpRequestServicePreG();
        } else {
            mBase = new HttpRequestServiceAfterG();
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
