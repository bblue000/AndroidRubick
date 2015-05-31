package androidrubick.xframework.net.http.request;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

import java.net.URI;

/**
 * The HttpPatch class does not exist in the Android framework, so this has been defined here.
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/5/30 0030.
 *
 * @since 1.0
 */
public class XHttpPatch extends HttpEntityEnclosingRequestBase {

    public final static String METHOD_NAME = "PATCH";

    public XHttpPatch() {
        super();
    }

    public XHttpPatch(final URI uri) {
        super();
        setURI(uri);
    }

    /**
     * @throws IllegalArgumentException if the uri is invalid.
     */
    public XHttpPatch(final String uri) {
        super();
        setURI(URI.create(uri));
    }

    @Override
    public String getMethod() {
        return METHOD_NAME;
    }

}