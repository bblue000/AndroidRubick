package androidrubick.xframework.net.http.request;

import java.util.Map;

import androidrubick.xframework.net.http.XHttp;

/**
 * somthing
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/5/17 0017.
 */
public class XHttpBodyBuilder {

    public static final String FORM_DATA = "application/x-www-form-urlencoded";
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";

    public static XHttpBodyBuilder from(Map<String, String> params) {
        return new XHttpBodyBuilder(params);
    }

    private String mContentType = XHttp.DEFAULT_OUTPUT_CONTENT_TYPE;
    private byte[] mBody;
    private XHttpBodyBuilder(Map<String, String> params) {

    }

    private XHttpBodyBuilder(String contentType, byte[] body) {

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
