package org.androidrubick.demo;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import org.androidrubick.app.BaseActivity;
import org.androidrubick.utils.ToastUtils;

/**
 * somthing
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/4/8 0008.
 */
public class MainActivity extends BaseActivity {


    @Override
    public int provideLayoutResId() {
        return R.layout.main;
    }

    @Override
    public void initView(View view, Bundle savedInstanceState) {
        ToastUtils.showLongToast(Uri.parse("http://sss.sss.sss/我是?q=").getPath());
    }

    @Override
    public void initListener(View view, Bundle savedInstanceState) {

    }

    @Override
    public void initData(View view, Bundle savedInstanceState) {

    }

    @Override
    public void updateDataToUI() {

    }
}
