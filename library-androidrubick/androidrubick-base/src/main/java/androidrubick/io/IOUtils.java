package androidrubick.io;

import androidrubick.utils.FrameworkLog;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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

	private final static String TAG = IOUtils.class.getSimpleName();
	
	private IOUtils() { }

	// >>>>>>>>>>>>>>>>>>>>>>
	// writeTo 系列
	/**
	 * 将输入流写入到输出流
	 * @param in 输入源
	 * @param out 输出流
	 * @param close 写入完成或者出错后是否需要关闭所有流
	 * @return true代表写入成功
	 * @throws java.io.IOException IO异常
     *
     * @since 1.0
	 */
	public static boolean writeTo(InputStream in, OutputStream out,
			boolean close) throws IOException {
		return writeTo(in, out, new byte[IOConstants.DEF_BUFFER_SIZE], close);
	}

	public static boolean writeTo(InputStream in, OutputStream out,
								  byte[] useBuf,
								  boolean close) throws IOException {
		if (null == in) {
			throw new NullPointerException("in is null!");
		}
		if (null == out) {
			throw new NullPointerException("out is null!");
		}
		try {
			byte[] buf = useBuf;
			int len = -1;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			return true;
		} catch (IOException e) {
			FrameworkLog.e(TAG, "writeTo Exception : " + e.getMessage());
			throw e;
		} finally {
			if (close) {
				close(out);
				close(in);
			}
		}
	}

	/**
	 * 将字符读取器写入到字符输出流
	 * @param close 写入完成或者出错后是否需要关闭所有流
	 * @return true代表写入成功
	 * @throws java.io.IOException IO异常
     *
     * @since 1.0
	 */
	public static boolean writeTo(Reader in, Writer out,
			boolean close) throws IOException {
		return writeTo(in, out, new char[IOConstants.DEF_BUFFER_SIZE], close);
	}

	public static boolean writeTo(Reader in, Writer out,
								  char[] useBuf,
								  boolean close) throws IOException
	{
		if (null == in) {
			throw new NullPointerException("in is null!");
		}
		if (null == out) {
			throw new NullPointerException("out is null!");
		}
		try {
			char[] buffer = useBuf;
			int n = 0;
			while (-1 != (n = in.read(buffer))) {
				out.write(buffer, 0, n);
			}
			return true;
		} catch (IOException e) {
			FrameworkLog.e(TAG, "writeTo Exception : " + e.getMessage());
			throw e;
		} finally {
			if (close) {
				close(out);
				close(in);
			}
		}
	}

	/**
	 * 将字节流写入到字符输出流
	 * @param encoding 字符编码方式
	 * @param close 写入完成或者出错后是否需要关闭所有流
	 * @return true代表写入成功
	 * @throws java.io.IOException IO异常
     *
     * @since 1.0
	 */
	public static boolean writeTo(InputStream in, Writer out,
			String encoding, boolean close) throws IOException {
		InputStreamReader reader;
		if (encoding == null) {
			encoding = IOConstants.DEF_CHARSET;
		}
		reader = new InputStreamReader(in, encoding);
		return writeTo(reader, out, close);
	}

	public static boolean writeTo(InputStream in, Writer out,
								  String encoding,
								  char[] useBuf,
								  boolean close) throws IOException {
		InputStreamReader reader;
		if (encoding == null) {
			encoding = IOConstants.DEF_CHARSET;
		}
		reader = new InputStreamReader(in, encoding);
		return writeTo(reader, out, useBuf, close);
	}

	// >>>>>>>>>>>>>>>>>>>>>>
	// toString 系列
	/**
	 * 读取字节流，并输出为String
	 * @param in 字节流
	 * @throws java.io.IOException IO异常
     *
     * @since 1.0
	 */
	public static String inputStreamToString(InputStream in)
			throws IOException {
		return inputStreamToString(in, IOConstants.DEF_CHARSET);
	}

	/**
	 * 读取字节流，并输出为String
	 * @param in 字节流
	 * @param encoding 编码方式
	 * @throws java.io.IOException IO异常
     *
     * @since 1.0
	 */
	public static String inputStreamToString(InputStream in, String encoding)
			throws IOException {
		if (null == in) {
			throw new NullPointerException("in is null!");
		}
		if (null == encoding) {
			encoding = IOConstants.DEF_CHARSET;
		}
		StringWriter writer = null;
		try {
			writer = new StringWriter();
			writeTo(in, writer, encoding, true);
			return writer.toString();
		} catch (IOException e) {
			FrameworkLog.e(TAG, "inputStreamToString Exception : " + e.getMessage());
			throw e;
		}
	}

	// >>>>>>>>>>>>>>>>>>>>>>
	// close
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
    		close.close();
		} catch (IOException ignore) { }
    }
}
