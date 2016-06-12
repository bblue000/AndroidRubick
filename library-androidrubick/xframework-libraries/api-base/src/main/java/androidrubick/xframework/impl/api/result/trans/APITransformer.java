package androidrubick.xframework.impl.api.result.trans;

import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/18.
 */
public abstract class APITransformer<T> {

    private static final Map<Class, APITransformer> CACHED;

    static {
        CACHED = new HashMap<Class, APITransformer>();
        CACHED.put(byte[].class, new ByteArrTransformer());
        CACHED.put(String.class, new StringTransformer());
        JSONTokenerTransformer jsonTransformer = new JSONTokenerTransformer();
        CACHED.put(JSONObject.class, jsonTransformer);
        CACHED.put(JSONArray.class, jsonTransformer);
    }

    public static APITransformer getTransformer(Class clz) {
        return CACHED.get(clz);
    }

    public static boolean isBase(Class<?> clz) {
        return CACHED.containsKey(clz);
    }

    public abstract T transform(Response response) throws Throwable;

}
