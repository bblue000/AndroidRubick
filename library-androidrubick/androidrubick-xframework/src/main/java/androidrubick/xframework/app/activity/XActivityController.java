package androidrubick.xframework.app.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import org.androidrubick.app.BaseApplication;
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
public class XActivityController {

    private XActivityController() { /* no instance needed */ }

    /**
     * Launch a new activity
     */
    public static void startActivity(Intent intent) {
        Preconditions.checkNotNull(intent, "intent should not be null");
        Context context = BaseApplication.getAppContext();
        // TODO 实际上是不是new task不单单是靠这个Flag决定的
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * Launch an activity for which you would like a result when it finished.
     */
    public static void startActivityForResult(Context context, Intent intent, int requestCode) {
        Preconditions.checkOperation(AndroidUtils.isActivityContext(context), "only activity context can start new activity for result");
        Preconditions.checkNotNull(intent, "intent should not be null");
        Objects.getAs(context, Activity.class).startActivityForResult(intent, requestCode);
    }

    /**
     * Launch a new activity of target {@code clz}
     */
    public static void startActivity(Class<? extends Activity> clz) {
        Preconditions.checkNotNull(clz, "clz should not be null");
        Context context = BaseApplication.getAppContext();
        startActivity(new Intent(context, clz));
    }

    /**
     * Launch an activity(of target {@code clz}) for which you would like a result when it finished.
     */
    public static void startActivityForResult(Context context, Class<? extends Activity> clz, int requestCode) {
        Preconditions.checkOperation(AndroidUtils.isActivityContext(context), "only activity context can start new activity for result");
        Preconditions.checkNotNull(clz, "clz should not be null");
        Objects.getAs(context, Activity.class).startActivityForResult(new Intent(context, clz), requestCode);
    }

    /**
     *
     * @param activity
     */
    public static void finishActivity(Activity activity) {
        activity.finish();
    }

    /**
     *
     * @param <T>
     * @return
     */
    public static <T extends Activity>T getTopActivity() {
        return null;
    }

}