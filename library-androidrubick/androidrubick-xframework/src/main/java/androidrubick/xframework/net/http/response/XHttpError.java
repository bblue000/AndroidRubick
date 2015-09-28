package androidrubick.xframework.net.http.response;

import java.io.PrintStream;
import java.io.PrintWriter;

import androidrubick.text.Strings;
import androidrubick.utils.Objects;
import androidrubick.utils.Preconditions;
import androidrubick.xbase.util.XLog;
import androidrubick.xframework.net.http.spi.XHttpRequestService;
import androidrubick.xframework.net.http.request.XHttpRequest;

/**
 *
 * HTTP请求执行过程中的异常，该异常没有捕获客户端代码导致的运行时异常，
 *
 * 这些异常理所应当让客户端知道，并自己处理。
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/9/10.
 *
 * @see XHttpRequestService#performRequest(androidrubick.xframework.net.http.request.XHttpRequest)
 */
public class XHttpError extends Exception {

    private static final String TAG = XHttpError.class.getSimpleName();

    public enum Type {
        /**
         * 连接超时
         */
        Timeout,

        /**
         * 无法建立连接，尝试连接过程中出错；
         */
        NoConnection,

        /**
         * 建立了连接，但是没有获得有效的请求行；
         *
         * 或者能获得请求行（status line），但是获取其他内容时出错（多为网络原因）；
         */
        Network,

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
        * for 5xx status codes（100...199也会包括在其中，如果{@link XHttpRequest#isAutoRedirect()}为false，
         * 300...399也会包含其中）
        */
        Server,

        /**
         * 其他未知的异常；
         *
         * <p/>
         *
         * other unknown runtime IO exception
         */
        Other
    }

    private Type mType = Type.Other;
    private int mStatusCode = -1;
    private String mMessage;
    private Throwable mRawCause;
    private XHttpResponse mResponse;
    /**
     * Constructs a new {@code XHttpError} with the current stack trace
     * and the specified response, maybe null.
     */
    public XHttpError(XHttpResponse response) {
        super();
        this.mResponse = response;
        if (!Objects.isNull(this.mResponse)) {
            this.mStatusCode = this.mResponse.getStatusCode();
            this.mMessage = this.mResponse.getStatusMessage();
        }
    }

    /**
     * Constructs a new {@code XHttpError} with the current stack trace
     * and the specified response, maybe null.
     */
    public XHttpError(Type type, XHttpResponse response) {
        this(response);
        this.mType = type;
    }

    /**
     * Constructs a new {@code XHttpError} with the current stack trace
     * and the specified detail message.
     *
     * @param response
     *            the specified response.
     * @param rawCause
     *            the specified rawCause.
     */
    public XHttpError(XHttpResponse response, Throwable rawCause) {
        this(response);
        initCause(rawCause);
        this.mRawCause = rawCause;
    }

    /**
     * Constructs a new {@code XHttpError} with the current stack trace
     * and the specified detail message.
     *
     * @param response
     *            the specified response.
     * @param rawCause
     *            the specified rawCause.
     */
    public XHttpError(Type type, XHttpResponse response, Throwable rawCause) {
        this(response, rawCause);
        this.mType = type;
    }

    /**
     * 如果没有手动设置，将使用{@link XHttpResponse#getStatusMessage()}；
     *
     * 如果{@link XHttpResponse response}为null，则返回{@link #getCause()}的{@link Throwable#getMessage()}
     */
    @Override
    public String getMessage() {
        return Strings.isEmpty(mMessage) && !Objects.isNull(mRawCause) ? mRawCause.getMessage() : mMessage;
    }

    @Override
    public Throwable getCause() {
        return this.mRawCause;
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
        mType = Preconditions.checkNotNull(type, "type");
    }

    /**
     * 如果还没有请求到响应行，返回-1
     */
    public int getStatusCode() {
        return mStatusCode;
    }

    /**
     * 如果该错误是建立连接之后，由于读取过程，或者服务器返回的status code不再[200, 300)之间等原因，
     *
     * 则返回响应对象，该对象已经关闭，不能读取内容，但可以获取状态栏，头信息等。
     *
     * <p/>
     *
     * type为
     * {@link androidrubick.xframework.net.http.response.XHttpError.Type#Auth} 或
     * {@link androidrubick.xframework.net.http.response.XHttpError.Type#Server}时，
     * 一定已经生成响应对象，其他类型的错误没有保证。
     */
    public XHttpResponse getResponse() {
        return mResponse;
    }

    /**
     * 设置状态值
     */
    public void setStatusCode(int code) {
        mStatusCode = code;
    }

    /**
     * 代码设置具体信息
     */
    public void setMessage(String detailMessage) {
        mMessage = detailMessage;
    }

    /**
     * 设置原始异常
     */
    public void setRawCause(Throwable rawCause) {
        mRawCause = rawCause;
    }

    @Override
    public void printStackTrace() {
        if (Objects.isNull(mRawCause)) {
            super.printStackTrace();
            XLog.e(TAG, toString());
        } else {
            mRawCause.printStackTrace();
        }
    }

    @Override
    public void printStackTrace(PrintStream err) {
        if (Objects.isNull(mRawCause)) {
            super.printStackTrace(err);
        } else {
            mRawCause.printStackTrace(err);
        }
    }

    @Override
    public void printStackTrace(PrintWriter err) {
        if (Objects.isNull(mRawCause)) {
            super.printStackTrace(err);
        } else {
            mRawCause.printStackTrace(err);
        }
    }

    @Override
    public void setStackTrace(StackTraceElement[] trace) {
        if (Objects.isNull(mRawCause)) {
            super.setStackTrace(trace);
        } else {
            mRawCause.setStackTrace(trace);
        }
    }

    @Override
    public StackTraceElement[] getStackTrace() {
        if (Objects.isNull(mRawCause)) {
            return super.getStackTrace();
        } else {
            return mRawCause.getStackTrace();
        }
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("type", getType())
                .add("statusCode", getStatusCode())
                .add("message", getMessage())
                .toString();
    }
}
