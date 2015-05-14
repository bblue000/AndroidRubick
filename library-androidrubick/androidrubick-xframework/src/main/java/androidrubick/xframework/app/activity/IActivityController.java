package androidrubick.xframework.app.activity;

import android.app.Activity;
import android.content.Intent;

import java.util.Collection;

/**
 * activity的控制器和管理器
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/3/28 0028.
 */
public interface IActivityController {

    public void startActivity(Intent intent);

    public void startActivityForResult(Intent intent);

    public void finishActivity(Intent intent);

    public Collection<? extends IControlledActivityInfo> getActivityStack();

    public Activity getTopActivity();
}
