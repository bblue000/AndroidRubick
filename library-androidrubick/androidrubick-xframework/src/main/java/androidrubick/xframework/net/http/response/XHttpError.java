package androidrubick.xframework.net.http.response;

import androidrubick.xframework.net.http.request.XHttpReq;
import androidrubick.xframework.net.http.spi.XHttpRequestService;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/10.
 *
 * @see XHttpRequestService#performRequest(XHttpReq)
 */
public class XHttpError extends Exception {

    public enum Type {
        /**
         * 连接超时
         */
        Timeout,

        /**
         * 无法建立连接
         */
        NoConnection,

        /**
         * 没有权限访问
         *
         * <p/>
         *
         * for 401 & 403 status codes
         *
         */
        Auth,

        /**
        * for 5xx status codes
        */
        Server,

        /**
         * 建立了连接，且能获得请求行（status line），但是获取内容时出错，多为网络原因
         */
        Network,

        /**
         * 其他未知的异常；
         *
         * <p/>
         *
         * other unknown runtime exception
         */
        Other
    }

    private Type mType;
    private int mStatusCode = -1;
    private Throwable mRawCause;
    private XHttpRes mResponse;
    /**
     * Constructs a new {@code XHttpError} with the current stack trace
     * and the specified detail message.
     *
     * @param detailMessage
     *            the detail message for this exception.
     */
    public XHttpError(String detailMessage) {
        super(detailMessage);
    }

    /**
     * Constructs a new {@code XHttpError} with the current stack trace,
     * the specified detail message and the specified cause.
     *
     * @param detailMessage
     *            the detail message for this exception.
     * @param throwable
     *            the cause of this exception.
     */
    public XHttpError(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
        mRawCause = null;
    }

    /**
     * Constructs a new {@code XHttpError} with the current stack trace
     * and the specified cause.
     *
     * @param throwable
     *            the cause of this exception.
     */
    public XHttpError(Throwable throwable) {
        super(throwable);
        mRawCause = throwable;
    }

    /**
     * 获取错误类型
     */
    public Type getType() {
        return mType;
    }

    /**
     * 设置错误类型
     */
    public void setType(Type type) {
        mType = type;
    }

    /**
     * 如果还没有请求到响应行，返回-1
     */
    public int getStatusCode() {
        return mStatusCode;
    }

    /**
     * 设置状态值
     */
    public void setStatusCode(int code) {
        mStatusCode = code;
    }

    /**
     * 如果该错误是由于Exception抛出，返回原始的异常；没有则返回null
     */
    public Throwable getRawCause() {
        return mRawCause;
    }

    /**
     * 设置原始异常
     */
    public void setRawCause(Throwable rawCause) {
        mRawCause = rawCause;
    }

    /**
     * 如果该错误是建立连接之后，由于读取过程，或者服务器返回的status code不再[200, 300)之间等原因，
     *
     * 则返回响应对象
     */
    public XHttpRes getResponse() {
        return mResponse;
    }

    /**
     * 如果该错误是建立连接之后，由于读取过程，或者服务器返回的status code不再[200, 300)之间等原因，
     *
     * 则会有响应对象，此时传入，让外部异常处理。
     */
    public void setResponse(XHttpRes response) {
        this.mResponse = response;
    }

}
