package androidrubick.xframework.net.http.request.body;

import java.io.OutputStream;

import androidrubick.collect.CollectionsCompat;
import androidrubick.net.MediaType;
import androidrubick.text.Strings;
import androidrubick.utils.Objects;
import androidrubick.xframework.net.http.XHttpUtils;

/**
 * content type 为<code>application/json</code>的请求体。
 *
 * <p/>
 *
 * 如果调用了{@link #withRawJson(String)}将忽略所有的参数（param）设置（即{@link #param} 和 {@link #params} 视为无效）。
 *
 * <br/>
 *
 * 特别地，如果没有设置{@link #withRawJson(String)}，则所有的<code>params</code>将组装为一个json字符串。
 *
 * <br/>
 *
 * 所以，如果请求中只有某个字段是json字符串，不要选择使用该请求体。
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

    /**
     * 设置纯JSON文本，一旦设置，无视其他参数设置
     */
    public XHttpJsonBody withRawJson(String json, String charsetName) {
        return withRawJson(json).paramCharset(charsetName);
    }

    @Override
    protected boolean writeGeneratedBody(OutputStream out) throws Exception {
        if (CollectionsCompat.isEmpty(getParams()) && !mRawJsonSet) {
            return false;
        }
        byte[] body = generateBody();
        out.write(body);
        return true;
    }

    protected byte[] generateBody() throws Exception {
        if (mRawJsonSet) {
            return XHttpUtils.getBytes(mRawJson, getParamCharset());
        }
        String json = Objects.getOr(XHttpUtils.toJson(getParams()), Strings.EMPTY);
        return XHttpUtils.getBytes(json, getParamCharset());
    }

    @Override
    public int calculateByteSize() {
        int size = super.calculateByteSize();
        if (size > 0) {
            return size;
        }
        try {
            return generateBody().length;
        } catch (Exception e) {
            return XHttpUtils.DEFAULT_BODY_SIZE;
        }
    }

    @Override
    public MediaType rawContentType() {
        return MediaType.JSON;
    }

}
