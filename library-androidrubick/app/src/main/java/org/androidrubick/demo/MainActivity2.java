package org.androidrubick.demo;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/9/8.
 */
public class MainActivity2 extends Activity {
    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tv = (TextView) findViewById(R.id.tv);

        System.out.println("=======0" + Thread.currentThread());

        new Thread() {
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
				tv.setText("mark sb");
//				Canvas canvas = new Canvas();

                Looper.prepare();

                Handler handler = new Handler() {
                    public void handleMessage(android.os.Message msg) {
                        dialog = new Dialog(MainActivity2.this) {
                            public void show() {
                                super.show();
                                tv.setText("222222222222");
                                System.out.println("=======2" + (Looper.getMainLooper() == Looper.myLooper()));
                                System.out.println("=======2" + Thread.currentThread());
                            };
                        };


                        System.out.println("=======1" + (Looper.getMainLooper() == Looper.myLooper()));
                        System.out.println("=======1" + Thread.currentThread());
                        dialog.show();
                        print(dialog, Dialog.class, "mDecor");
                        print(get(dialog, Dialog.class, "mDecor"), View.class, "mParent");
                        try {
                            print(get(get(dialog, Dialog.class, "mDecor"), View.class, "mParent"),
                                    Class.forName("android.view.ViewRootImpl"), "mThread");
                        } catch (ClassNotFoundException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        postDelayed(new Runnable(){
                            public void run() {
                                dialog.hide();

                                postDelayed(new Runnable(){
                                    public void run() {
                                        runOnUiThread(new Runnable() {

                                            @Override
                                            public void run() {
                                                System.out.println("=======4" + Thread.currentThread());
                                                dialog.show();
                                                print(dialog, Dialog.class, "mDecor");
                                                print(get(dialog, Dialog.class, "mDecor"), View.class, "mParent");
                                                try {
                                                    print(get(get(dialog, Dialog.class, "mDecor"), View.class, "mParent"),
                                                            Class.forName("android.view.ViewRootImpl"), "mThread");
                                                } catch (ClassNotFoundException e) {
                                                    // TODO Auto-generated catch block
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                    };
                                }, 1000);
                            };
                        }, 1000);

                    };
                };
                handler.sendEmptyMessage(0);
                new Thread() {
                    public void run() {
                        System.out.println("=======3" + Thread.currentThread());
                    };
                }.start();
                Looper.loop();

            };
        }.start();
    }

    private void print(Object object, Class<?> clz, String var) {
        try {
            Field field = clz.getDeclaredField(var);
            field.setAccessible(true);
            System.err.println("=======" + field.get(object));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Object get(Object object, Class<?> clz, String var) {
        try {
            Field field = clz.getDeclaredField(var);
            field.setAccessible(true);
            return field.get(object);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
