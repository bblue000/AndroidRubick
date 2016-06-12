package androidrubick.io;

import androidrubick.utils.ArraysCompat;
import androidrubick.utils.Objects;
import androidrubick.utils.Preconditions;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.FileOutputStream;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
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
	 * @param closeIns 写入完成或者出错后是否需要关闭输入流
	 * @param out 输出流
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
	public static boolean writeTo(InputStream in, boolean closeIns,
								  OutputStream out, boolean closeOut,
								  byte[] useBuf, IOProgressCallback callback) throws IOException {
		Preconditions.checkNotNull(in, "in");
		Preconditions.checkNotNull(out, "out");
		long readTotal = 0;
		try {
			byte[] buf = null == useBuf ? new byte[IOConstants.DEF_BUFFER_SIZE] : useBuf;
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
	 * 将byte array data写入到输出流
	 *
	 * @param data byte array data
	 * @param out 写入完成或者出错后是否需要关闭输出流
	 * @param closeOut 写入完成或者出错后是否需要关闭输出流
	 * @param useBuf 使用提供的字节数组进行中间传输变量，
	 *               为null时使用{@link IOConstants#DEF_BUFFER_SIZE}长度的字节数组
	 * @param callback IO进度的回调，可为null
	 *
	 * @return true代表写入成功
	 *
	 * @throws IOException IO异常
	 * @throws NullPointerException <code>in</code>或者<code>out</code>为null时抛出
	 */
	public static boolean writeTo(byte[] data,
								  OutputStream out, boolean closeOut,
								  byte[] useBuf, IOProgressCallback callback) throws IOException {
		Preconditions.checkNotNull(data, "data");
		Preconditions.checkNotNull(out, "out");
		if (ArraysCompat.isEmpty(useBuf)) {
			try {
				out.write(data);
				if (null != callback)
					callback.onProgress(data.length, data.length);
				if (null != callback)
					callback.onComplete(data.length);
				return true;
			} finally {
				if (closeOut) close(out);
			}
		} else {
			return IOUtils.writeTo(new ByteArrayInputStream(data), true,
					out, closeOut, useBuf, callback);
		}

	}

	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	// TODO write char
	/**
	 * 将字符读取器写入到字符输出流
	 *
	 * @param in 输入源
	 * @param closeIns 写入完成或者出错后是否需要关闭输入流
	 * @param out 输出对象
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
	public static boolean writeTo(Reader in, boolean closeIns,
								  Writer out, boolean closeOut,
								  char[] useBuf, IOProgressCallback callback) throws IOException {
		Preconditions.checkNotNull(in, "in");
		Preconditions.checkNotNull(out, "out");
		long readTotal = 0;
		try {
			char[] buffer = null == useBuf ? new char[IOConstants.DEF_BUFFER_SIZE] : useBuf;
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
	 * @param closeIns 写入完成或者出错后是否需要关闭输入流
	 * @param closeOut 写入完成或者出错后是否需要关闭输出流
	 * @param charsetName 字符编码方式，将{@code in}以指定编码方式写入到{@code out}中，
	 *                 如果为null，则默认为{@link IOConstants#DEF_CHARSET_NAME}
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
	public static boolean writeTo(InputStream in, boolean closeIns,
								  Writer out, boolean closeOut,
								  String charsetName,
								  char[] useBuf, IOProgressCallback callback) throws IOException {
		InputStreamReader reader;
		charsetName = Objects.getOr(charsetName, IOConstants.DEF_CHARSET_NAME);
		reader = new InputStreamReader(in, charsetName);
		return writeTo(reader, closeIns, out, closeOut, useBuf, callback);
	}

	/**
	 * 将byte array data写入到字符输出流
	 *
	 * @param data byte array data
	 * @param out 写入完成或者出错后是否需要关闭输出流
	 * @param closeOut 写入完成或者出错后是否需要关闭输出流
	 * @param charsetName 字符编码方式，将{@code in}以指定编码方式写入到{@code out}中，
	 *                 如果为null，则默认为{@link IOConstants#DEF_CHARSET_NAME}
	 * @param useBuf 使用提供的字节数组进行中间传输变量，
	 *               为null时使用{@link IOConstants#DEF_BUFFER_SIZE}长度的字节数组
	 * @param callback IO进度的回调，可为null
	 *
	 * @return true代表写入成功
	 *
	 * @throws IOException IO异常
	 * @throws NullPointerException <code>in</code>或者<code>out</code>为null时抛出
	 */
	public static boolean writeTo(byte[] data,
								  Writer out, boolean closeOut,
								  String charsetName,
								  char[] useBuf, IOProgressCallback callback) throws IOException {
		Preconditions.checkNotNull(data, "data");
		Preconditions.checkNotNull(out, "out");
		if (ArraysCompat.isEmpty(useBuf)) {
			try {
				out.write(new String(data, charsetName));
				if (null != callback)
					callback.onProgress(data.length, data.length);
				if (null != callback)
					callback.onComplete(data.length);
				return true;
			} finally {
				if (closeOut) close(out);
			}
		} else {
			return IOUtils.writeTo(new ByteArrayInputStream(data), true,
					out, closeOut, charsetName, useBuf, callback);
		}
	}




	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	// TODO Reader write to OutputStream
	/**
	 * 将字节流写入到字符输出流
	 *
	 * @param closeIns 写入完成或者出错后是否需要关闭输入流
	 * @param closeOut 写入完成或者出错后是否需要关闭输出流
	 * @param charsetName 字符编码方式，将{@code in}以指定编码方式写入到{@code out}中，
	 *                 如果为null，则默认为{@link IOConstants#DEF_CHARSET_NAME}
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
	public static boolean writeTo(Reader in, boolean closeIns,
								  OutputStream out, boolean closeOut,
								  String charsetName,
								  char[] useBuf, IOProgressCallback callback) throws IOException {
		OutputStreamWriter writer;
		charsetName = Objects.getOr(charsetName, IOConstants.DEF_CHARSET_NAME);
		writer = new OutputStreamWriter(out, charsetName);
		return writeTo(in, closeIns, writer, closeOut, useBuf, callback);
	}





	// >>>>>>>>>>>>>>>>>>>>>>
	// TODO toString 系列
	/**
	 * 读取字节流，并输出为String
	 *
	 * @param in 字节流
	 * @param charsetName 编码方式，如果为null，则默认为{@link IOConstants#DEF_CHARSET_NAME}
	 * @param closeIns 写入完成或者出错后是否需要关闭输入流
	 *
	 * @throws java.io.IOException IO异常
	 * @throws java.lang.NullPointerException <code>in</code>为null时抛出
     *
     * @since 1.0
	 */
	public static String inputStreamToString(InputStream in, String charsetName, boolean closeIns)
			throws IOException {
		Preconditions.checkNotNull(in, "in");
		StringWriter writer = new StringWriter();
		writeTo(in, closeIns, writer, true, charsetName, null, null);
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
		Preconditions.checkNotNull(in, "in");
		StringWriter writer = new StringWriter();
		writeTo(in, closeIns, writer, true, null, null);
		return writer.toString();
	}


	public static boolean stringToOutputStream(String src, String charsetName,
											   OutputStream out, boolean closeOut)
			throws IOException  {
		return writeTo(src.getBytes(charsetName), out, closeOut, null, null);
	}


	public static boolean stringToWriter(String src,
										 Writer out, boolean closeOut)
			throws IOException {
		Preconditions.checkNotNull(out, "out");
		try {
			out.write(src);
			return true;
		} finally {
			if (closeOut) close(out);
		}
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
