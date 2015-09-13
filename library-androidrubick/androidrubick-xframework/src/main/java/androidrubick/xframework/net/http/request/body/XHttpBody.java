package androidrubick.xframework.net.http.request.body;

import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Map;

import androidrubick.collect.CollectionsCompat;
import androidrubick.net.MediaType;
import androidrubick.utils.ArraysCompat;
import androidrubick.utils.Exceptions;
import androidrubick.utils.Objects;
import androidrubick.utils.Preconditions;
import androidrubick.collect.MapBuilder;
import androidrubick.xframework.net.http.XHttps;

/**
 * 封装POST等含有请求体的请求方法创建请求体的过程。
 *
 * <p/>
 *
 * 创建一个HTTP/HTTPS请求体：
 * <table>
 *     <tr>
 *         <td>方法</td>
 *         <td>说明</td>
 *     </tr>
 *     <tr>
 *         <td>{@link #param} 和 {@link #params}</td>
 *         <td>设置参数，每个参数对应请求体中一项，比如，</td>
 *     </tr>
 *     <tr>
 *         <td>{@link #paramCharset(String)}</td>
 *         <td>设置参数字符编码，默认为{@link androidrubick.text.Charsets#UTF_8 UTF-8}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link #withRawBody(byte[])}</td>
 *         <td>直接设置请求体的字节数组，所有参数设置将无效</td>
 *     </tr>
 * </table>
 *
 * 可以使用{@link #writeTo(java.io.OutputStream)}提取出内容。
 *
 * <p/>
 *
 * 默认提供了三种实现：
 * <ul>
 *     <li>
 *         {@link XHttpJsonBody} 内容为Json字符串的请求体
 *     </li>
 *     <li>
 *         {@link XHttpUrlEncodedBody} 内容为使用UrlEncoder编码的字符串的请求体
 *     </li>
 *     <li>
 *         {@link XHttpMultipartBody} 为表单项请求体
 *     </li>
 * </ul>
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

    // instance
    private MediaType mContentType;
    private Charset mParamEncoding;
    private Map<String, Object> mParams;
    private byte[] mRawBody;
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
        CollectionsCompat.put(mParams, key, value);
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
        if (!CollectionsCompat.isEmpty(params)) {
            prepareParams();
            CollectionsCompat.putAll(mParams, params);
        }
        return self();
    }

    private void prepareParams() {
        if (null == mParams) {
            mParams = MapBuilder.newHashMap(16).build();
        }
    }

    /**
     * 设置参数的字符编码。
     *
     * <p/>
     *
     * 默认为{@link androidrubick.text.Charsets#UTF_8}
     */
    public R paramCharset(String charsetName) {
        Preconditions.checkArgument(!Objects.isEmpty(charsetName), "charset is null or empty");
        return paramCharset(Charset.forName(charsetName));
    }

    /**
     * 设置参数的字符编码。
     *
     * <p/>
     *
     * 默认为{@link androidrubick.text.Charsets#UTF_8}
     */
    public R paramCharset(Charset charset) {
        Preconditions.checkNotNull(charset, "charset");
        mParamEncoding = charset;
        // 修改contentType的
        mContentType = getContentType().withCharset(mParamEncoding.name());
        return self();
    }

    /**
     * 硬性指定ContentType，这将覆盖{@link #rawContentType()}
     */
    public R contentType(String contentType) {
        Preconditions.checkArgument(!Objects.isEmpty(contentType), "charset is null or empty");
        return contentType(MediaType.parse(contentType));
    }

    /**
     * 硬性指定ContentType，这将覆盖{@link #rawContentType()}
     */
    public R contentType(MediaType contentType) {
        Preconditions.checkNotNull(contentType, "Content-Type");
        // 如果
        if (Objects.isNull(contentType.charset())) {
            contentType = contentType.withCharset(getParamCharset().name());
        }
        mContentType = contentType;
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
     * 获得调用{@link #param(String, Object)}和{@link #params(Map)}方法设置的参数，
     * 如果没有设置过参数，则返回null。
     */
    public Map<String, Object> getParams() {
        return mParams;
    }

    /**
     * 获取参数的字符集编码，如果没有设置过，默认返回{@link androidrubick.text.Charsets#UTF_8 UTF-8}
     *
     * @see #paramCharset(String)
     */
    public Charset getParamCharset() {
        return Objects.getOr(mParamEncoding, XHttps.DEFAULT_CHARSET);
    }

    /**
     * 获取外部设置的内容类型；
     *
     * 如果没有调用{@link #contentType}设置过，则返回{@link #rawContentType()}。
     *
     */
    public MediaType getContentType() {
        if (Objects.isNull(mContentType)) {
            mContentType = rawContentType().withCharset(getParamCharset().name());
        }
        return mContentType;
    }

    /**
     * 获取直接设置的请求体的内容
     */
    public byte[] getRawBody() {
        return mRawBody;
    }

    /**
     * 将body写入到指定输出流中。
     *
     * <p/>
     *
     * 该方法的执行顺序：
     * <ol>
     *     <li>
     *         首先判断是否有rawBody，有则写入rawBody；没有则转2
     *     </li>
     *     <li>
     *         子类分别实现自己的生成请求体的方法{@link #writeGeneratedBody(java.io.OutputStream)}；没有则转3
     *     </li>
     *     <li>
     *         写入空字节数组。
     *     </li>
     * </ol>
     */
    public synchronized void writeTo(OutputStream out) {
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
            throw Exceptions.toRuntime(e);
        }
    }

    // >>>>>>>>>>>>>>>>>>>>>>
    // write body internal
    /**
     * 判断是否设置了原始数据，如果已经设置，则向参数流中写入原始数据，并返回true；
     *
     * 否则直接返回false
     *
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

    /**
     * {@link #writeTo} 的最后一步
     * @param out
     * @throws Exception
     */
    protected void writeEmpty(OutputStream out) throws Exception {
        out.write(XHttps.NONE_BYTE);
    }

    /**
     * 简单计算内容长度
     *
     * <p/>
     *
     * 默认地，如果设置了{@link #withRawBody(byte[]) rawBody}，返回rawBody的长度。
     *
     * 如果没有设置，则返回0。
     */
    public int calculateByteSize() {
        int rawBodySize = ArraysCompat.getLength(mRawBody);
        if (rawBodySize <= 0) {
            try {
                rawBodySize = ArraysCompat.getLength(generatedBody());
            } catch (Exception e) {}
        }
        return rawBodySize;
    }

    /**
     * 没有设置RawBody的情况下，调用该方法获得实现类生成的body
     */
    protected byte[] generatedBody() throws Exception {
        return XHttps.NONE_BYTE;
    }

    /**
     * 子类本身默认的content type，在用户没有明确设置时，使用该content-type
     */
    public abstract MediaType rawContentType() ;
}
