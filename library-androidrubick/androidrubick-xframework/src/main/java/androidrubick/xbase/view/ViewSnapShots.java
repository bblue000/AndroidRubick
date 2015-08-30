package androidrubick.xbase.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;
import android.webkit.WebView;

import java.lang.reflect.Method;

/**
 * somthing
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/8/30 0030.
 *
 * @since 1.0
 */
public class ViewSnapShots {

    private ViewSnapShots() { /* no instance needed */ }

    public static Bitmap createSnapshot(Context context) throws Throwable{
        if (!(context instanceof Activity)) {
            return null;
        }
        Activity activity = (Activity) context;
        // View是你需要截图的View
        View view = activity.getWindow().getDecorView();
        try {
            return createSnapshot1(context, view, Bitmap.Config.ARGB_8888);
        } catch (Throwable e) {
            if (e instanceof OutOfMemoryError) {
                try {
                    return createSnapshot1(context, view, Bitmap.Config.RGB_565);
                } catch (Throwable e2) {
                    throw e;
                }
            }
            try {
                return createSnapshot2(context, view, Bitmap.Config.ARGB_8888);
            } catch (Throwable e2) {
                if (e instanceof OutOfMemoryError) {
                    try {
                        return createSnapshot2(context, view, Bitmap.Config.RGB_565);
                    } catch (Throwable e3) {
                        throw e3;
                    }
                }
            }
        }
        return null;
    }

    private static Bitmap createSnapshot1(Context context, View view,
                                          Bitmap.Config config) throws Throwable{
        Class<?>[] arrayOfClass1 = new Class[3];
        arrayOfClass1[0] = Bitmap.Config.class;
        Class<?> localClass1 = Integer.TYPE;
        arrayOfClass1[1] = localClass1;
        Class<?> localClass2 = Boolean.TYPE;
        arrayOfClass1[2] = localClass2;
        Object[] arrayOfObject1 = new Object[3];
        arrayOfObject1[0] = config;
        Integer localInteger1 = Integer.valueOf(0);
        arrayOfObject1[1] = localInteger1;
        Boolean localBoolean = Boolean.valueOf(false);
        arrayOfObject1[2] = localBoolean;
        Method method = View.class.getDeclaredMethod("createSnapshot",
                arrayOfClass1);
        method.setAccessible(true);
        return (Bitmap) method.invoke(view, arrayOfObject1);
    }

    private static Bitmap createSnapshot2(Context context, View view,
                                          Bitmap.Config config) throws Throwable{
        Class<?>[] arrayOfClass2 = new Class[2];
        arrayOfClass2[0] = Bitmap.Config.class;
        Class<?> localClass3 = Integer.TYPE;
        arrayOfClass2[1] = localClass3;
        Object[] arrayOfObject2 = new Object[2];
        arrayOfObject2[0] = config;
        Integer localInteger2 = Integer.valueOf(0);
        arrayOfObject2[1] = localInteger2;
        Method method = View.class.getDeclaredMethod("createSnapshot",
                arrayOfClass2);
        method.setAccessible(true);
        return (Bitmap) method.invoke(view, arrayOfObject2);
    }

    /**
     * 该方法耗时跟网页的大小有关，具有不确定性
     * @throws Throwable 网页太大，多次尝试获取无法成功时将抛出异常
     */
    public static Bitmap createWebViewFullCut(Context context, WebView webView) throws Throwable {
        if (null == context || null == webView) {
            return null;
        }
        int width = webView.getWidth();
        int height = (int) (webView.getScale() * webView.getContentHeight());
        try {
            Bitmap bm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bm);
            webView.draw(canvas);
            return bm;
        } catch (Throwable e) {
            if (e instanceof OutOfMemoryError) {
                try {
                    Bitmap bm = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                    Canvas canvas = new Canvas(bm);
                    webView.draw(canvas);
                    return bm;
                } catch (Throwable e2) {
                    int times = 1;
                    float scale = 1.0f;
                    while (true) {
                        scale = scale * 0.8f;
                        int scaledH = (int)(scale * height);
                        int scaledW = (int)(scale * width);
                        try {
                            Bitmap bm = Bitmap.createBitmap(scaledW, scaledH,
                                    Bitmap.Config.ARGB_8888);
                            Canvas canvas = new Canvas(bm);
                            canvas.scale(scale, scale);
                            webView.draw(canvas);
                            return bm;
                        } catch (Throwable e3) {
                            if (times > 5) {
                                throw e3;
                            }
                            times ++;
                        }
                    }
                }
            }
        }
        return null;
    }

    public static Bitmap createWebViewVisibleCut(Context context, WebView webView) {
        if (null == context || null == webView) {
            return null;
        }
        boolean isDrawingCacheEnabled = webView.isDrawingCacheEnabled();
        if (!isDrawingCacheEnabled) {
            webView.setDrawingCacheEnabled(true);
        }
        // create a copy
        Bitmap bm = Bitmap.createBitmap(webView.getDrawingCache());
        if (!isDrawingCacheEnabled) {
            webView.setDrawingCacheEnabled(false);
        }
        return bm;
    }
}