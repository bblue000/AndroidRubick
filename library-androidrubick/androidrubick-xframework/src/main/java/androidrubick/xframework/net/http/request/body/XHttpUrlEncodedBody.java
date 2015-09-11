package androidrubick.xframework.net.http.request.body;

import java.io.OutputStream;

import androidrubick.collect.CollectionsCompat;
import androidrubick.net.HttpUrls;
import androidrubick.net.MediaType;
import androidrubick.text.Strings;
import androidrubick.xframework.net.http.XHttpUtils;

/**
 * content type 为<code>application/x-www-form-urlencoded</code>的
 *
 * 请求体
 *
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/5/22.
 *
 * @since 1.0
 */
public class XHttpUrlEncodedBody extends XHttpBody<XHttpUrlEncodedBody> {

    protected XHttpUrlEncodedBody() {
        super();
    }

    @Override
    protected boolean writeGeneratedBody(OutputStream out) throws Exception {
        if (CollectionsCompat.isEmpty(getParams())) {
            return false;
        }
        byte[] body = generatedBody();
        out.write(body);
        return true;
    }

    @Override
    protected byte[] generatedBody() throws Exception {
        byte[] body = NONE_BYTE;
        // 直接写入参数
        String query = HttpUrls.toUrlEncodedQueryString(getParams(), getParamCharset().name());
        if (!Strings.isEmpty(query)) {
            body = XHttpUtils.getBytes(query, getParamCharset());
        }
        return body;
    }

    @Override
    public MediaType rawContentType() {
        return MediaType.FORM_DATA;
    }
}
