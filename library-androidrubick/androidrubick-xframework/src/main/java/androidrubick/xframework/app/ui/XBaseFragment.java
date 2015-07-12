package androidrubick.xframework.app.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.androidrubick.app.BaseApplication;

import androidrubick.utils.FrameworkLog;
import butterknife.ButterKnife;

/**
 * Created by yong01.yin on 2014/11/11.
 */
public abstract class XBaseFragment extends Fragment implements IUIFlow {

    // root view是否已经创建，如果没有创建，而想使用应该创建后才能使用的方法时，将抛出异常
    private boolean mIsRootViewCreated = false;
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
        FrameworkLog.i("BaseFragment", "execute onCreateView!!! ");
        // 为了实现findViewById
        int resId = provideLayoutResId();
        View contentView;
        if (resId > 0) {
            contentView = inflater.inflate(provideLayoutResId(), container, false);
            mRootView = contentView;
        }
        contentView = provideLayoutView();
        if (null != contentView)  {
            mRootView = contentView;
        }
        // 保证RootView加载完成
        mIsRootViewCreated = (null != mRootView);
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
        // 给一些变量赋值
        mFragmentActivity = getActivity();

        ButterKnife.inject(this, mRootView);
        // 细分生命周期
        initView(mRootView, savedInstanceState);
        initListener(mRootView, savedInstanceState);
        initData(mRootView, savedInstanceState);
    }

    public FragmentActivity getFragmentActivity() {
        return isAddToActivity() ? getActivity() : mFragmentActivity;
    }

    public boolean isAddToActivity() {
        return isAdded();
    }

    public Context getApplicationContext() {
        return BaseApplication.getAppContext();
    }

    protected final void ensureRootViewCreated() {
        if (!mIsRootViewCreated) {
            throw new IllegalStateException("root view hasn't been created yet");
        }
    }

    @Override
    public View provideLayoutView() {
        return null;
    }

    @Override
    public View getRootView() {
        ensureRootViewCreated();
        return mRootView;
    }

    /**
     * 仿照Activity的findVieById
     */
    public <T extends View> T findViewById(int id) {
        ensureRootViewCreated();
        return (T) getRootView().findViewById(id);
    }

    /**
     * 推荐使用。
     *
     * 增加了检测是否已经加入
     *
     * @param intent
     */
    public void startActivityChecked(Intent intent) {
        if (isAddToActivity()) {
            return;
        }
        startActivity(intent);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    public void startActivity(Class<? extends Activity> clz) {
        final Activity activity = getFragmentActivity();
        startActivity(new Intent(activity, clz));
    }

    @Override
    public void startActivityForResult(Class<? extends Activity> clz, int requestCode) {
        final Activity activity = getFragmentActivity();
        startActivityForResult(new Intent(activity, clz), requestCode);
    }
}
