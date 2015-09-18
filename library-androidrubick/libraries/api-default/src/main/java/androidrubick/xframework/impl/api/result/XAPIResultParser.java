package androidrubick.xframework.impl.api.result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import androidrubick.cache.mem.ByteArrayPool;
import androidrubick.io.IOUtils;
import androidrubick.io.PoolingByteArrayOutputStream;
import androidrubick.utils.Exceptions;
import androidrubick.utils.Objects;
import androidrubick.xbase.annotation.Configurable;
import androidrubick.xbase.util.JsonParser;
import androidrubick.xframework.impl.api.internal.XAPIStatusImpl;
import androidrubick.xframework.net.http.XHttps;
import androidrubick.xframework.net.http.request.XHttpRequest;
import androidrubick.xframework.net.http.response.XHttpResponse;

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

    public static XAPIStatusImpl parse(XHttpRequest request, XHttpResponse response, Class<?> clz)
        throws IOException {

        ByteArrayPool pool = XHttps.BYTE_ARRAY_POOL;

        byte[] buf = pool.getBuf(512);
        PoolingByteArrayOutputStream out = new PoolingByteArrayOutputStream(pool,
                (int) response.getContentLength());
        try {
            final String charset = response.getContentCharset();
            IOUtils.writeTo(response.getContent(), false, out, false, buf, null);
            IOUtils.close(response);
            final byte[] data = out.toByteArray();
            // 1、byte[]
            if (Objects.equals(clz, byte[].class)) {
                return new XAPIStatusImpl(response.getStatusCode(), response.getStatusMessage(), data);
            }
            // 2、String
            if (Objects.equals(clz, String.class)) {
                return new XAPIStatusImpl(response.getStatusCode(), response.getStatusMessage(),
                        new String(data, charset));
            }
            // 3、JSONObject
            if (Objects.equals(clz, JSONObject.class) || Objects.equals(clz, JSONArray.class)) {
                String str = new String(data, charset);
                try {
                    return new XAPIStatusImpl(response.getStatusCode(), response.getStatusMessage(),
                            new JSONTokener(str).nextValue());
                } catch (JSONException e) {
                    throw Exceptions.toRuntime(e);
                }
            }
            // 4、API object
            if (BaseResult.class.isAssignableFrom(clz)) {
                ParameterizedType type = new ParameterizedType() {
                    @Override
                    public Type[] getActualTypeArguments() {
                        return new Type[0];
                    }

                    @Override
                    public Type getOwnerType() {
                        return null;
                    }

                    @Override
                    public Type getRawType() {
                        return null;
                    }
                };

                return new XAPIStatusImpl(response.getStatusCode(), response.getStatusMessage(),
                        JsonParser.toObject(new String(data, charset), clz));
            }
            return new XAPIStatusImpl(response.getStatusCode(), response.getStatusMessage(),
                    JsonParser.toObject(new String(data, charset), clz));
        } finally {
            pool.returnBuf(buf);
            IOUtils.close(response);
            IOUtils.close(out);
        }
    }

}
