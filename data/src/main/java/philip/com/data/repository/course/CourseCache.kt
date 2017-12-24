package philip.com.data.repository.course

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import philip.com.data.models.CourseEntity


/**
 * Interface defining methods for the caching of Courses. This is to be implemented by the
 * cache layer, using this interface as a way of communicating.
 */
interface CourseCache {

    /**
     * Clear all Courses from the cache.
     */
    fun clearCourses(): Completable

    /**
     * Save a given list of Courses to the cache.
     */
    fun saveCourses(courses: List<CourseEntity>): Completable

    /**
     * Retrieve a list of Courses, from the cache.
     */
    fun getCourses(): Flowable<List<CourseEntity>>

    /**
     * Check whether there is a list of Courses stored in the cache.
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