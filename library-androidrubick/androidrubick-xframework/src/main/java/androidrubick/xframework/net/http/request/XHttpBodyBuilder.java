package androidrubick.xframework.net.http.request;

import java.util.Map;

import androidrubick.utils.Preconditions;
import androidrubick.xframework.net.http.XHttp;

/**
 * 封装POST等含有请求体的请求方法创建请求体的过程
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/5/17 0017.
 */
public class XHttpBodyBuilder {

    public static final String URLENCODED_FORM_DATA = "application/x-www-form-urlencoded";
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";

    public static XHttpBodyBuilder newUrlEncodedBody() {
        return new XHttpBodyBuilder();
    }

    public static XHttpBodyBuilder newUrlEncodedBody(Map<String, String> params) {
        return new XHttpBodyBuilder();
    }

    public static XHttpBodyBuilder newMultipart() {
        return new XHttpBodyBuilder(MULTIPART_FORM_DATA);
    }

    public static XHttpBodyBuilder newMultipart(Map<String, String> params) {
        return new XHttpBodyBuilder(MULTIPART_FORM_DATA);
    }

    private String mContentType = XHttp.DEFAULT_OUTPUT_CONTENT_TYPE;
    private byte[] mBody;
    private XHttpBodyBuilder(){}
    private XHttpBodyBuilder(String contentType) {
        mContentType = Preconditions.checkNotNull(contentType, "contentType is null");
    }

    /**
     * 获取内容类型
     */
    public String getContentType() {
        return mContentType;
    }

    /**
     * 获取内容类型
     */
    public byte[] getBody() {
        return mBody;
    }
}
