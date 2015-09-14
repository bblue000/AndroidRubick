package androidrubick.xframework.api;

/**
 *
 * API 回调
 *
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/6/1.
 *
 * @since 1.0
 */
public interface XAPICallback<Result> {

    /**
     * 请求成功时，返回指定对象
     *
     * <p/>
     *
     * {@link XAPIStatus#getCode()} 为HTTP状态值，或者API状态值（视具体实现而定）。
     *
     * @param result 结果对象
     */
    void onSuccess(Result result, XAPIStatus status);

    /**
     * 请求失败时，返回错误信息（错误代码和错误信息）
     *
     * <p/>
     *
     * {@link XAPIStatus#getCode()} 为HTTP状态值，或者API错误码（视具体实现而定），或者
     *
     * 是XAPI.ERR_*（特征是code<0）。
     *
     * @param status 错误信息对象
     */
    void onFailed(XAPIStatus status);

    /**
     * 请求取消时调用
     *
     * @param result 虽然取消，但是如果已经执行完毕，则传回结果
     */
    void onCanceled(Result result);

}
