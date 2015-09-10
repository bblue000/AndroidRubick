package androidrubick.xframework.app.ui.component;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidrubick.utils.Preconditions;
import androidrubick.xframework.app.XGlobals;
import androidrubick.xframework.app.ui.XActivityController;

/**
 * Created by yong01.yin on 2014/11/11.
 */
public abstract class XBaseFragment extends Fragment implements XUIComponent {

    private boolean mFirstTimeBuilt = true;
    // 根View，外部提供的View——Activity的根View其实是内置的FrameLayout
    private View mRootView;

    /**
     * 持有该Fragment的FragmentActivity的引用
     */
    protected FragmentActivity mFragmentActivity;

    /**
     * Called to do initial creation of a fragment.  This is called after
     * {@link #onAttach(Activity)} and before
     * {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     *
     * <p>Note that this can be called while the fragment's activity is
     * still in the process of being created.  As such, you can not rely
     * on things like the activity's content view hierarchy being initialized
     * at this point.  If you want to do work once the activity itself is
     * created, see {@link #onActivityCreated(Bundle)}.
     *
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 不推荐再重写此方法.所以加上了final<br/><br/>
     * Not recommend that you override this method.<br/><br/>
     * use {@link #onViewCreated(View, Bundle)} to do some thing
     * @see {@link #onViewCreated(View, Bundle)}
     */
    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container,
                                   Bundle savedInstanceState) {
        if (needBuild()) {
            mRootView = doCreateView(inflater, container, savedInstanceState);
        }
        return mRootView;
    }

    /**
     * Called immediately after {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     * has returned, but before any saved state has been restored in to the view.
     * This gives subclasses a chance to initialize themselves once
     * they know their view hierarchy has been completely created.  The fragment's
     * view hierarchy is not however attached to its parent at this point.
     * @param view The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
    }

    /**
     * Called when the fragment's activity has been created and this
     * fragment's view hierarchy instantiated.  It can be used to do final
     * initialization once these pieces are in place, such as retrieving
     * views or restoring state.  It is also useful for fragments that use
     * {@link #setRetainInstance(boolean)} to retain their instance,
     * as this callback tells the fragment when it is fully associated with
     * the new activity instance.  This is called after {@link #onCreateView}
     * and before {@link #onViewStateRestored(Bundle)}.
     *
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public final void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        doOnActivityCreated(savedInstanceState);
    }

    /**
     * 创建contentView，并返回视图。
     */
    protected View doCreateView(LayoutInflater inflater, ViewGroup container,
                                  Bundle savedInstanceState) {
        View rootView = provideLayoutView(inflater, container, savedInstanceState);
        Preconditions.checkNotNull(rootView, "root view is null, ensure provideLayoutView is right!");
        return rootView;
    }

    protected void doOnActivityCreated(Bundle savedInstanceState) {
        // 给一些变量赋值
        mFragmentActivity = getActivity();
        if (needBuild()) {
            // 细分生命周期
            doInitOnViewCreated(mRootView, savedInstanceState);
        }
        mFirstTimeBuilt = false;
    }

    protected void doInitOnViewCreated(View rootView, Bundle savedInstanceState) {
        // 细分生命周期
        initView(mRootView, savedInstanceState);
        initListener(mRootView, savedInstanceState);
        initData(mRootView, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public FragmentActivity getFragmentActivity() {
        return isAddToActivity() ? getActivity() : mFragmentActivity;
    }

    public boolean isAddToActivity() {
        return isAdded();
    }

    @Override
    public Context getApplicationContext() {
        return XGlobals.getAppContext();
    }

    private boolean needBuild() {
        return mFirstTimeBuilt || rebuildOnMultiAttach();
    }

    /**
     * 当从Activity中attach/detach，是否需要重新加载View，执行initXXX系列方法。
     */
    protected boolean rebuildOnMultiAttach() {
        return false;
    }

    @Override
    public View provideLayoutView(LayoutInflater inflater, ViewGroup container,
                                  Bundle savedInstanceState) {
        // 为了实现findViewById
        int resId = provideLayoutResId();
        if (resId > 0) {
            return inflater.inflate(provideLayoutResId(), container, false);
        }
        return null;
    }

    @Override
    public View getRootView() {
        return mRootView;
    }

    /**
     * 仿照Activity的findVieById
     */
    public <T extends View> T findViewById(int id) {
        return (T) getRootView().findViewById(id);
    }

    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // start Activity methods
    /**
     * 推荐使用。
     *
     * 增加了检测是否已经加入Activity
     *
     */
    public void startActivityChecked(Class<? extends Activity> clz) {
        if (!isAddToActivity()) return;
        startActivity(clz);
    }

    /**
     * 推荐使用。
     *
     * 增加了检测是否已经加入Activity
     *
     */
    public void startActivityChecked(Intent intent) {
        if (!isAddToActivity()) return;
        startActivity(intent);
    }

    /**
     * 推荐使用。
     *
     * 增加了检测是否已经加入Activity
     *
     */
    public void startActivityForResultChecked(Class<? extends Activity> clz, int requestCode) {
        if (!isAddToActivity()) return;
        startActivityForResult(clz, requestCode);
    }

    /**
     * 推荐使用。
     *
     * 增加了检测是否已经加入Activity
     *
     */
    public void startActivityForResultChecked(Intent intent, int requestCode) {
        if (!isAddToActivity()) return;
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void startActivity(Intent intent) {
        XActivityController.startActivity(intent);
    }

    @Override
    public void startActivity(Class<? extends Activity> clz) {
        XActivityController.startActivity(clz);
    }

    @Override
    public void startActivityForResult(Class<? extends Activity> clz, int requestCode) {
        startActivityForResult(new Intent(getApplicationContext(), clz), requestCode);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

    /**
     * 关闭所属Activity
     */
    public void finishActivity() {
        if (!isAddToActivity()) return;
        getActivity().finish();
    }

    /**
     * {@inheritDoc}
     *
     * <p/>
     *
     * 默认返回当前Fragment
     *
     */
    @Override
    public Object getUITag() {
        return this;
    }
}
