package androidrubick.xframework.app.activity;

import android.app.Activity;
import android.content.Intent;

import org.androidrubick.app.BaseActivity;

/**
 * something
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/7/9.
 *
 * @since 1.0
 */
public abstract class XBaseActivity extends BaseActivity {

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        XActivityController.startActivityForResult(this, intent, requestCode);
    }

    @Override
    public void startActivityForResult(Class<? extends Activity> clz, int requestCode) {
        super.startActivityForResult(clz, requestCode);
    }
}
