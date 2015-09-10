package androidrubick.xframework.net.http;

import java.util.Map;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/10.
 */
public abstract class XHttpMessage {

    public abstract XHttpMessage header(String header, Object value) ;

    public abstract XHttpMessage headers(Map<String, ?> headers) ;

}
