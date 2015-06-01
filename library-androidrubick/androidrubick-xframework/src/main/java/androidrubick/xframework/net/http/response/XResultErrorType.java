package androidrubick.xframework.net.http.response;

/**
 *
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/6/1 0001.
 *
 * @since 1.0
 */
public enum XResultErrorType {

    /**
     * 客户端处理错误
     */
    Client,

    /**
     * 连接时错误
     */
    Connection,

    /**
     * 与服务器交互时错误
     */
    ServerResponse,

    /**
     * 其他类型的错误
     */
    Other

}
