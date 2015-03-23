package org.androidrubick.app;

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
     * define the layout res of the activity
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
     * define the content view of the activity
     */
    View provideLayoutView();

    /**
     * 在onCreate中调用，在initData之前调用
     *
     * <p/>
     * called before {@link #updateDataToUI()} while
     * {@link android.app.Activity#onCreate(android.os.Bundle)} is running
     *
     * @param view root view of the activity
     *
     */
    void initView(View view, Bundle savedInstanceState);

    /**
     * 在onCreate中调用，initView, initData之后被调用
     * @added 1.0
     */
    void initListener();

    /**
     * 在onCreate中调用，在initView和initListener之后调用
     *
     * <br/>
     * called immediately after {@link #initView(android.view.View, android.os.Bundle)} while
     * {@link android.app.Activity#onCreate(android.os.Bundle)} is running
     *
     */
    void updateDataToUI() ;

    /**
     * this method returns the pure View.
     */
    View getRootView();

}
