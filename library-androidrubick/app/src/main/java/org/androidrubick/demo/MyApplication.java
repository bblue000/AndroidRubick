package org.androidrubick.demo;

import androidrubick.xframework.app.XApplication;
import androidrubick.xframework.impl.statistics.A;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/9.
 */
public class MyApplication extends XApplication {

    private A a;
    @Override
    protected void onCreateMainApp() {
        super.onCreateMainApp();
        a = new A();
    }
}
