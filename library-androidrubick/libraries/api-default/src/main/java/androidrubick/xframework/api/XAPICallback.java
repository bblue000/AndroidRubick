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
     * {@link XAPIStatus#code} 为HTTP状态值，或者API状态值（视具体实现而定）。
     *
     * @param result 结果对象
     */
    void onSuccess(Result result);

    /**
     * 请求失败时，返回错误信息（错误代码和错误信息）
     *
     * <p/>
     *
     * {@link XAPIStatus#code} 为HTTP状态值，或者API错误码（视具体实现而定），或者
     *
     * 是{@link androidrubick.xframework.api.XAPIStatus}#ERR_*（特征是code<0）。
     *
     * @param status 错误信息对象
     */
    void onFailed(XAPIStatus status);

}
