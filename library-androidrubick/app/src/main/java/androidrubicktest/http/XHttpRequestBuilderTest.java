package androidrubicktest.http;

import java.io.File;
import java.io.UnsupportedEncodingException;

import androidrubick.xframework.net.http.XHttps;
import androidrubick.xframework.net.http.request.body.XHttpBody;

/**
 * something
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/5/22.
 *
 * @since 1.0
 */
public class XHttpRequestBuilderTest {

    public static void main(String[] args) throws UnsupportedEncodingException {
        testUrlEncodedBody();
        testJsonBody();
        testMultipart();
    }

    public static void testUrlEncodedBody() throws UnsupportedEncodingException {
        System.out.println("============testUrlEncodedBody");
        System.out.println(XHttpBody.newUrlEncodedBody().paramCharset("utf8").getContentType());
        System.out.println(XHttps.bodyToString(XHttpBody.newUrlEncodedBody()
                .paramCharset("utf8")
                .param("userName", "帅哥")
                .param("version", 1.0f)));
    }

    public static void testJsonBody() throws UnsupportedEncodingException {
        System.out.println("============testJsonBody");
        System.out.println(XHttpBody.newJsonBody().paramCharset("utf8").getContentType());
        System.out.println(XHttps.bodyToString(XHttpBody.newJsonBody()
                .paramCharset("utf8")
                .param("userName", "帅哥")
                .param("version", 1.0f)));
        System.out.println(XHttps.bodyToString(XHttpBody.newJsonBody()
                .withRawJson("{}").paramCharset("utf8")));
    }

    public static void testMultipart() throws UnsupportedEncodingException {
        System.out.println("============testMultipart");
        System.out.println(XHttpBody.newMultipartBody().paramCharset("utf8").getContentType());
        System.out.println(XHttps.bodyToString(XHttpBody.newMultipartBody()
                .paramCharset("utf8")
                .param("userName", "帅哥")
                .param("version", 1.0f)));
        System.out.println(XHttps.bodyToString(XHttpBody.newMultipartBody()
                .rawField("file", "苍老师.avi".getBytes()).paramCharset("utf8")));

        File dir = new File(".");
        File file = null;
        for (File t : dir.listFiles()) {
            if (t.isFile()) {
                file = t;
                break;
            }
        }
        System.out.println(file.getAbsolutePath());
        System.out.println(XHttps.bodyToString(XHttpBody.newMultipartBody()
                .file("file", file)
                .paramCharset("utf8")));
    }

}
