package androidrubick.xframework.app.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * 封装了一些步骤化的操作，比如初始化View，初始化监听器，初始化数据
 * <p/>
 * Created by yong01.yin on 2014/11/11.
 */
public abstract class XBaseActivity extends FragmentActivity implements IUIFlow {

    private View mRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        XActivityController.dispatchOnActivityCreated(this, savedInstanceState);
        super.onCreate(savedInstanceState);
        doOnCreate(savedInstanceState);
    }

    protected void doOnCreate(Bundle savedInstanceState) {
        int resId = provideLayoutResId();
        View contentView;
        if (resId > 0) {
            contentView = LayoutInflater.from(this).inflate(resId, (ViewGroup) getWindow().getDecorView(), false);
            mRootView = contentView;
        }
        contentView = provideLayoutView();
        if (null != contentView)  {
            mRootView = contentView;
        }

        if (null != mRootView) {
            setContentView(mRootView);
        }

        // TODO use ButterKnife
        ButterKnife.inject(this, mRootView);
        initView(mRootView, savedInstanceState);
        initListener(mRootView, savedInstanceState);
        initData(mRootView, savedInstanceState);
    }

    @Override
    public View provideLayoutView() {
        return null;
    }

    @Override
    public View getRootView() {
        return mRootView;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        XActivityController.dispatchOnActivityNewIntent(this, intent);
        super.onNewIntent(intent);
    }

    @Override
    protected void onRestart() {
        XActivityController.dispatchOnActivityRestarted(this);
        super.onRestart();
    }

    @Override
    protected void onStart() {
        XActivityController.dispatchOnActivityStarted(this);
        super.onStart();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        XActivityController.dispatchOnActivityRestoreInstanceState(this, savedInstanceState);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        XActivityController.dispatchOnActivityResumed(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        XActivityController.dispatchOnActivityPaused(this);
        super.onPause();
    }

    @Override
    protected void onStop() {
        XActivityController.dispatchOnActivityStopped(this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        XActivityController.dispatchOnActivityDestroyed(this);
        super.onDestroy();
    }

    @Override
    public void startActivity(Class<? extends Activity> clz) {
        startActivity(new Intent(this, clz));
    }

    @Override
    public void startActivityForResult(Class<? extends Activity> clz, int requestCode) {
        startActivityForResult(new Intent(this, clz), requestCode);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        XActivityController.dispatchStartActivityForResult(intent, requestCode);
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    public void finish() {
        XActivityController.dispatchFinishActivity(this);
        super.finish();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.isTracking()
                && !event.isCanceled()) {
            doBackPressFinish();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void doBackPressFinish() {
        if (validateBackPressFinish()) {
            onBackPressed();
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p/>
     *
     * 默认返回true
     */
    @Override
    public boolean validateBackPressFinish() {
        return true;
    }
}


