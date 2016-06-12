package org.androidrubick.demo;

import androidrubick.app.BaseApplication;
import androidrubick.xframework.impl.statistics.A;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/9.
 */
public class MyApplication extends BaseApplication {

    private A a;
    @Override
    protected void onCreateMainApp() {
        super.onCreateMainApp();
        a = new A();
    }
}
