package androidrubick.xframework.app.control;


import androidrubick.utils.Objects;

/**
 *
 * UI控制器的基类。
 *
 * <p/>
 *
 * UI控制器，从Manager中获取需要的数据，
 *
 * <p/>
 *
 * Controller和Manager之间不一定是一对一的关系，相对而言，Model和Manager二者
 *
 * 在某种意义上来说，已经构成了完整的业务逻辑，UI和Controller完成的UI显示逻辑。
 *
 * 因此，该处抽象不对Controller和Manager之间建立关系。
 *
 * <p/>
 * <p/>
 *
 * Created by Yin Yong on 15/7/2.
 *
 * @since 1.0
 */
public abstract class XUIController<Callback extends XUIController.XUICtrlCallback> {

    protected Callback mCallback;
    protected XUIController() {

    }

    public void bindCallback(Callback callback) {
        mCallback = callback;
    }

    public void unbindCallback() {
        mCallback = null;
    }

    public boolean isBind() {
        return !Objects.isNull(mCallback);
    }

    /**
     * 请求初始化数据，可能是请求，也可能是直接获取，
     *
     * 当数据加载完成后，将告知界面（{@link XUICtrlCallback#onDataInited(Object)}）
     */
    public abstract void initData() ;

    /**
     * 加载更新数据，可能是请求，也可能是直接获取，
     *
     * 当数据加载完成后，将告知界面（{@link XUICtrlCallback#onDataRefreshed(Object)}）
     */
    public abstract void loadData() ;

    /**
     * <code>View</code>层的回调，确保都是在UI线程下执行回调中的方法
     */
    public interface XUICtrlCallback<Data> {

        abstract void onDataInited(Data data) ;

        abstract void onDataRefreshed(Data data) ;

    }

}
