package androidrubicktest.api;

import android.util.Log;

import org.json.JSONObject;

import androidrubick.xframework.api.XAPI;
import androidrubick.xframework.api.XAPICallback;
import androidrubick.xframework.api.XAPIHolder;
import androidrubick.xframework.api.XAPIStatus;

/**
 * something
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/7/15.
 *
 * @since 1.0
 */
public class XAPITest {

    public static void test() {
        XAPIHolder holder = XAPI.get(APIConfig.GET_AREA_DATA, null, JSONObject.class, new XAPICallback() {
            @Override
            public void onSuccess(Object result, XAPIStatus status) {
                Log.d("yytest", "onSuccess result = " + result);
                System.gc();
            }

            @Override
            public void onFailed(XAPIStatus status) {
                Log.d("yytest", "onFailed status = " + status);
            }

            @Override
            public void onCanceled(Object result) {
                Log.d("yytest", "onCanceled result = " + result);
            }
        });
        holder.execute();
        holder.execute();
        holder.execute();
        holder.execute();
        holder.execute();
        holder.execute();
    }

    public static void testHolder() {
        XAPIHolder holder = XAPI.get(APIConfig.GET_AREA_DATA, null, JSONObject.class, new XAPICallback() {
            @Override
            public void onSuccess(Object result, XAPIStatus status) {
                Log.d("yytest", "testHolder onSuccess result = " + result);
            }

            @Override
            public void onFailed(XAPIStatus status) {
                Log.d("yytest", "testHolder onFailed status = " + status);
            }

            @Override
            public void onCanceled(Object result) {
                Log.d("yytest", "testHolder onCanceled result = " + result);
            }
        });

        holder.cancel(true);
        holder.execute();
        holder.execute();
        holder.execute();
        holder.execute();
        holder.execute();
        holder.execute();
    }

}
