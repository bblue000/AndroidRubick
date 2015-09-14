package androidrubick.xframework.net;

import androidrubick.xframework.net.http.XHttpJob;

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
public abstract class XHttpProgressableJob<Progress, Result> extends XHttpJob<Progress, Result> {

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
//    protected final Result doParse(XHttpRes response) throws IOException {
//        final long contentLength = response.getContentLength();
////        XNetJobUtils.getByteArrayPool().getBuf()
////        IOUtils.writeTo(response.getContent(), null, true, new IOProgressCallback() {
////            @Override
////            public void onProgress(long readThisTime, long readTotal) {
////                publishProgress();
////            }
////
////            @Override
////            public void onComplete(long readTotal) {
////                publishProgress();
////            }
////        });
//        return null;
//    }
//
//    /**
//     * 处理错误
//     *
//     * @param exception {@link #doInBackground(Object[])}过程中产生的错误
//     */
//    protected abstract Result doExc(Throwable exception);
}
