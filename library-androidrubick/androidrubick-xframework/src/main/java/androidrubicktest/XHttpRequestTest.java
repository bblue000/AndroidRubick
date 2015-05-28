package androidrubicktest;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import androidrubick.xframework.net.http.request.XHttpRequestBuilder;
import androidrubick.xframework.net.http.request.body.XHttpBody;

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
            XHttpRequestBuilder.newInstance()
                    .url("http://www.baidu.com")
                    .build()
                    .performRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
