package androidrubick.xframework.net.http.request.body;

import java.io.OutputStream;
import java.util.Map;

import androidrubick.utils.Objects;
import androidrubick.utils.Preconditions;
import androidrubick.collect.MapBuilder;
import androidrubick.xframework.net.http.PredefinedBAOS;
import androidrubick.xframework.net.http.XHttp;

/**
 * 封装POST等含有请求体的请求方法创建请求体的过程
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/5/17 0017.
 *
 * @see androidrubick.net.MediaType#FORM_DATA
 * @see androidrubick.net.MediaType#FORM_DATA_MULTIPART
 */
public abstract class XHttpBody<R extends XHttpBody> {

    public static XHttpUrlEncodedBody newUrlEncodedBody() {
        return new XHttpUrlEncodedBody();
    }

    public static XHttpMultipartBody newMultipartBody() {
        return new XHttpMultipartBody();
    }

    static final byte[] NONE_BYTE = new byte[0];

    boolean mUserSetContentType = false;
    String mContentType = XHttp.DEFAULT_OUTPUT_CONTENT_TYPE.name();
    String mParamEncoding = XHttp.DEFAULT_CHARSET;
    Map<String, String> mParams;
    byte[] mRawBody;
    protected XHttpBody() {
    }

    protected R self() {
        return (R) this;
    }

    /**
     * 设置单个请求参数
     *
     * @param key 单个参数的键值
     * @param value 单个参数的值
     *
     */
    public R param(String key, String value) {
        Preconditions.checkArgument(!Objects.isEmpty(key), "param key is null or empty");
        prepareParams();
        mParams.put(key, value);
        return self();
    }

    /**
     *
     * 设置参数
     *
     * @param params 参数信息
     *
     */
    public R params(Map<String, String> params) {
        if (!Objects.isEmpty(params)) {
            prepareParams();
            mParams.putAll(params);
        }
        return self();
    }

    private void prepareParams() {
        if (null == mParams) {
            mParams = MapBuilder.newHashMap(16).build();
        }
    }

    /**
     * 设置参数的字符编码
     */
    public R paramEncoding(String charset) {
        mParamEncoding = Preconditions.checkNotNull(charset);
        return self();
    }

    /**
     * 直接设置字节流作为请求体的内容
     */
    public R withRawBody(byte[] body) {
        mRawBody = body;
        return self();
    }

    /**
     * 硬性指定ContentType
     */
    public R contentType(String contentType) {
        Preconditions.checkNotNull(contentType);
        mUserSetContentType = true;
        mContentType = contentType;
        return self();
    }

    /**
     * 获取内容类型
     */
    public String getContentType() {
        return mContentType;
    }

    /**
     * 获取内容
     */
    public byte[] getBody() {
        PredefinedBAOS out = new PredefinedBAOS(calculateByteSize());
        writeTo(out);
        return out.toByteArray();
    }

    /**
     * 将body写入到指定输出流中
     */
    public abstract void writeTo(OutputStream out);

    /**
     * 简单计算内容长度
     * @return
     */
    protected abstract int calculateByteSize();

    /**
     * 创建当前的请求体
     */
    public R build() {
        return self();
    }
}
