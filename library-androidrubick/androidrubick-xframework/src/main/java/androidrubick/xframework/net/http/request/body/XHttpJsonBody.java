package androidrubick.xframework.net.http.request.body;

import java.io.OutputStream;

import androidrubick.net.MediaType;
import androidrubick.text.Strings;
import androidrubick.utils.Objects;
import androidrubick.xframework.net.http.request.XHttpRequestEncoder;

/**
 * 请求体为Json
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
        byte[] body = generateBody();
        out.write(body);
        return true;
    }

    protected byte[] generateBody() throws Exception {
        if (mRawJsonSet) {
            return XHttpRequestEncoder.getBytes(mRawJson, mParamEncoding);
        }
        return XHttpRequestEncoder.getBytes(XHttpRequestEncoder.toJson(mParams), mParamEncoding);
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
    protected MediaType rawContentType() {
        return MediaType.JSON;
    }
}
