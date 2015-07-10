package androidrubick.xframework.app.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * UI初始化流程，及相关API
 *
 * <p/>
 * Created by yong01.yin on 2014/11/11.
 */
interface IUIFlow {

    /**
     * 提供activity的layout res ID
     *
     * <p/>
     *
     * 如果想设置特定的View，可以调用{@link #provideLayoutView()}
     *
     * <br/>
     * define the layout res of the activity/fragment
     */
    int provideLayoutResId();

    /**
     * 提供activity的content View
     *
     * <p/>
     *
     * 先通过{@link #provideLayoutResId()}获取，再通过{@link #provideLayoutView()}。
     *
     * <p/>
     * define the content view of the activity/fragment
     */
    View provideLayoutView();

    /**
     * 在{@link Activity#onCreate(Bundle)}中调用，
     * 在{@link #initListener(View, Bundle)}之前调用
     *
     * <p/>
     *
     * @param view root view of the activity/fragment
     *
     */
    void initView(View view, Bundle savedInstanceState);

    /**
     * 在{@link Activity#onCreate(Bundle)}中调用，
     * 在{@link #initView(View, Bundle)}之后被调用
     *
     * @param view root view of the activity/fragment
     */
    void initListener(View view, Bundle savedInstanceState);

    /**
     * 在{@link Activity#onCreate(Bundle)}中调用，
     * 在{@link #initView(View, Bundle)}和{@link #initListener(View, Bundle)}之后调用
     *
     * <p/>
     *
     * @param view root view of the activity/fragment
     *
     */
    void initData(View view, Bundle savedInstanceState) ;

    /**
     * 一个内置的用于更新UI的方法
     *
     * <br/>
     *
     */
    void updateDataToUI() ;

    /**
     * this method returns the pure View.（并非是{@link android.view.Window#getDecorView()}）
     */
    View getRootView();

    /**
     *
     * 启动指定的Activity
     *
     * @param clz an Activity Class
     */
    void startActivity(Class<? extends Activity> clz);

    /**
     *
     * 启动指定的Activity（如果需要设定参数，使用{@link Activity#startActivityForResult(Intent, int)}）
     *
     * @param clz an Activity Class
     */
    void startActivityForResult(Class<? extends Activity> clz, int requestCode);

    /**
     * 检查是否符合回退条件（根据{@link #validateBackPressFinish()}判断），
     * 如果符合则调用默认的回退操作。
     *
     * @see #validateBackPressFinish()
     */
    void doBackPressFinish();

    /**
     * 当用户想要关闭当前界面时，判断是否满足关闭界面的条件，若满足则返回true，否则返回false。
     *
     * 该方法在{@link #doBackPressFinish()}中被调用，用于判断是否需要直接执行默认的回退操作。
     *
     * @see #doBackPressFinish()
     */
    boolean validateBackPressFinish();
}
