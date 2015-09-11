package androidrubick.xframework.net.http;

import org.apache.http.ProtocolVersion;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Map;

import androidrubick.cache.mem.ByteArrayPool;
import androidrubick.collect.CollectionsCompat;
import androidrubick.text.Strings;
import androidrubick.xbase.util.JsonParser;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/10.
 */
public class XHttpUtils {

    private XHttpUtils() { /* no instance needed */ }

    public static final ByteArrayPool BYTE_ARRAY_POOL = new ByteArrayPool(4096);

    public static ProtocolVersion defHTTPProtocolVersion() {
        return new ProtocolVersion("HTTP", 1, 1);
    }

    public static String toJson(Map<String, ?> parameters) {
        if (CollectionsCompat.isEmpty(parameters)) {
            return Strings.EMPTY;
        }
        return JsonParser.toJsonString(parameters);
    }

    public static byte[] getBytes(Object value, String charsetName) throws UnsupportedEncodingException {
        return String.valueOf(value).getBytes(charsetName);
    }

    public static byte[] getBytes(Object value, Charset charset) throws UnsupportedEncodingException {
        return String.valueOf(value).getBytes(charset.name());
    }
}
