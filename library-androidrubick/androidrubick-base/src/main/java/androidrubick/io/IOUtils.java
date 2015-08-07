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
	 *
	 * @return true代表写入成功
	 *
	 * @throws java.io.IOException IO异常
     *
     * @since 1.0
	 */
	public static boolean writeTo(InputStream in, OutputStream out,
								  boolean closeIns, boolean closeOut) throws IOException {
		return writeTo(in, out, closeIns, closeOut, null);
	}

	/**
	 * 将输入流写入到输出流
	 *
	 * @param in 输入源
	 * @param out 输出流
	 * @param closeIns 写入完成或者出错后是否需要关闭输入流
	 * @param closeOut 写入完成或者出错后是否需要关闭输出流
	 * @param callback IO进度的回调
	 *
	 * @return true代表写入成功
	 *
	 * @throws java.io.IOException IO异常
     *
     * @since 1.0
	 */
	public static boolean writeTo(InputStream in, OutputStream out,
								  boolean closeIns, boolean closeOut,
								  IOProgressCallback callback) throws IOException {
		return writeTo(in, out, new byte[IOConstants.DEF_BUFFER_SIZE], closeIns, closeOut, callback);
	}

	/**
	 * 将输入流写入到输出流
	 *
	 * @param in 输入源
	 * @param out 输出流
	 * @param useBuf 使用提供的字节数组进行中间传输变量
	 * @param closeIns 写入完成或者出错后是否需要关闭输入流
	 * @param closeOut 写入完成或者出错后是否需要关闭输出流
	 *
	 * @return true代表写入成功
	 *
	 * @throws java.io.IOException IO异常
	 *
	 * @since 1.0
	 */
	public static boolean writeTo(InputStream in, OutputStream out,
								  byte[] useBuf,
								  boolean closeIns, boolean closeOut) throws IOException {
		return writeTo(in, out, useBuf, closeIns, closeOut, null);
	}

	/**
	 * 将输入流写入到输出流
	 *
	 * @param in 输入源
	 * @param out 输出流
	 * @param useBuf 使用提供的字节数组进行中间传输变量
	 * @param closeIns 写入完成或者出错后是否需要关闭输入流
	 * @param closeOut 写入完成或者出错后是否需要关闭输出流
	 * @param callback IO进度的回调
	 *
	 * @return true代表写入成功
	 *
	 * @throws java.io.IOException IO异常
	 *
	 * @since 1.0
	 */
	public static boolean writeTo(InputStream in, OutputStream out,
								  byte[] useBuf,
								  boolean closeIns, boolean closeOut,
								  IOProgressCallback callback) throws IOException {
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


	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	// TODO write char
	/**
	 * 将字符读取器写入到字符输出流
	 *
	 * @param in 输入源
	 * @param out 输出对象
	 * @param closeIns 写入完成或者出错后是否需要关闭输入流
	 * @param closeOut 写入完成或者出错后是否需要关闭输出流
	 *
	 * @return true代表写入成功
	 *
	 * @throws java.io.IOException IO异常
     *
     * @since 1.0
	 */
	public static boolean writeTo(Reader in, Writer out,
								  boolean closeIns, boolean closeOut) throws IOException {
		return writeTo(in, out, closeIns, closeOut, null);
	}

	/**
	 * 将字符读取器写入到字符输出流
	 *
	 * @param in 输入源
	 * @param out 输出对象
	 * @param closeIns 写入完成或者出错后是否需要关闭输入流
	 * @param closeOut 写入完成或者出错后是否需要关闭输出流
	 * @param callback IO进度的回调
	 *
	 * @return true代表写入成功
	 *
	 * @throws java.io.IOException IO异常
     *
     * @since 1.0
	 */
	public static boolean writeTo(Reader in, Writer out,
								  boolean closeIns, boolean closeOut,
								  IOProgressCallback callback) throws IOException {
		return writeTo(in, out, new char[IOConstants.DEF_BUFFER_SIZE], closeIns, closeOut, callback);
	}

	/**
	 * 将字符读取器写入到字符输出流
	 *
	 * @param in 输入源
	 * @param out 输出对象
	 * @param useBuf 使用提供的字节数组进行中间传输变量
	 * @param closeIns 写入完成或者出错后是否需要关闭输入流
	 * @param closeOut 写入完成或者出错后是否需要关闭输出流
	 *
	 * @return true代表写入成功
	 *
	 * @throws java.io.IOException IO异常
	 *
	 * @since 1.0
	 */
	public static boolean writeTo(Reader in, Writer out,
								  char[] useBuf,
								  boolean closeIns, boolean closeOut) throws IOException
	{
		return writeTo(in, out, useBuf, closeIns, closeOut, null);
	}

	/**
	 * 将字符读取器写入到字符输出流
	 *
	 * @param in 输入源
	 * @param out 输出对象
	 * @param useBuf 使用提供的字节数组进行中间传输变量
	 * @param closeIns 写入完成或者出错后是否需要关闭输入流
	 * @param closeOut 写入完成或者出错后是否需要关闭输出流
	 * @param callback IO进度的回调
	 *
	 * @return true代表写入成功
	 *
	 * @throws java.io.IOException IO异常
	 *
	 * @since 1.0
	 */
	public static boolean writeTo(Reader in, Writer out,
								  char[] useBuf,
								  boolean closeIns, boolean closeOut,
								  IOProgressCallback callback) throws IOException
	{
		if (null == in) {
			throw new NullPointerException("in is null!");
		}
		if (null == out) {
			throw new NullPointerException("out is null!");
		}
		long readTotal = 0;
		try {
			char[] buffer = useBuf;
			int n;
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
	 *
	 * @return true代表写入成功
	 *
	 * @throws java.io.IOException IO异常
     *
     * @since 1.0
	 */
	public static boolean writeTo(InputStream in, Writer out,
								  String encoding,
								  boolean closeIns, boolean closeOut) throws IOException {
		return writeTo(in, out, encoding, closeIns, closeOut, null);
	}

	/**
	 * 将字节流写入到字符输出流
	 *
	 * @param encoding 字符编码方式，将{@code in}以指定编码方式写入到{@code out}中，
	 *                 如果为null，则默认为{@link IOConstants#DEF_CHARSET}
	 * @param closeIns 写入完成或者出错后是否需要关闭输入流
	 * @param closeOut 写入完成或者出错后是否需要关闭输出流
	 * @param callback IO进度的回调
	 *
	 * @return true代表写入成功
	 *
	 * @throws java.io.IOException IO异常
	 *
	 * @since 1.0
	 */
	public static boolean writeTo(InputStream in, Writer out,
								  String encoding,
								  boolean closeIns, boolean closeOut,
								  IOProgressCallback callback) throws IOException {
		return writeTo(in, out, encoding, new char[IOConstants.DEF_BUFFER_SIZE], closeIns, closeOut, callback);
	}

	/**
	 * 将字节流写入到字符输出流
	 *
	 * @param encoding 字符编码方式，将{@code in}以指定编码方式写入到{@code out}中，
	 *                 如果为null，则默认为{@link IOConstants#DEF_CHARSET}
	 * @param useBuf 使用提供的字节数组进行中间传输变量
	 * @param closeIns 写入完成或者出错后是否需要关闭输入流
	 * @param closeOut 写入完成或者出错后是否需要关闭输出流
	 *
	 * @return true代表写入成功
	 *
	 * @throws java.io.IOException IO异常
	 *
	 * @since 1.0
	 */
	public static boolean writeTo(InputStream in, Writer out,
								  String encoding,
								  char[] useBuf,
								  boolean closeIns, boolean closeOut) throws IOException {
		return writeTo(in, out, encoding, useBuf, closeIns, closeOut, null);
	}

	/**
	 * 将字节流写入到字符输出流
	 *
	 * @param encoding 字符编码方式，将{@code in}以指定编码方式写入到{@code out}中，
	 *                 如果为null，则默认为{@link IOConstants#DEF_CHARSET}
	 * @param useBuf 使用提供的字节数组进行中间传输变量
	 * @param closeIns 写入完成或者出错后是否需要关闭输入流
	 * @param closeOut 写入完成或者出错后是否需要关闭输出流
	 * @param callback IO进度的回调
	 *
	 * @return true代表写入成功
	 *
	 * @throws java.io.IOException IO异常
	 *
	 * @since 1.0
	 */
	public static boolean writeTo(InputStream in, Writer out,
								  String encoding,
								  char[] useBuf,
								  boolean closeIns, boolean closeOut,
								  IOProgressCallback callback) throws IOException {
		InputStreamReader reader;
		if (null == encoding) {
			encoding = IOConstants.DEF_CHARSET;
		}
		reader = new InputStreamReader(in, encoding);
		return writeTo(reader, out, useBuf, closeIns, closeOut, callback);
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
	 *
	 * @return true代表写入成功
	 *
	 * @throws java.io.IOException IO异常
	 *
	 * @since 1.0
	 */
	public static boolean writeTo(Reader in, OutputStream out,
								  String encoding,
								  boolean closeIns, boolean closeOut) throws IOException {
		return writeTo(in, out, encoding, closeIns, closeOut, null);
	}

	/**
	 * 将字节流写入到字符输出流
	 *
	 * @param encoding 字符编码方式，将{@code in}以指定编码方式写入到{@code out}中，
	 *                 如果为null，则默认为{@link IOConstants#DEF_CHARSET}
	 * @param closeIns 写入完成或者出错后是否需要关闭输入流
	 * @param closeOut 写入完成或者出错后是否需要关闭输出流
	 * @param callback IO进度的回调
	 *
	 * @return true代表写入成功
	 *
	 * @throws java.io.IOException IO异常
	 *
	 * @since 1.0
	 */
	public static boolean writeTo(Reader in, OutputStream out,
								  String encoding,
								  boolean closeIns, boolean closeOut,
								  IOProgressCallback callback) throws IOException {
		return writeTo(in, out, encoding, new char[IOConstants.DEF_BUFFER_SIZE], closeIns, closeOut, callback);
	}

	/**
	 * 将字节流写入到字符输出流
	 *
	 * @param encoding 字符编码方式，将{@code in}以指定编码方式写入到{@code out}中，
	 *                 如果为null，则默认为{@link IOConstants#DEF_CHARSET}
	 * @param useBuf 使用提供的字节数组进行中间传输变量
	 * @param closeIns 写入完成或者出错后是否需要关闭输入流
	 * @param closeOut 写入完成或者出错后是否需要关闭输出流
	 *
	 * @return true代表写入成功
	 *
	 * @throws java.io.IOException IO异常
	 *
	 * @since 1.0
	 */
	public static boolean writeTo(Reader in, OutputStream out,
								  String encoding,
								  char[] useBuf,
								  boolean closeIns, boolean closeOut) throws IOException {
		return writeTo(in, out, encoding, useBuf, closeIns, closeOut, null);
	}

	/**
	 * 将字节流写入到字符输出流
	 *
	 * @param encoding 字符编码方式，将{@code in}以指定编码方式写入到{@code out}中，
	 *                 如果为null，则默认为{@link IOConstants#DEF_CHARSET}
	 * @param useBuf 使用提供的字节数组进行中间传输变量
	 * @param closeIns 写入完成或者出错后是否需要关闭输入流
	 * @param closeOut 写入完成或者出错后是否需要关闭输出流
	 * @param callback IO进度的回调
	 *
	 * @return true代表写入成功
	 *
	 * @throws java.io.IOException IO异常
	 *
	 * @since 1.0
	 */
	public static boolean writeTo(Reader in, OutputStream out,
								  String encoding,
								  char[] useBuf,
								  boolean closeIns, boolean closeOut,
								  IOProgressCallback callback) throws IOException {
		OutputStreamWriter writer;
		if (null == encoding) {
			encoding = IOConstants.DEF_CHARSET;
		}
		writer = new OutputStreamWriter(out, encoding);
		return writeTo(in, writer, useBuf, closeIns, closeOut, callback);
	}





	// >>>>>>>>>>>>>>>>>>>>>>
	// TODO toString 系列
	/**
	 * 读取字节流，并输出为String
	 *
	 * @param in 字节流
	 * @param encoding 编码方式，如果为null，则默认为{@link IOConstants#DEF_CHARSET}
	 *
	 * @throws java.io.IOException IO异常
     *
     * @since 1.0
	 */
	public static String inputStreamToString(InputStream in, String encoding, boolean closeIns)
			throws IOException {
		if (null == in) {
			throw new NullPointerException("in is null!");
		}

		StringWriter writer = new StringWriter();
		writeTo(in, writer, encoding, closeIns, true);
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
