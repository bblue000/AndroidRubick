package androidrubick.xframework.app.ui.component;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidrubick.utils.Preconditions;
import androidrubick.xframework.app.ui.XActivityCallbackDispatcher;
import androidrubick.xframework.app.ui.XActivityController;

/**
 * 封装了一些步骤化的操作，比如初始化View，初始化监听器，初始化数据
 *
 * <p/>
 *
 * Created by yong01.yin on 2014/11/11.
 */
public abstract class XBaseActivity extends FragmentActivity implements XUIComponent {

    private View mRootView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        XActivityCallbackDispatcher.dispatchOnActivityCreated(this, savedInstanceState);
        super.onCreate(savedInstanceState);
        doOnCreateView(savedInstanceState);
        doInitOnViewCreated(getRootView(), savedInstanceState);
    }

    /**
     * 创建contentView，并调用{@link #setContentView} 设置视图。
     */
    protected void doOnCreateView(Bundle savedInstanceState) {
        mRootView = provideLayoutView(getLayoutInflater(), (ViewGroup) getWindow().getDecorView(), savedInstanceState);
        Preconditions.checkNotNull(mRootView, "root view is null, ensure provideLayoutView is right!");
        setContentView(mRootView);
    }

    protected void doInitOnViewCreated(View rootView, Bundle savedInstanceState) {
        initView(rootView, savedInstanceState);
        initListener(rootView, savedInstanceState);
        initData(rootView, savedInstanceState);
    }

    @Override
    public View provideLayoutView(LayoutInflater inflater, ViewGroup container,
                                  Bundle savedInstanceState) {
        int resId = provideLayoutResId();
        if (resId > 0) {
            return inflater.inflate(resId, container, false);
        }
        return null;
    }

    @Override
    public View getRootView() {
        return mRootView;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        XActivityCallbackDispatcher.dispatchOnActivityNewIntent(this, intent);
        super.onNewIntent(intent);
    }

    @Override
    protected void onRestart() {
        XActivityCallbackDispatcher.dispatchOnActivityRestarted(this);
        super.onRestart();
    }

    @Override
    protected void onStart() {
        XActivityCallbackDispatcher.dispatchOnActivityStarted(this);
        super.onStart();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        XActivityCallbackDispatcher.dispatchOnActivityRestoreInstanceState(this, savedInstanceState);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        XActivityCallbackDispatcher.dispatchOnActivityResumed(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        XActivityCallbackDispatcher.dispatchOnActivityPaused(this);
        super.onPause();
    }

    @Override
    protected void onStop() {
        XActivityCallbackDispatcher.dispatchOnActivityStopped(this);
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        XActivityCallbackDispatcher.dispatchOnActivitySaveInstanceState(this, outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        XActivityCallbackDispatcher.dispatchOnActivityDestroyed(this);
        super.onDestroy();
    }

    @Override
    public void startActivity(Class<? extends Activity> clz) {
        XActivityController.startActivity(clz);
    }

    @Override
    public void startActivity(Intent intent) {
        XActivityController.startActivity(intent);
    }

    @Override
    public void startActivityForResult(Class<? extends Activity> clz, int requestCode) {
        startActivityForResult(new Intent(this, clz), requestCode);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        XActivityCallbackDispatcher.dispatchStartActivityForResult(intent, requestCode);
        super.startActivityForResult(intent, requestCode);
    }

    /**
     * 这是后续Fragment版本增加的方法，super.startActivityFromFragment(fragment, intent, requestCode)
     * 调用的是super.startActivityForResult()，也就是原始Activity类的startActivityForResult
     * （此处是{@link android.support.v4.app.FragmentActivity v4包的FragmentActivity}）。
     *
     * <p/>
     *
     * 如果是直接继承自Activity：
     * <pre>
     * Activity :startActivityForResult
     *          :startActivityFromFragment
     *      |- this class   :startActivityForResult
     *                          |- super.startActivityForResult
     *                      :startActivityFromFragment
     *                          |- super.startActivityForResult
     * </pre>
     *
     * <p/>
     *
     * 以下是继承自FragmentActivity的实现：
     * <pre>
     * Activity
     *  |- FragmentActivity :startActivityForResult
     *                          |- super.startActivityForResult
     *                      :startActivityFromFragment
     *                          |- super.startActivityForResult
     *      |- this class   :startActivityForResult
     *                          |- super.startActivityForResult
     *                      :startActivityFromFragment
     *                          |- super.startActivityForResult
     * </pre>
     */
    @Override
    public void startActivityFromFragment(Fragment fragment, Intent intent, int requestCode) {
        XActivityCallbackDispatcher.dispatchStartActivityForResult(intent, requestCode);
        super.startActivityFromFragment(fragment, intent, requestCode);
    }

    @Override
    public void finish() {
        XActivityCallbackDispatcher.dispatchFinishActivity(this);
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

    /**
     * {@inheritDoc}
     *
     * <p/>
     *
     * 默认返回当前Activity
     */
    @Override
    public Object getUITag() {
        return this;
    }
}


