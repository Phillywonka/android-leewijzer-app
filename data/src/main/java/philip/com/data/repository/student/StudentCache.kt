package philip.com.data.repository.student

import io.reactivex.Single


/**
 * Interface defining methods for the caching of Courses. This is to be implemented by the
 * cache layer, using this interface as a way of communicating.
 */
interface StudentCache {

    /**
     * Check whether there is a Student stored in the cache.
     *
     * @return true if the list is cached, otherwise false
     */
    fun isCached(): Single<Boolean>

    /**
     * Set a point in time at when the cache was last updated.
     *
     * @param lastCache the point in time at when the cache was last updated
     */
    fun setLastCacheTime(lastCache: Long)

    /**
     * Check if the cache is expired.
     *
     * @return true if the cache is expired, otherwise false
     */
    fun isExpired(): Boolean

}