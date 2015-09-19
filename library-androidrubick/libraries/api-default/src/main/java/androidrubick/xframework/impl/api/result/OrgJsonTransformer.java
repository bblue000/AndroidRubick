package androidrubick.xframework.impl.api.result;

import org.json.JSONTokener;

import androidrubick.utils.Objects;
import androidrubick.xframework.net.http.response.XHttpResponse;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/19 0019.
 *
 * @since 1.0
 */
/*package*/ class OrgJsonTransformer extends APITransformer {

    @Override
    public Object transform(XHttpResponse response) throws Throwable {
        APITransformer transformer = getTransformer(String.class);
        if (Objects.isNull(transformer)) {
            return null;
        }
        String jsonString = (String) transformer.transform(response);
        return new JSONTokener(jsonString).nextValue();
    }
}