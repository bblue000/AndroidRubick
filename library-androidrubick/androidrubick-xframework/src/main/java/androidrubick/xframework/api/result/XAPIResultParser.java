package androidrubick.xframework.api.result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;

import androidrubick.io.IOUtils;
import androidrubick.utils.Objects;
import androidrubick.xframework.net.http.response.XHttpResponse;
import androidrubick.cache.mem.ByteArrayPool;
import androidrubick.io.PoolingByteArrayOutputStream;
import androidrubick.xbase.annotation.Configurable;

/**
 * something
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/6/4.
 *
 * @since 1.0
 */
@Configurable
public class XAPIResultParser {

    private XAPIResultParser() { }

    private static final ByteArrayPool sPool = new ByteArrayPool(4096);

    public static <T>T parse(XHttpResponse response, Class<? extends T> clz)
        throws IOException {
        byte[] buf = sPool.getBuf(512);
        PoolingByteArrayOutputStream out = new PoolingByteArrayOutputStream(sPool,
                (int) response.getContentLength());
        try {
            final String charset = response.getContentCharset();
            IOUtils.writeTo(response.getContent(), false, out, false, buf, null);
            IOUtils.close(response);
            final byte[] data = out.toByteArray();

            if (XResultable.class.isAssignableFrom(clz)) {
//                return JsonParser.toJsonObject(new String(out.toByteArray(), charset), clz);
            }
            // 1、String
            if (Objects.equals(clz, String.class)) {
                return (T) new String(data, charset);
            }
            // 2、JSONObject
            if (Objects.equals(clz, JSONObject.class) || Objects.equals(clz, JSONArray.class)) {
                String str = new String(data, charset);
                try {
                    return (T) new JSONTokener(str).nextValue();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            return (T) data;

        } finally {
            sPool.returnBuf(buf);
            IOUtils.close(response);
            IOUtils.close(out);
        }
    }

}
