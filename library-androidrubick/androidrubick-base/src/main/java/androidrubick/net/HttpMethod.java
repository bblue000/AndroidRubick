package androidrubick.net;

/**
 * Contains constant definitions for the HTTP methods.
 *
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/5/15.
 */
public enum HttpMethod {

    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE"),
    HEAD("HEAD"),
    OPTIONS("OPTIONS"),
    TRACE("TRACE"),
    PATCH("PATCH");

    private String name;
    private HttpMethod(String name) {
        this.name = name;
    }

    /**
     * 获取
     */
    public String getName() {
        return this.name;
    }

}
