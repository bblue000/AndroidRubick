package androidrubick.net;

/**
 * Contains constant definitions for the HTTP methods.
 *
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/5/15.
 *
 * @since 1.0
 */
public enum HttpMethod {

    GET("GET"),
    POST("POST") {
        @Override
        public boolean canContainBody() {
            return true;
        }
    },
    PUT("PUT") {
        @Override
        public boolean canContainBody() {
            return true;
        }
    },
    DELETE("DELETE"),
    HEAD("HEAD"),
    OPTIONS("OPTIONS"),
    TRACE("TRACE"),
    PATCH("PATCH") {
        @Override
        public boolean canContainBody() {
            return true;
        }
    };

    private String name;

    HttpMethod(String name) {
        this.name = name;
    }

    /**
     * 获取请求方法名称
     *
     * @since 1.0
     */
    public String getName() {
        return this.name;
    }

    /**
     * 是否能包含请求体
     *
     * @since 1.0
     */
    public boolean canContainBody() {
        return false;
    }

}
