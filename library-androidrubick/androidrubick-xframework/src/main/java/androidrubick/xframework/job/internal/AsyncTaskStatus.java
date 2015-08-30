package androidrubick.xframework.job.internal;

/**
 * Indicates the current status of the task. Each status will be set only once
 * during the lifetime of a task.
 *
 * <p/>
 * <p/>
 * Created by Yin Yong on 15/6/4.
 *
 * @since 1.0
 */
public enum AsyncTaskStatus {

    /**
     * Indicates that the task has not been executed yet.
     */
    PENDING,
    /**
     * Indicates that the task is running.
     */
    RUNNING,
    /**
     * Indicates that {@link AsyncTask#onPostExecute} has finished.
     */
    FINISHED,

}
