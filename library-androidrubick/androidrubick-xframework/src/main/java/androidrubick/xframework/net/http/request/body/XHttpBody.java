package androidrubick.xframework.net.http.request.body;

import android.util.AndroidRuntimeException;

import java.io.OutputStream;
import java.util.Map;

import androidrubick.net.MediaType;
import androidrubick.utils.Objects;
import androidrubick.utils.Preconditions;
import androidrubick.collect.MapBuilder;
import androidrubick.xframework.net.http.PredefinedBAOS;
import androidrubick.xframework.net.http.XHttp;
import androidrubick.xframework.xbase.annotation.Configurable;

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

    public static XHttpJsonBody newJsonBody() {
        return new XHttpJsonBody();
    }


    // static
    static final byte[] NONE_BYTE = new byte[0];
    @Configurable
    static final int DEFAULT_BODY_SIZE = 512;


    // instance
    boolean mUserSetContentType = false;
    String mContentType;
    String mParamEncoding = XHttp.DEFAULT_CHARSET;
    Map<String, Object> mParams;
    byte[] mRawBody;
    private boolean mIsBuild;
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
    public R param(String key, Object value) {
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
    public R params(Map<String, ?> params) {
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
     * 直接设置字节流作为请求体的内容（如果设置了该值，则无视其他param）
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
        checkBuild();
        return mContentType;
    }

    /**
     * 获取内容
     */
    public byte[] getBody() {
        checkBuild();
        PredefinedBAOS out = new PredefinedBAOS(calculateByteSize());
        writeTo(out);
        return out.toByteArray();
    }

    /**
     * 将body写入到指定输出流中
     */
    public void writeTo(OutputStream out) {
        checkBuild();
        try {
            // if raw body supported
            if (writeRawBody(out)) {
                return ;
            }
            if (writeGeneratedBody(out)) {
                return ;
            }
        } catch (Exception e) {
            throw new AndroidRuntimeException(e);
        }
    }

    // >>>>>>>>>>>>>>>>>>>>>>
    // write body internal
    protected boolean writeRawBody(OutputStream out) throws Exception {
        if (!Objects.isNull(mRawBody)) {
            out.write(mRawBody);
            return true;
        }
        return false;
    }

    protected boolean writeGeneratedBody(OutputStream out) throws Exception {
        return false;
    }

    /**
     * 简单计算内容长度
     */
    protected int calculateByteSize() {
        checkBuild();
        if (null != mRawBody) {
            return mRawBody.length;
        }
        return 0;
    }

    /**
     * 是否调用了Build完成了创建过程
     */
    protected boolean isBuild() {
        return mIsBuild;
    }

    protected void checkBuild() {
        Preconditions.checkOperation(isBuild(), "%s not build", toString());
    }

    protected abstract MediaType rawContentType() ;

    protected void validateContentTypeInBuild() {
        // 如果没有指定指定contentType
        if (!mUserSetContentType) {
            MediaType mediaType = rawContentType();
            if (!Objects.isEmpty(mParamEncoding)) {
                mediaType = mediaType.withCharset(mParamEncoding);
            }
            mContentType = mediaType.name();
        }
    }

    /**
     * 创建当前的请求体
     */
    public R build() {
        validateContentTypeInBuild();
        mIsBuild = true;
        return self();
    }
}
