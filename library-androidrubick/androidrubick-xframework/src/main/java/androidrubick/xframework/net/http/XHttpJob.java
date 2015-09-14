package androidrubick.xframework.net.http;

import androidrubick.io.IOUtils;
import androidrubick.xframework.job.XJob;
import androidrubick.xframework.net.http.request.XHttpRequest;
import androidrubick.xframework.net.http.response.XHttpError;
import androidrubick.xframework.net.http.response.XHttpResponse;

/**
 *
 * 网络异步任务的基类
 *
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/7/21.
 *
 * @since 1.0
 */
public abstract class XHttpJob<Progress, Result> extends XJob<XHttpRequest, Progress, Result> {

//    @Override
//    protected final Result doInBackground(XHttpRequest... params) {
//        for (XHttpRequest request : params) {
//            XHttpResponse response = null;
//            try {
//                response = request.performRequest();
//                return parseResponse(request, response);
//            } catch (XHttpError e) {
//                return onExc(request, e);
//            } finally {
//                IOUtils.close(response);
//            }
//        }
//    }
//
//    /**
//     * 处理结果
//     *
//     * @param response 请求结果
//     */
//    protected abstract Result parseResponse(XHttpRequest request, XHttpResponse response);
//
//    /**
//     * 处理错误。
//     *
//     * <p/>
//     *
//     * 该方法仍是在子线程中调用
//     *
//     * @param exception {@link #doInBackground(Object[])}过程中产生的错误
//     */
//    protected abstract Result onExc(XHttpRequest request, XHttpError exception);
}
