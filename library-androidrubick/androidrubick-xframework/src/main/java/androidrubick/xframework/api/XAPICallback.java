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
     * 是{@link androidrubick.xframework.api.XAPIStatus}#ERR_*（特征是code<0）。
     *
     * @param status 错误信息对象
     */
    void onFailed(XAPIStatus status);

    /**
     * 请求取消时调用。如果已经执行得到结果（无论成功还是失败，
     * 成功时，两个参数同{@link #onSuccess}；
     * 失败是，<code>result</code>为null，<code>status</code>同{@link #onFailed}）
     *
     * @param result 虽然取消，但是如果已经执行完毕，则传回结果；没有执行完毕的情况，返回null
     * @param status 如果已经执行完毕，则返回请求信息对象；没有执行完毕的情况，返回null
     */
    void onCanceled(Result result, XAPIStatus status);

}
