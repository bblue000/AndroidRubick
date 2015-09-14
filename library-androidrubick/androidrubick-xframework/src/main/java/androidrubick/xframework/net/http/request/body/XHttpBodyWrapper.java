package androidrubick.xframework.net.http.request.body;

import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Map;

import androidrubick.net.MediaType;
import androidrubick.utils.Preconditions;

/**
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/9/14 0014.
 *
 * @since 1.0
 */
public class XHttpBodyWrapper<R extends XHttpBody> extends XHttpBody<R> {

    private R mBase;
    public XHttpBodyWrapper(R base) {
        super();
        mBase = Preconditions.checkNotNull(base, "base");
    }

    @Override
    public R param(String key, Object value) {
        return (R) mBase.param(key, value);
    }

    @Override
    public R params(Map<String, ?> params) {
        return (R) mBase.params(params);
    }

    @Override
    public R paramCharset(Charset charset) {
        return (R) mBase.paramCharset(charset);
    }

    @Override
    public R paramCharset(String charsetName) {
        return (R) mBase.paramCharset(charsetName);
    }

    @Override
    public R contentType(String contentType) {
        return (R) mBase.contentType(contentType);
    }

    @Override
    public R contentType(MediaType contentType) {
        return (R) mBase.contentType(contentType);
    }

    @Override
    public R withRawBody(byte[] body) {
        return (R) mBase.withRawBody(body);
    }

    @Override
    public Map<String, Object> getParams() {
        return mBase.getParams();
    }

    @Override
    public Charset getParamCharset() {
        return mBase.getParamCharset();
    }

    @Override
    public MediaType getContentType() {
        return mBase.getContentType();
    }

    @Override
    public byte[] getRawBody() {
        return mBase.getRawBody();
    }

    @Override
    public synchronized void writeTo(OutputStream out) {
        mBase.writeTo(out);
    }

    @Override
    public MediaType rawContentType() {
        return mBase.rawContentType();
    }

    @Override
    public int calculateByteSize() {
        return mBase.calculateByteSize();
    }

}