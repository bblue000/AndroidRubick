package androidrubick.xframework.app.ui.component;

/**
 *
 * 界面加载相关的回调：
 *
 * <p/>
 *
 * 对Activity、Fragment而言，有几个时机是可以对外开放的，
 *
 * 如：
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/9/9.
 */
public interface XUICallback {

    void onCreated(XUIComponent flow);

    /**
     * 当界面加载成功时调用
     */
    void onViewCreated(XUIComponent flow);

    /**
     * 准备进行UI，监听器等的初始化操作
     */
    void onPrepareInit(XUIComponent flow) ;

    /**
     * 已经完成UI，监听器等的初始化操作
     */
    void onPostInit(XUIComponent flow) ;

    /**
     * 当界面销毁时调用
     */
    void onViewDestroyed(XUIComponent flow);

    void onDestroy(XUIComponent flow);
}
