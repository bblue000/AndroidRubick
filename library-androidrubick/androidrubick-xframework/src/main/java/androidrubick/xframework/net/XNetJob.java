package androidrubick.xframework.net;

import java.io.IOException;

import androidrubick.io.IOUtils;
import androidrubick.xframework.net.http.response.XHttpRes;
import androidrubick.xframework.job.XJob;

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
public abstract class XNetJob<Param, Progress, Result> extends XJob<Param, Progress, Result> {

//    @Override
//    protected final Result doInBackground(Param... params) {
//        XHttpRes response = null;
//        try {
//            XHttpRequest request = generateHttpRequest(params);
//            response = request.performRequest();
//            return doParse(response);
//        } catch (Throwable e) {
//            return doExc(e);
//        } finally {
//            IOUtils.close(response);
//        }
//    }
//
//    /**
//     * 生成HTTP请求对象
//     *
//     * @param params 参数
//     */
//    protected abstract XHttpRequest generateHttpRequest(Param...params);
//
//    /**
//     * 处理结果
//     * @param response 请求结果
//     */
//    protected abstract Result doParse(XHttpRes response) throws IOException;
//
//    /**
//     * 处理错误
//     *
//     * @param exception {@link #doInBackground(Object[])}过程中产生的错误
//     */
//    protected abstract Result doExc(Throwable exception);
}
