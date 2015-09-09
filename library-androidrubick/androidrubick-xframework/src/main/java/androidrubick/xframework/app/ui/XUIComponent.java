package androidrubick.xframework.app.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidrubick.xframework.app.control.XUIController;

/**
 * UI初始化流程，及相关API
 *
 * <p/>
 *
 * Created by yong01.yin on 2014/11/11.
 */
public interface XUIComponent {

    /**
     * 提供activity的layout res ID
     *
     * <p/>
     *
     * 如果想设置特定的View，可以调用{@link #provideLayoutView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)}
     *
     * <br/>
     * define the layout res of the activity/fragment
     */
    int provideLayoutResId();

    /**
     * 提供activity/fragment的content View，默认实现是根据{@link #provideLayoutResId()}
     * 加载布局，没有提供则返回null
     *
     * <p/>
     * define the content view of the activity/fragment
     */
    View provideLayoutView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState);

    /**
     * 在{@link Activity#onCreate(Bundle)}中调用，
     * 在{@link #initListener(View, Bundle)}之前调用
     *
     * <p/>
     *
     * @param rootView root content view of the activity/fragment
     *
     */
    void initView(View rootView, Bundle savedInstanceState);

    /**
     * 在{@link Activity#onCreate(Bundle)}中调用，
     * 在{@link #initView(View, Bundle)}之后被调用
     *
     * @param rootView root content view of the activity/fragment
     */
    void initListener(View rootView, Bundle savedInstanceState);

    /**
     * 在{@link Activity#onCreate(Bundle)}中调用，
     * 在{@link #initView(View, Bundle)}和{@link #initListener(View, Bundle)}之后调用。
     *
     * <p/>
     *
     * 该方法中执行Controller的初始化（{@link XUIController#initData()}）和
     * 绑定（{@link XUIController#bindCallback(XUIController.XUICtrlCallback)}）操作。
     *
     * <p/>
     *
     * @param rootView root content view of the activity/fragment
     *
     */
    <Data, Callback extends XUIController.XUICtrlCallback<Data>>XUIController<Callback>
    provideController(View rootView, Bundle savedInstanceState) ;

    /**
     * 获取由{@link #provideController(android.view.View, android.os.Bundle)}创建的{@link XUIController}对象
     */
    <Data, Callback extends XUIController.XUICtrlCallback<Data>> XUIController<Callback> getController() ;

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

    /**
     * as a specific UI component, we can mark this by a tag.
     *
     * <p/>
     *
     * 默认返回当前组件
     */
    Object getUITag();
}
