package androidrubicktest;

import java.io.UnsupportedEncodingException;

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

}
