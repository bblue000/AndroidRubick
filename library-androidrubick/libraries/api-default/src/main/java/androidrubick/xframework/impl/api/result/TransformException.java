package androidrubick.xframework.impl.api.result;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

/**
 *
 *
 * <p/>
 *
 * Created by Yin Yong on 16/3/21.
 */
public class TransformException extends IOException {

    public TransformException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransformException(Throwable cause) {
        super(cause);
    }


    @Override
    public StackTraceElement[] getStackTrace() {
        return getCause().getStackTrace();
    }

    @Override
    public void setStackTrace(StackTraceElement[] trace) {
        getCause().setStackTrace(trace);
    }

    @Override
    public void printStackTrace() {
        getCause().printStackTrace();
    }

    @Override
    public void printStackTrace(PrintStream err) {
        getCause().printStackTrace(err);
    }

    @Override
    public void printStackTrace(PrintWriter err) {
        getCause().printStackTrace(err);
    }

    @Override
    public String getMessage() {
        return getCause().getMessage();
    }

    @Override
    public Throwable getCause() {
        return getCause().getCause();
    }

    /**
     * no fill
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

}
