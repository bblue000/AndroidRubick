package androidrubick.utils.retrypolocy;

/**
 * Retry policy for a request.
 *
 * @since 1.0
 */
public interface RetryPolicy {

    /**
     * Returns true if this policy has attempts remaining, false otherwise.
     *
     * @since 1.0
     */
    public boolean hasAttemptRemaining() ;

    /**
     * Prepares for the next retry by applying a backoff to the timeout.
     * @param error The error code of the last attempt.
     * @throws Throwable In the event that the retry could not be performed (for example if we
     * ran out of attempts), the passed in error is thrown.
     *
     * @since 1.0
     */
    public <Ex extends Throwable>void retry(Ex error) throws Ex;
}