package androidrubick.xframework.net.http.request.body;

import org.apache.http.HttpEntity;

import java.io.OutputStream;

import androidrubick.collect.CollectionsCompat;
import androidrubick.net.MediaType;
import androidrubick.utils.Objects;
import androidrubick.xframework.net.http.request.XHttpRequestUtils;

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
    protected MediaType rawContentType() {
        return MediaType.FORM_DATA;
    }

    @Override
    protected boolean writeGeneratedBody(OutputStream out) throws Exception {
        if (CollectionsCompat.isEmpty(this.mParams)) {
            return false;
        }
        byte[] body = generateBody();
        out.write(body);
        return true;
    }

    protected byte[] generateBody() throws Exception {
        byte[] body = NONE_BYTE;
        // 直接写入参数
        String query = XHttpRequestUtils.parseUrlEncodedParameters(mParams, mParamEncoding);
        if (!Objects.isEmpty(query)) {
            body = XHttpRequestUtils.getBytes(query, mParamEncoding);
        }
        return body;
    }

    @Override
    protected int calculateByteSize() {
        checkBuild();
        int size = super.calculateByteSize();
        if (size > 0) {
            return size;
        }
        try {
            return generateBody().length;
        } catch (Exception e) {
            return DEFAULT_BODY_SIZE;
        }
    }

    @Override
    protected HttpEntity genreateHttpEntityByDerived() throws Exception {
        return XHttpRequestUtils.createByteArrayEntity(generateBody(), getContentType(), mParamEncoding);
    }
}
