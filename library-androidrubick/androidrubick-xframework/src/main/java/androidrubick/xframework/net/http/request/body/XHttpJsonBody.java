package androidrubick.xframework.net.http.request.body;

import org.apache.http.HttpEntity;

import java.io.OutputStream;

import androidrubick.collect.CollectionsCompat;
import androidrubick.net.MediaType;
import androidrubick.text.Strings;
import androidrubick.utils.Objects;
import androidrubick.xframework.net.http.request.XHttpRequestUtils;
import androidrubick.xbase.annotation.Configurable;

/**
 * content type 为<code>application/json</code>的
 *
 * 请求体
 *
 * <p/>
 *
 * 该Json类型为application/json
 *
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/5/25.
 *
 * @since 1.0
 */
public class XHttpJsonBody extends XHttpBody<XHttpJsonBody> {

    private boolean mRawJsonSet;
    private String mRawJson = Strings.EMPTY;

    protected XHttpJsonBody() {
        super();
    }

    /**
     * 设置纯JSON文本，一旦设置，无视其他参数设置
     */
    public XHttpJsonBody withRawJson(String json) {
        mRawJsonSet = true;
        mRawJson = Objects.getOr(json, Strings.EMPTY);
        return this;
    }

    @Override
    protected boolean writeGeneratedBody(OutputStream out) throws Exception {
        if (CollectionsCompat.isEmpty(mParams) && !mRawJsonSet) {
            return false;
        }
        byte[] body = generateBody();
        out.write(body);
        return true;
    }

    protected byte[] generateBody() throws Exception {
        if (mRawJsonSet) {
            return XHttpRequestUtils.getBytes(mRawJson, mParamEncoding);
        }
        return XHttpRequestUtils.getBytes(XHttpRequestUtils.toJson(mParams), mParamEncoding);
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

    @Configurable
    @Override
    protected MediaType rawContentType() {
        return MediaType.JSON;
    }

    @Override
    protected HttpEntity genreateHttpEntityByDerived() throws Exception {
        return XHttpRequestUtils.createByteArrayEntity(generateBody(), getContentType(), mParamEncoding);
    }
}
