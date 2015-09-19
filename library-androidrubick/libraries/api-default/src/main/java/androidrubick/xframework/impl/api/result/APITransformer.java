package androidrubick.xframework.impl.api.result;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidrubick.xframework.net.http.response.XHttpResponse;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/18.
 */
/*package*/ abstract class APITransformer<T> {

    private static final Map<Class, APITransformer> CACHED;

    static {
        CACHED = new HashMap<Class, APITransformer>();
        CACHED.put(byte[].class, new ByteArrTransformer());
        CACHED.put(String.class, new StringTransformer());
        OrgJsonTransformer jsonTransformer = new OrgJsonTransformer();
        CACHED.put(JSONObject.class, jsonTransformer);
        CACHED.put(JSONArray.class, jsonTransformer);
    }

    static APITransformer getTransformer(Class clz) {
        return CACHED.get(clz);
    }

    abstract T transform(XHttpResponse response) throws Throwable;

}
