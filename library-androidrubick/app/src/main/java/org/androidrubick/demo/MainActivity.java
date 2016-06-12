package org.androidrubick.demo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidrubick.app.events.XEventAPI;
import androidrubick.app.events.annotation.XEvent;
import androidrubick.xbase.util.AndroidUtils;
import androidrubick.xbase.util.AppInfos;
import androidrubick.xframework.api.APICallback;
import androidrubick.xframework.api.APIStatus;
import androidrubick.xframework.api.XAPI;
import androidrubick.xframework.app.XGlobalUIs;
import androidrubick.xframework.app.ui.component.XBaseActivity;
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

        XAPI.get("http://n.myopen.vip.com/address/address", null, String.class, new APICallback<String>() {
            @Override
            public void onSuccess(String s) {
                XEventAPI.post("1", s);
            }

            @Override
            public void onFailed(APIStatus status) {
                XEventAPI.post("1", status.message);
            }
        });

    }

    @XEvent({"1", "2", "3"})
    void dd(String msg) {
        Log.d("Event", "" + AndroidUtils.isMainThread());
        XGlobalUIs.showTip(this, msg, msg);
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

    @Override
    public Object getUITag() {
        return "main";
    }
}
