package org.androidrubick.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import org.androidrubick.utils.AndroidUtils;

import androidrubick.utils.Objects;

/**
 * somthing
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/3/24 0024.
 */
public class ActivityController {

    public static void startActivity(Context context, Intent intent) {
        Objects.checkNotNull(intent, "intent should not be null");
        if (!AndroidUtils.isActivityContext(context)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    public static void startActivityForResult(Context context, Intent intent, int requestCode) {
        Objects.checkOperation(AndroidUtils.isActivityContext(context), "only activity context can start new activity for result");
        Objects.checkNotNull(intent, "intent should not be null");
        Objects.getAs(context, Activity.class).startActivityForResult(intent, requestCode);
    }

    public static void startActivity(Context context, Class<? extends Activity> clz) {
        Objects.checkNotNull(clz, "clz should not be null");
        startActivity(context, new Intent(context, clz));
    }

    public static void startActivityForResult(Context context, Class<? extends Activity> clz, int requestCode) {
        Objects.checkOperation(AndroidUtils.isActivityContext(context), "only activity context can start new activity for result");
        Objects.checkNotNull(clz, "clz should not be null");
        Objects.getAs(context, Activity.class).startActivityForResult(new Intent(context, clz), requestCode);
    }

}
