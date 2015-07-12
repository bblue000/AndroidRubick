package org.androidrubick.demo;

import android.os.Bundle;
import android.view.View;

import androidrubick.xframework.app.ui.XBaseFragment;

/**
 * somthing
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/7/11 0011.
 *
 * @since 1.0
 */
public class Fa extends XBaseFragment {

    @Override
    public int provideLayoutResId() {
        return R.layout.main;
    }

    @Override
    public void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    public void initListener(View view, Bundle savedInstanceState) {

    }

    @Override
    public void initData(View view, Bundle savedInstanceState) {
        startActivity(TestActivity.class);
    }

    @Override
    public void updateDataToUI() {

    }

    @Override
    public void doBackPressFinish() {

    }

    @Override
    public boolean validateBackPressFinish() {
        return false;
    }
}
