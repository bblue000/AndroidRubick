package org.androidrubick.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * 封装了一些步骤化的操作，比如初始化View，初始化监听器，初始化数据
 * <p/>
 * Created by yong01.yin on 2014/11/11.
 */
public abstract class BaseActivity extends FragmentActivity implements IUIFlow {

    private View mRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int resId = provideLayoutResId();
        View contentView;
        if (resId > 0) {
            contentView = LayoutInflater.from(this).inflate(resId, (ViewGroup) getWindow().getDecorView(), false);
            mRootView = contentView;
        }
        contentView = provideLayoutView();
        if (null != contentView)  {
            mRootView = contentView;
        }

        if (null != mRootView) {
            setContentView(mRootView);
        }

        ButterKnife.inject(this, mRootView);
        initView(mRootView, savedInstanceState);
        initListener();
        updateDataToUI();
    }

    @Override
    public View provideLayoutView() {
        return null;
    }

    @Override
    public final View getRootView() {
        return mRootView;
    }

    @Override
    public void startActivity(Class<? extends Activity> clz) {
        startActivity(new Intent(this, clz));
    }

    @Override
    public void startActivityForResult(Class<? extends Activity> clz, int requestCode) {
        startActivityForResult();
    }
}


