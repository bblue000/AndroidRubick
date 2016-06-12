package androidrubick.app;

import android.os.Bundle;

import androidrubick.app.events.XEventAPI;
import androidrubick.xframework.app.ui.component.XBaseActivity;
import butterknife.ButterKnife;

/**
 *
 * APP中所有{@link android.app.Activity}的基类，封装了一些基本的操作，以简化客户端开发
 *
 * <p/>
 *
 * Created by Yin Yong on 16/6/12.
 */
public abstract class BaseActivity extends XBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        XEventAPI.register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void doOnCreateView(Bundle savedInstanceState) {
        super.doOnCreateView(savedInstanceState);
        ButterKnife.inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        XEventAPI.unregister(this);
        ButterKnife.reset(this);
    }

    /**
     * 返回一个字符串，定义该Activity
     */
    @Override
    public Object getUITag() {
        return super.getUITag();
    }
}
