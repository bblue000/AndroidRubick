package androidrubicktest;

import org.apache.http.HttpResponse;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import androidrubick.io.IOUtils;
import androidrubick.net.HttpMethod;
import androidrubick.utils.StandardSystemProperty;
import androidrubick.xframework.net.http.request.XHttpRequestBuilder;
import androidrubick.xframework.net.http.request.body.XHttpBody;
import androidrubick.xframework.net.http.response.XHttpResponseHolder;

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
            XHttpResponseHolder response = XHttpRequestBuilder.newInstance()
                    .url("http://www.baidu.com/")
                    .build()
                    .performRequest();

            String result = IOUtils.inputStreamToString(response.getEntity().getContent(), "utf8");
            System.out.println(StandardSystemProperty.HTTP_AGENT.value());
            System.out.println("content type = " + response.getContentType());
            System.out.println("content charset = " + response.getContentCharset());
            System.out.println("content encoding = " + response.getContentEncoding());
//            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void testPost() {
        System.out.println("============testPost");
        try {
            XHttpResponseHolder response = XHttpRequestBuilder.newInstance()
                    .url("http://www.baidu.com/")
                    .method(HttpMethod.POST)
                    .param("q", "我是中国人")
                    .param("word", "我是中国人")
                    .param("wd", "我是中国人")
                    .withBody(XHttpBody.newUrlEncodedBody().param("wd", "我是中国人").build())
                    .build()
                    .performRequest();

            String result = IOUtils.inputStreamToString(response.getEntity().getContent(), "utf8");
            System.out.println("content type = " + response.getContentType());
            System.out.println("content charset = " + response.getContentCharset());
            System.out.println("content encoding = " + response.getContentEncoding());
//            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
