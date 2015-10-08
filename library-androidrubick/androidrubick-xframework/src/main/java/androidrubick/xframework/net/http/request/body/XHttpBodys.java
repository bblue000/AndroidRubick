package androidrubick.xframework.net.http.request.body;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import androidrubick.collect.CollectionsCompat;
import androidrubick.net.HttpUrls;
import androidrubick.text.Strings;
import androidrubick.xbase.util.JsonParser;
import androidrubick.xbase.util.XLog;
import androidrubick.xframework.net.http.XHttps;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/10/8.
 */
public class XHttpBodys {

    private XHttpBodys() { /* no instance needed */ }

    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // normal common
    /**
     * 将<code>bodyString</code>转为byte数组；
     *
     * 如果<code>bodyString</code>为空，转为<code>new byte[0]</code>，
     * 如果<code>charsetName</code>为空或者不被不支持，使用系统默认的charset，
     * 其他情况则使用指定的编码返回byte数组。
     *
     * @param bodyString Unicode字符串
     * @param charsetName 字符串编码
     * @return 字节码
     */
    public static byte[] toByteArray(String bodyString, String charsetName) {
        if (Strings.isEmpty(bodyString)) {
            return XHttps.NONE_BYTE;
        }
        if (Strings.isEmpty(charsetName)) {
            // use the system default charset
            return bodyString.getBytes();
        }
        try {
            return bodyString.getBytes(charsetName);
        } catch (UnsupportedEncodingException e) {
            XLog.e(XHttps.TAG, "XHttpBodys#toByteArray", e);
            // use the system default charset
            return bodyString.getBytes();
        }
    }

    /**
     * 将<code>bodyString</code>转为byte数组，并写入<code>out</code>；
     *
     * 如果<code>bodyString</code>为空，转为<code>new byte[0]</code>，
     * 如果<code>charsetName</code>为空或者不被不支持，使用系统默认的charset，
     * 其他情况则使用指定的编码返回byte数组。
     *
     * @param out 输出流
     * @param bodyString Unicode字符串
     * @param charsetName 字符串编码
     * @throws IOException
     */
    public static void writeTo(OutputStream out, String bodyString, String charsetName) throws IOException {
        out.write(toByteArray(bodyString, charsetName));
    }

    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // json
    /**
     * 将<code>parameters</code>转为json字符串
     * @param parameters 参数
     * @return json字符串
     */
    public static String toJsonString(Map<String, ?> parameters) {
        if (CollectionsCompat.isEmpty(parameters)) {
            return Strings.EMPTY;
        }
        return JsonParser.toJsonString(parameters);
    }

    /**
     * 将<code>parameters</code>转为json字符串，再转为byte数组
     * @param parameters 参数
     * @return json字符串再转为byte数组
     */
    public static byte[] toJsonByteArray(Map<String, ?> parameters, String charsetName) {
        return toByteArray(toJsonString(parameters), charsetName);
    }

    /**
     * 将<code>parameters</code>转为json字符串，再转为byte数组，并写入<code>out</code>；
     *
     * @param out 输出流
     * @param parameters 参数
     * @param charsetName 字符串编码
     * @throws IOException
     */
    public static void writeJsonTo(OutputStream out, Map<String, ?> parameters,
                                   String charsetName) throws IOException {
        out.write(toJsonByteArray(parameters, charsetName));
    }

    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // url encoded
    public static String toUrlEncodedString(Map<String, ?> parameters, String charsetName) {
        if (CollectionsCompat.isEmpty(parameters)) {
            return Strings.EMPTY;
        }
        return HttpUrls.toUrlEncodedQueryString(parameters, charsetName);
    }

    public static byte[] toUrlEncodedByteArray(Map<String, ?> parameters, String charsetName) {
        return toByteArray(toUrlEncodedString(parameters, charsetName), charsetName);
    }
}
