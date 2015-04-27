package org.androidrubick.demo;

import android.os.Bundle;
import android.view.View;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;

import org.androidrubick.app.BaseActivity;
import org.androidrubick.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import androidrubick.xframework.events.internal.XEventAnnotationProcessor;

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
        EventBus eventBus = new EventBus(new SubscriberExceptionHandler() {
            @Override
            public void handleException(Throwable exception, SubscriberExceptionContext context) {
                ToastUtils.showToast(exception.getMessage());
            }
        });
        eventBus.register(this);

        List list = new ArrayList<String>();
        list.add("");
        eventBus.post(list);
        XEventAnnotationProcessor.inject(this);
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
