package org.ixming.androidrubick.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

/**
 * 
 * UI初始化流程，及相关API
 * 
 * @author vipshop
 *
 */
interface IBaseUIFlow extends View.OnClickListener {
	
	/**
	 * 提供activity的layout
	 * 
	 * <br/>
	 * define the layout res of the activity
	 */
	int provideLayoutResId();
	
	/**
	 * 在onCreate中调用，在initData之前调用
	 * 
	 * <br/>
	 * called before {@link #initData(View, android.os.Bundle)} while
	 * {@link Activity#onCreated(android.os.Bundle)} is running
	 * 
	 * @param view root view of the activity
	 * 
	 * @see #findViewById(int)
	 */
	void initView(View view);
	
	/**
	 * 在onCreate中调用，在initView之后调用
	 * 
	 * <br/>
	 * called immediately after {@link #initView(View)} while
	 * {@link Activity#onCreated(android.os.Bundle)} is running
	 * 
	 * @param view root view of the activity
	 * @param savedInstanceState If the activity is being re-created from
     * a previous saved state, this is the state.
	 */
	void initData(View view, Bundle savedInstanceState) ;
	
	/**
	 * 在onCreate中调用，initView, initData之后被调用
	 * @added 1.0
	 */
	void initListener();
	
	/**
	 * this method returns the pure View.
	 */
	View getRootView();
	
	/**
	 * 通过ID找到指定的View，并为之添加监听器；<br/>
	 * 
	 * 该方法着重强调此View只需添加点击事件，而不会对之进行状态或者
	 * 显示的改变。
	 */
	IBaseUIFlow bindClickListener(int resId);
	
	/**
	 * 给指定的View添加监听器
	 */
	IBaseUIFlow bindClickListener(View view);
	
	/**
	 * 移除resId指定的View的单击事件监听器
	 */
	IBaseUIFlow removeClickListener(int resId);
	
	/**
	 * 移除View的单击事件监听器
	 */
	IBaseUIFlow removeClickListener(View view);
	
	/**
	 * 创建一个本Activity的Handler对象，此方法在onCreate()中调用，且
	 * 在initView及initData之前。
	 * @added 1.0
	 */
	Handler provideActivityHandler();
	
}
