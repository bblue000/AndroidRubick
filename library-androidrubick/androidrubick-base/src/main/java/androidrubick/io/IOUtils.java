package androidrubick.io;

import androidrubick.utils.Objects;

import java.io.Closeable;
import java.io.FileOutputStream;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * 工具类，封装简单的I/O转换操作
 * 
 * @author Yin Yong
 *
 * @since 1.0
 *
 */
public class IOUtils {

	private IOUtils() { }

	// >>>>>>>>>>>>>>>>>>>>>>
	// TODO writeTo 系列
	/**
	 * 将输入流写入到输出流
	 *
	 * @param in 输入源
	 * @param out 输出流
	 * @param closeIns 写入完成或者出错后是否需要关闭输入流
	 * @param closeOut 写入完成或者出错后是否需要关闭输出流
	 * @param useBuf 使用提供的字节数组进行中间传输变量，
	 *               为null时使用{@link IOConstants#DEF_BUFFER_SIZE}长度的字节数组
	 * @param callback IO进度的回调，可为null
	 *
	 * @return true代表写入成功
	 *
	 * @throws java.io.IOException IO异常
	 * @throws java.lang.NullPointerException <code>in</code>或者<code>out</code>为null时抛出
	 *
	 * @since 1.0
	 */
	public static boolean writeTo(InputStream in, OutputStream out,
								  boolean closeIns, boolean closeOut,
								  byte[] useBuf,
								  IOProgressCallback callback) throws IOException {
		if (null == in) {
			throw new NullPointerException("in is null!");
		}
		if (null == out) {
			throw new NullPointerException("out is null!");
		}
		long readTotal = 0;
		try {
			byte[] buf = useBuf; int len;
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


	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	// TODO write char
	/**
	 * 将字符读取器写入到字符输出流
	 *
	 * @param in 输入源
	 * @param out 输出对象
	 * @param closeIns 写入完成或者出错后是否需要关闭输入流
	 * @param closeOut 写入完成或者出错后是否需要关闭输出流
	 * @param useBuf 使用提供的字节数组进行中间传输变量，
	 *               为null时使用{@link IOConstants#DEF_BUFFER_SIZE}长度的字符数组
	 * @param callback IO进度的回调，可为null
	 *
	 * @return true代表写入成功
	 *
	 * @throws java.io.IOException IO异常
	 * @throws java.lang.NullPointerException <code>in</code>或者<code>out</code>为null时抛出
	 *
	 * @since 1.0
	 */
	public static boolean writeTo(Reader in, Writer out,
								  boolean closeIns, boolean closeOut,
								  char[] useBuf,
								  IOProgressCallback callback) throws IOException {
		if (null == in) {
			throw new NullPointerException("in is null!");
		}
		if (null == out) {
			throw new NullPointerException("out is null!");
		}
		long readTotal = 0;
		try {
			char[] buffer = useBuf; int n;
			while (-1 != (n = in.read(buffer))) {
				out.write(buffer, 0, n);
				if (null != callback)
					callback.onProgress(n, readTotal += n);
			}
			if (null != callback)
				callback.onComplete(readTotal);
			return true;
		} finally {
			if (closeOut) close(out);
			if (closeIns) close(in);
		}
	}






	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	// TODO InputStream write to Writer
	/**
	 * 将字节流写入到字符输出流
	 *
	 * @param encoding 字符编码方式，将{@code in}以指定编码方式写入到{@code out}中，
	 *                 如果为null，则默认为{@link IOConstants#DEF_CHARSET}
	 * @param closeIns 写入完成或者出错后是否需要关闭输入流
	 * @param closeOut 写入完成或者出错后是否需要关闭输出流
	 * @param useBuf 使用提供的字节数组进行中间传输变量，
	 *               为null时使用{@link IOConstants#DEF_BUFFER_SIZE}长度的字符数组
	 * @param callback IO进度的回调，可为null
	 *
	 * @return true代表写入成功
	 *
	 * @throws java.io.IOException IO异常
	 * @throws java.lang.NullPointerException <code>in</code>或者<code>out</code>为null时抛出
	 *
	 * @since 1.0
	 */
	public static boolean writeTo(InputStream in, Writer out,
								  String encoding,
								  boolean closeIns, boolean closeOut,
								  char[] useBuf,
								  IOProgressCallback callback) throws IOException {
		InputStreamReader reader;
		if (null == encoding) {
			encoding = IOConstants.DEF_CHARSET;
		}
		reader = new InputStreamReader(in, encoding);
		return writeTo(reader, out, closeIns, closeOut, useBuf, callback);
	}






	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	// TODO Reader write to OutputStream
	/**
	 * 将字节流写入到字符输出流
	 *
	 * @param encoding 字符编码方式，将{@code in}以指定编码方式写入到{@code out}中，
	 *                 如果为null，则默认为{@link IOConstants#DEF_CHARSET}
	 * @param closeIns 写入完成或者出错后是否需要关闭输入流
	 * @param closeOut 写入完成或者出错后是否需要关闭输出流
	 * @param useBuf 使用提供的字节数组进行中间传输变量，
	 *               为null时使用{@link IOConstants#DEF_BUFFER_SIZE}长度的字符数组
	 * @param callback IO进度的回调，可为null
	 *
	 * @return true代表写入成功
	 *
	 * @throws java.io.IOException IO异常
	 * @throws java.lang.NullPointerException <code>in</code>或者<code>out</code>为null时抛出
	 *
	 * @since 1.0
	 */
	public static boolean writeTo(Reader in, OutputStream out,
								  String encoding,
								  boolean closeIns, boolean closeOut,
								  char[] useBuf,
								  IOProgressCallback callback) throws IOException {
		OutputStreamWriter writer;
		if (null == encoding) {
			encoding = IOConstants.DEF_CHARSET;
		}
		writer = new OutputStreamWriter(out, encoding);
		return writeTo(in, writer, closeIns, closeOut, useBuf, callback);
	}





	// >>>>>>>>>>>>>>>>>>>>>>
	// TODO toString 系列
	/**
	 * 读取字节流，并输出为String
	 *
	 * @param in 字节流
	 * @param encoding 编码方式，如果为null，则默认为{@link IOConstants#DEF_CHARSET}
	 * @param closeIns 写入完成或者出错后是否需要关闭输入流
	 *
	 * @throws java.io.IOException IO异常
	 * @throws java.lang.NullPointerException <code>in</code>为null时抛出
     *
     * @since 1.0
	 */
	public static String inputStreamToString(InputStream in, String encoding, boolean closeIns)
			throws IOException {
		if (null == in) {
			throw new NullPointerException("in is null!");
		}
		StringWriter writer = new StringWriter();
		writeTo(in, writer, encoding, closeIns, true, null, null);
		return writer.toString();
	}

	/**
	 * 读取字节流，并输出为String
	 *
	 * @param in 字符流
	 * @param closeIns 写入完成或者出错后是否需要关闭输入流
	 *
	 * @throws java.io.IOException IO异常
	 * @throws java.lang.NullPointerException <code>in</code>为null时抛出
     *
     * @since 1.0
	 */
	public static String readerToString(Reader in, boolean closeIns)
			throws IOException {
		if (null == in) {
			throw new NullPointerException("in is null!");
		}
		StringWriter writer = new StringWriter();
		writeTo(in, writer, closeIns, true, null, null);
		return writer.toString();
	}






	// >>>>>>>>>>>>>>>>>>>>>>
	// TODO close
	/**
	 * 关闭指定的可关闭的I/O
	 * @param close 一个实现了Closeable的I/O对象
     *
     * @since 1.0
	 */
	public static void close(Closeable close) {
    	if (null == close) {
    		return ;
    	}
    	try {
    		if (close instanceof Flushable) {
				Objects.getAs(close, Flushable.class)
						.flush();
			}
		} catch (IOException ignore) { }
    	try {
    		if (close instanceof FileOutputStream) {
				Objects.getAs(close, FileOutputStream.class)
						.getFD().sync();
			}
		} catch (IOException ignore) { }
    	try {
    		close.close();
		} catch (IOException ignore) { }
    }
}
