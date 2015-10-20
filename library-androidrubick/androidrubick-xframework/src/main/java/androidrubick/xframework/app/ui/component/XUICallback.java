package androidrubick.xframework.app.ui.component;

/**
 *
 * 为外部提供全局的监听界面加载相关的回调。
 *
 * <p/>
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/9/9.
 *
 * @see SimpleUICallback
 */
public interface XUICallback {

    /**
     * 组件创建时调用
     */
    void onCreated(XUIComponent flow);

    /**
     * 当组件准备加载页面视图时调用
     */
    void onPrepareCreateView(XUIComponent flow);

    /**
     * 当组件界面加载完成时调用
     */
    void onPostCreatedView(XUIComponent flow);

    /**
     * 准备进行UI，监听器等的初始化操作
     */
    void onPrepareInit(XUIComponent flow) ;

    /**
     * 已经完成UI，监听器等的初始化操作
     */
    void onPostInit(XUIComponent flow) ;

    /**
     * 组件不再使用时销毁时调用，如Activity是调用{@link android.app.Activity#onDestroy()}后会回调，
     *
     * fragment在Activity不再使用它的时候（“no longer in use”）调用。
     */
    void onDestroy(XUIComponent flow);


    public static class SimpleUICallback implements XUICallback {
        @Override
        public void onCreated(XUIComponent flow) { }
        @Override
        public void onPrepareCreateView(XUIComponent flow) { }
        @Override
        public void onPostCreatedView(XUIComponent flow) { }
        @Override
        public void onPrepareInit(XUIComponent flow) { }
        @Override
        public void onPostInit(XUIComponent flow) { }
        @Override
        public void onDestroy(XUIComponent flow) { }
    }
}
