package androidrubick.xframework.net.http;

import org.apache.http.ProtocolVersion;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/10.
 */
public class XHttpUtils {

    private XHttpUtils() { /* no instance needed */ }

    public static ProtocolVersion defHTTPProtocolVersion() {
        return new ProtocolVersion("HTTP", 1, 1);
    }

}
