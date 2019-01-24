package org.cobraparser.async

/**
 * Used by methods that need to return results asynchronously. Results are
 * received in the event dispatch thread. This is a generic class that takes a
 * type parameter <code>TResult</code>, the type of the expected result object.
 *
 */
interface AsyncResult<TResult> {

    /**
     * Registers a listener of asynchronous results.
     *
     * @param listener
     */
    fun addResultListener(listener: AsyncResultListener<TResult>)

    /**
     * Removes a listener
     *
     * @param listener
     */
    fun removeResultListener(listener: AsyncResultListener<TResult>)

    /**
     * Forces listeners to be notified of a result if there is one
     */
    fun signal()

}