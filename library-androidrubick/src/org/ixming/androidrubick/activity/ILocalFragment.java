package org.ixming.androidrubick.activity;

import android.content.Context;
import android.view.View;

/**
 * define base operations fragments should do as 
 * it recommends here
 * @version 1.0
 */
interface ILocalFragment extends IBaseUIFlow, IControlledActivity, IInjectableActivity {
	
	/**
	 * just like findViewById in Activity or View subclasses
	 * @param id target id to find
	 * @return View if it exists or null
	 */
	<T extends View>T findViewById(int id);
	
	/**
	 * return the application context
	 */
	Context getApplicationContext();
	
}
