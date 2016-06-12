package androidrubick.xframework.impl.api.result;

import com.squareup.okhttp.Response;

import org.json.JSONTokener;

/**
 *
 * Android源码中对{@link JSONXXX}的支持，如{@link org.json.JSONObject}、
 * {@link org.json.JSONArray}等。
 *
 * <p/>
 *
 * Created by Yin Yong on 16/3/22.
 */
public class JSONTokenerTransformer extends APITransformer<Object> {

    @Override
    public Object transform(Response response) throws Throwable {
        return new JSONTokener(response.body().string()).nextValue();
    }

}
