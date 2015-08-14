package androidrubick.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * somthing
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/8/5 0005.
 *
 * @since 1.0
 */
public abstract class IOBuilder {

    public static IOBuilder from(InputStream ins) {

    }

    public static IOBuilder from(Reader ins, String charset) {

    }

    public static IOBuilder from(File file) throws IOException {
        return from(FileUtils.openFileInput(file));
    }

    private IOBuilder() {

    }

    protected boolean isChar() {

    }

    protected abstract boolean enableCharTransfer(String charset);

    protected byte[] mByteBuf;
    protected char[] mCharBuf;
    protected boolean mCloseIn;
    protected boolean mCloseOut;
    protected IOProgressCallback mIOProgressCallback;

    /**
     * 结束（成功或出现异常）之后，是否关闭输入源
     */
    public IOBuilder closeIn(boolean value) {
        mCloseIn = value;
        return this;
    }

    /**
     * 结束（成功或出现异常）之后，是否关闭输出源
     */
    public IOBuilder closeOut(boolean value) {
        mCloseOut = value;
        return this;
    }

    public IOBuilder callback(IOProgressCallback callback) {
        mIOProgressCallback = callback;
        return this;
    }

    /**
     * 必要的条件设置完后，最后一步调用
     * @param out 输出源
     */
    public boolean to(OutputStream out) throws IOException {
        if (null == in) {
            throw new NullPointerException("in is null!");
        }
        if (null == out) {
            throw new NullPointerException("out is null!");
        }
        long readTotal = 0;
        try {
            byte[] buf = useBuf;
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
                if (null != callback)
                    callback.onProgress(len, readTotal += len);
            }
            if (null != callback)
                callback.onComplete(readTotal);
            return true;
        } finally {
            if (closeOut) close(out);
            if (closeIns) close(in);
        }
    }

    /**
     * 必要的条件设置完后，最后一步调用
     * @param out 输出源
     */
    public boolean to(Writer out, String charset) throws IOException {

    }

    /**
     * 必要的条件设置完后，最后一步调用
     * @param file 目标文件
     * @param createIfUnexists 如果文件不存在是否
     * @param append
     * @return
     * @throws IOException
     */
    public boolean to(File file, boolean createIfUnexists, boolean append) throws IOException {
        return to(FileUtils.openFileOutput(file, createIfUnexists, append));
    }

    /**
     * 必要的条件设置完后，最后一步调用，输出String
     */
    public String toString(String charset) throws IOException {
        StringWriter writer = new StringWriter();
        closeOut(true);
        if (to(writer, charset)) {
            return writer.toString();
        }
        return null;
    }

}
