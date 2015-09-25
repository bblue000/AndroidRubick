package androidrubicktest.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import androidrubick.io.IOUtils;
import androidrubick.net.HttpMethod;
import androidrubick.utils.StandardSystemProperty;
import androidrubick.xframework.net.http.request.XHttpRequest;
import androidrubick.xframework.net.http.request.body.XHttpBody;
import androidrubick.xframework.net.http.response.XHttpError;
import androidrubick.xframework.net.http.response.XHttpResponse;

/**
 * something
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/5/22.
 *
 * @since 1.0
 */
public class XHttpRequestTest {

    public static void main(String[] args) throws UnsupportedEncodingException {
        testGet();
    }

    public static void testGet() {
        System.out.println("============testGet");
        try {
            XHttpResponse response = XHttpRequest.get("http://www.baidu.com/")
                    .performRequest();

            String result = IOUtils.inputStreamToString(response.getContent(), "utf8", true);
            System.out.println(StandardSystemProperty.HTTP_AGENT.value());
            System.out.println("content type = " + response.getContentType());
            System.out.println("content charset = " + response.getContentCharset());
            System.out.println("content encoding = " + response.getContentEncoding());
            System.out.println("content length = " + response.getContentLength());
//            System.out.println(result);
        } catch (XHttpError e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void testPost() {
        System.out.println("============testPost");
        try {
            XHttpResponse response = XHttpRequest.post("http://www.baidu.com/")
                    .method(HttpMethod.POST)
                    .withBody(XHttpBody.newUrlEncodedBody().param("wd", "我是中国人"))
                    .performRequest();

            String result = IOUtils.inputStreamToString(response.getContent(), "utf8", true);
            System.out.println("content type = " + response.getContentType());
            System.out.println("content charset = " + response.getContentCharset());
            System.out.println("content encoding = " + response.getContentEncoding());
            System.out.println("content length = " + response.getContentLength());
//            System.out.println(result);
        } catch (XHttpError e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
