package androidrubick.xframework.net.http.request.body;

import android.util.AndroidRuntimeException;

import org.apache.http.HttpEntity;

import java.io.OutputStream;
import java.util.Map;

import androidrubick.net.MediaType;
import androidrubick.utils.Objects;
import androidrubick.utils.Preconditions;
import androidrubick.collect.MapBuilder;
import androidrubick.xframework.net.http.PredefinedBAOS;
import androidrubick.xframework.net.http.XHttp;
import androidrubick.xframework.net.http.request.XHttpRequestUtils;
import androidrubick.xframework.xbase.annotation.Configurable;
import androidrubick.xframework.xbase.annotation.ForTest;

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
    protected static final byte[] NONE_BYTE = new byte[0];
    @Configurable
    protected static final int DEFAULT_BODY_SIZE = 512;


    // instance
    protected boolean mUserSetContentType = false;
    protected String mContentType;
    protected String mParamEncoding = XHttp.DEFAULT_CHARSET;
    protected Map<String, Object> mParams;
    protected byte[] mRawBody;
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
        Preconditions.checkArgument(!Objects.isEmpty(charset), "charset is null or empty");
        mParamEncoding = charset;
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
        Preconditions.checkArgument(!Objects.isEmpty(contentType), "charset is null or empty");
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
            writeEmpty(out);
        } catch (Exception e) {
            throw new AndroidRuntimeException(e);
        }
    }

    // >>>>>>>>>>>>>>>>>>>>>>
    // write body internal
    /**
     * 判断是否设置了原始数据，如果已经设置，则向参数流中写入原始数据，并返回true；
     *
     * 否则直接返回false
     * @throws Exception
     */
    protected boolean writeRawBody(OutputStream out) throws Exception {
        if (!Objects.isNull(mRawBody)) {
            out.write(mRawBody);
            return true;
        }
        return false;
    }

    /**
     * 判断是否由子类各自的条件生成请求体数据，如果能够生成，则向参数流中写入原始数据，并返回true；
     *
     * 否则直接返回false
     * @throws Exception
     */
    protected boolean writeGeneratedBody(OutputStream out) throws Exception {
        return false;
    }

    protected void writeEmpty(OutputStream out) throws Exception {
        out.write(NONE_BYTE);
    }

    /**
     * 兼容 for HttpClient
     */
    public HttpEntity genreateHttpEntity() {
        checkBuild();
        try {
            HttpEntity httpEntity = genreateHttpEntityByRawBody();
            if (null != httpEntity) {
                return httpEntity;
            }
            httpEntity = genreateHttpEntityByDerived();
            if (null != httpEntity) {
                return httpEntity;
            }
            return genreateEmptyHttpEntity();
        } catch (Exception e) {
            throw new AndroidRuntimeException(e);
        }
    }

    protected HttpEntity genreateHttpEntityByRawBody() throws Exception {
        if (Objects.isNull(mRawBody)) {
            return null;
        }
        return XHttpRequestUtils.createByteArrayEntity(mRawBody, getContentType(), mParamEncoding);
    }

    protected HttpEntity genreateHttpEntityByDerived() throws Exception {
        return null;
    }

    protected HttpEntity genreateEmptyHttpEntity() throws Exception {
        return XHttpRequestUtils.createByteArrayEntity(NONE_BYTE, getContentType(), mParamEncoding);
    }

    /**
     * 获取内容
     */
    @ForTest
    public byte[] getBody() {
        checkBuild();
        PredefinedBAOS out = new PredefinedBAOS(calculateByteSize());
        writeTo(out);
        return out.toByteArray();
    }

    /**
     * 简单计算内容长度
     */
    @ForTest
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

    /**
     * 子类本身默认的content type
     */
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
