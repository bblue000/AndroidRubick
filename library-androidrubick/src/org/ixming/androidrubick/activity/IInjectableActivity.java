package org.ixming.androidrubick.activity;

import org.ixming.androidrubick.inject4android.InjectConfigure;

import android.view.View;


interface IInjectableActivity {

	/**
	 * used by {@link #injectSelf()};
	 */
	InjectConfigure provideInjectConfigure() ;
		
	/**
	 * 初始时，是否主动使用动态注入
	 */
	boolean useInjectBeforeInitView() ;
	
	/**
	 * 调用该方法，动态注入
	 */
	void injectSelf() ;

	/**
	 * 根据提供的配置，调用该方法，动态注入
	 */
	void injectSelf(InjectConfigure configure) ;
	
	/**
	 * @param target 需要注入的对象
	 * @param rootView 所在的View
	 */
	void injectTarget(Object target, View rootView) ;
	
	/**
	 * @param target 需要注入的对象
	 * @param rootView 所在的View
	 * @param configure 注入的配置
	 */
	void injectTarget(Object target, View rootView, InjectConfigure configure) ;
	
}
