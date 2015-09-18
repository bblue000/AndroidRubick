package androidrubick.xframework.net.http;

import androidrubick.io.IOProgressCallback;
import androidrubick.io.IOUtils;
import androidrubick.utils.MathPreconditions;
import androidrubick.xframework.net.http.request.XHttpRequest;
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
public abstract class XHttpProgressableJob<Progress, Result> extends XHttpJob<Progress, Result> {

    private int mBufSize;
    protected XHttpProgressableJob(int bufferSize) {
        mBufSize = MathPreconditions.checkPositive("bufferSize", bufferSize);
    }

    public int getBufferSize() {
        return mBufSize;
    }

//    @Override
//    protected Result parseResponse(XHttpRequest request, XHttpResponse response) {
//        final long contentLength = response.getContentLength();
//        byte[] d = XHttps.BYTE_ARRAY_POOL.getBuf(mBufSize);
//        IOUtils.writeTo(response.getContent(), true, null, true, new IOProgressCallback() {
//            @Override
//            public void onProgress(long readThisTime, long readTotal) {
//                publishProgress();
//            }
//
//            @Override
//            public void onComplete(long readTotal) {
//                publishProgress();
//            }
//        });
//        return null;
//    }

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
}
