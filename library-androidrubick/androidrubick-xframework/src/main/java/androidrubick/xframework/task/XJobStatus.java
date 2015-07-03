package androidrubick.xframework.task;

/**
 * Identifies the current status of a job if it is in the queue
 */
/*package*/ enum XJobStatus {
    /**
     * Job is in the queue but cannot run yet. cause not added into the queue
     */
    Pending,

    /**
     * Job is in the queue, ready to be run. Waiting for an available consumer.
     */
    InQueue,

    /**
     * Job is being executed by one of the runners.
     */
    Running,


    /**
     * 正常执行结束
     *
     * <p/>
     *
     */
    Finished,

    /**
     * 任务被取消了
     *
     * <p/>
     *
     */
    Canceled
}
