package androidrubicktest;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;

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

    public static void testUrlEncodedBody() {
        System.out.println("============testUrlEncodedBody");
        System.out.println(XHttpBody.newUrlEncodedBody().paramEncoding("utf8").build().getContentType());
        System.out.println(new String(XHttpBody.newUrlEncodedBody().paramEncoding("utf8").param("userName", "帅哥")
                .param("version", 1.0f).build().getBody()));
    }

    public static void testJsonBody() {
        System.out.println("============testJsonBody");
        System.out.println(XHttpBody.newJsonBody().paramEncoding("utf8").build().getContentType());
        System.out.println(new String(XHttpBody.newJsonBody().paramEncoding("utf8").param("userName", "帅哥")
                .param("version", 1.0f).build().getBody()));
        System.out.println(new String(XHttpBody.newJsonBody().withRawJson("{}").paramEncoding("utf8").build().getBody()));
    }

    public static void testMultipart() {
        System.out.println("============testMultipart");
        System.out.println(XHttpBody.newMultipartBody().paramEncoding("utf8").build().getContentType());
        System.out.println(new String(XHttpBody.newMultipartBody().paramEncoding("utf8").param("userName", "帅哥")
                .param("version", 1.0f).build().getBody()));
        System.out.println(new String(XHttpBody.newMultipartBody().rawField("file", "苍老师.avi".getBytes()).paramEncoding("utf8").build().getBody()));

        File dir = new File(".");
        File file = null;
        for (File t : dir.listFiles()) {
            if (t.isFile()) {
                file = t;
                continue;
            }
        }
        System.out.println(file.getAbsolutePath());
        System.out.println(new String(XHttpBody.newMultipartBody()
                .file("file", file)
                .paramEncoding("utf8").build().getBody()));
    }

}
