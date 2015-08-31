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
 *
 * <p/><p/>
 *
 * xframework的宗旨是：<b>简化开发</b>
 */
package androidrubick.xframework.job;