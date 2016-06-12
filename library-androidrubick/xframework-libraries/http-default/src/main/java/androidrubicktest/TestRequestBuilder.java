package androidrubicktest;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

import androidrubick.xframework.net.http.XHttps;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/29.
 */
public class TestRequestBuilder {

    private TestRequestBuilder() { /* no instance needed */ }

    public static void main(String args[]) {

        //UnknownHostException

//        XHttps.requestSync(XHttps.newReqBuilder().url("http://www.baidu.com").post(RequestBody.create(null, "")).build(), new StringTransformer() {
//            @Override
//            public void ok(String data) {
//                System.out.println(data);
//            }
//
//            @Override
//            public void fail(Request request, Response response, IOException e) {
//                System.out.println(response);
//                e.printStackTrace();
//            }
//        });



//        XHttps.requestSync(XHttps.newReqBuilder().url("http://localhost/test").build(), new JsonTransformer() {
//            @Override
//            public void ok(Object data) {
//                System.out.println(data);
//            }
//
//            @Override
//            public void fail(Request request, Response response, IOException e) {
//                e.printStackTrace();
//            }
//        });




//        XHttps.requestSync(XHttps.newReqBuilder().url("http://localhost/test").build(), new JSONTokenerTransformer() {
//            @Override
//            public void ok(Object data) {
//                System.out.println(data);
//            }
//
//            @Override
//            public void fail(Request request, Response response, IOException e) {
//                e.printStackTrace();
//            }
//        });

    }

}
