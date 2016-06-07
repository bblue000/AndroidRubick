package com.example.yy.viewbinderdemo;

import android.os.Bundle;
import android.util.Log;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("yytest", "a1");
    }

}
