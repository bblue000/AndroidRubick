package org.androidrubick.demo;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;

import java.util.ArrayList;
import java.util.List;

import androidrubick.xbase.util.AndroidUtils;
import androidrubick.xbase.util.AppInfos;
import androidrubick.xbase.util.ToastUtils;
import androidrubick.xframework.app.ui.component.XBaseActivity;
import androidrubick.xframework.events.XEventAPI;
import androidrubick.xframework.events.annotation.XEvent;
import androidrubicktest.api.XAPITest;
import butterknife.OnClick;

/**
 * somthing
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/4/8 0008.
 */
public class MainActivity extends XBaseActivity {

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
        XEventAPI.register(this);
        new Thread("yytest") {
            @Override
            public void run() {

                XEventAPI.post("1", "1");
                XEventAPI.postToMain("1", "2");

            }
        }.start();

        AppInfos.printMemeory();
//        XApplication.is();

    }

    @Subscribe
    void d(List list) {
        Context context = (Context) list.get(0);
    }

    @XEvent({"1", "2", "3"})
    void dd(String msg) {
        Log.d("Event", "" + AndroidUtils.isMainThread());
        ToastUtils.showToast(msg);
    }

    @Override
    public void initListener(View view, Bundle savedInstanceState) {
    }

    @Override
    public void initData(View view, Bundle savedInstanceState) {

        XAPITest.test();
        XAPITest.testHolder();

    }

    @Override
    public void updateDataToUI() {
        
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        XEventAPI.unregister(this);
    }

    @OnClick(R.id.btn)
    void open() {
        getSupportFragmentManager().beginTransaction()
        .add(R.id.root, new Fa(), "")
        .commit();
    }
}
