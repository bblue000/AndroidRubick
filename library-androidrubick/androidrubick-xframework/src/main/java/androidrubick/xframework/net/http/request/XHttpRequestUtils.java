package androidrubick.xframework.net.http.request;

import androidrubick.utils.Objects;

/**
 * 帮助类
 *
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/5/19.
 *
 * @since 1.0
 */
public class XHttpRequestUtils {

    private XHttpRequestUtils() {}

    /**
     * 向目标URL后追加参数
     */
    public static String appendQuery(String baseUrl, String query) {
        if (Objects.isEmpty(query)) {
            return baseUrl;
        }
        String url = baseUrl;
        int indexOfQueryStart = baseUrl.lastIndexOf("?");
        if (indexOfQueryStart < 0) {
            url += "?";
        } else if (indexOfQueryStart != baseUrl.length() - 1 && !baseUrl.endsWith("&")) {
            url += "&";
        }
        url += query;
        return url;
    }

}
