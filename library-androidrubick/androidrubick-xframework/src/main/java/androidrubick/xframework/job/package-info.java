/**
 * 该包定义任务相关的顶层接口，抽象出android应用程序开发中的任务管理、分发、执行等。
 *
 * <p/>
 *
 * {@link androidrubick.xframework.job.XJob} 模仿{@link android.os.AsyncTask}实现，
 * 并做了部分扩展。
 *
 * <p/>
 *
 * 实例代码：
 * <pre>
 *     class Job1 extends XJob<ParamEntity, Void, ResultEntity> {
 *         protected ResultEntity doInBackground(ParamEntity...params) {
 *             // ...
 *         }
 *
 *         protected void onPostExecute(ResultEntity result) {
 *             // ...
 *         }
 *     }
 *
 *     ParamEntity param = ...;
 *     Job1 job = new Job1();
 *     job.execute(param);
 * </pre>
 *
 * <p/>
 *
 * 一个可以：
 * <pre>
 *     public class SimpleJobRetryWrapper<Params, Progress, Result> extends XJob<Params, Progress, Result> {
 *         private XJob<Params, Progress, Result> mAnother;
 *         private RetryPolicy mRetryPolicy;
 *
 *         public SimpleJobRetryWrapper(XJob<Params, Progress, Result> another, RetryPolicy retryPolicy) {
 *             mAnother = Preconditions.checkNotNull(another, "raw job is null");
 *             mRetryPolicy = Preconditions.checkNotNull(retryPolicy, "retry policy is null");
 *         }
 *
 *         public final XJob<Params, Progress, Result> getRawJob() {
 *             return mAnother;
 *         }
 *
 *         public final RetryPolicy getRetryPolicy() {
 *             return mRetryPolicy;
 *         }
 *
 *         \@Override
 *         protected Result doInBackground(Params... params) {
 *             Result result;
 *             while (true) {
 *                 try {
 *                     result = mAnother.doInBackground(params);
 *                 } catch (Throwable e) {
 *                     try {
 *                         mRetryPolicy.retry(e);
 *                         continue;
 *                     } catch (Throwable cannotRetryEx) {
 *                         throw Exceptions.toRuntime(cannotRetryEx);
 *                     }
 *                 }
 *                 break;
 *             }
 *             return result;
 *         }
 *     }
 * </pre>
 *
 *
 * <p/><p/>
 *
 * xframework的宗旨是：<b>简化开发</b>
 */
package androidrubick.xframework.job;