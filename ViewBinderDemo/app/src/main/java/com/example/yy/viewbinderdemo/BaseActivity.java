package com.example.yy.viewbinderdemo;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by Yin Yong on 16/6/6.
 */
public class BaseActivity extends Activity {


    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        Log.d("yytest", "onCreateView");

        return super.onCreateView(name, context, attrs);
    }

    @NonNull
    @Override
    public LayoutInflater getLayoutInflater() {
        return new LayoutInflater(super.getLayoutInflater(), this) {
            @Override
            public LayoutInflater cloneInContext(Context newContext) {
                return null;
            }
        };
    }
}
