package androidrubick.xframework.app.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import org.androidrubick.utils.AndroidUtils;

import androidrubick.utils.Objects;
import androidrubick.utils.Preconditions;

/**
 * 工具类：提供{@link IActivityController}相关的静态调用方法
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/4/10 0010.
 */
public class ActivityController {

    private ActivityController() { /* no instance needed */ }

    public static void startActivity(Context context, Intent intent) {
        Preconditions.checkNotNull(intent, "intent should not be null");
        if (!AndroidUtils.isActivityContext(context)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    public static void startActivityForResult(Context context, Intent intent, int requestCode) {
        Preconditions.checkOperation(AndroidUtils.isActivityContext(context), "only activity context can start new activity for result");
        Preconditions.checkNotNull(intent, "intent should not be null");
        Objects.getAs(context, Activity.class).startActivityForResult(intent, requestCode);
    }

    public static void startActivity(Context context, Class<? extends Activity> clz) {
        Preconditions.checkNotNull(clz, "clz should not be null");
        startActivity(context, new Intent(context, clz));
    }

    public static void startActivityForResult(Context context, Class<? extends Activity> clz, int requestCode) {
        Preconditions.checkOperation(AndroidUtils.isActivityContext(context), "only activity context can start new activity for result");
        Preconditions.checkNotNull(clz, "clz should not be null");
        Objects.getAs(context, Activity.class).startActivityForResult(new Intent(context, clz), requestCode);
    }

}