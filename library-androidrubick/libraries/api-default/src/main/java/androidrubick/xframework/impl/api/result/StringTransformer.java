package androidrubick.xframework.impl.api.result;

import com.squareup.okhttp.Response;

/**
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/9/19 0019.
 *
 * @since 1.0
 */
/*package*/ class StringTransformer extends APITransformer<String> {

    @Override
    public String transform(Response response) throws Throwable {
        return response.body().string();
    }
}