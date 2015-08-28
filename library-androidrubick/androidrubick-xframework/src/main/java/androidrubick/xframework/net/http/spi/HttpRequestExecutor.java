package androidrubick.xframework.net.http.spi;

import java.io.IOException;

import androidrubick.xframework.net.http.request.XHttpRequest;
import androidrubick.xframework.net.http.response.XHttpResultHolder;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/8/28.
 */
public interface HttpRequestExecutor {

    XHttpResultHolder performRequest(XHttpRequest request) throws IOException;

}
