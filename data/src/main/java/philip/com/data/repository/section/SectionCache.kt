package philip.com.data.repository.section

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import philip.com.data.models.SectionEntity


/**
 * Interface defining methods for the caching of Sections. This is to be implemented by the
 * cache layer, using this interface as a way of communicating.
 */
interface SectionCache {

    /**
     * Clear all Sections from the cache.
     */
    fun clearSections(): Completable

    /**
     * Save a given list of Sections to the cache.
     */
    fun saveSections(courses: List<SectionEntity>): Completable

    /**
     * Retrieve a list of Sections, from the cache.
     */
    fun getSections(): Flowable<List<SectionEntity>>

    /**
     * Check whether there is a list of Sections stored in the cache.
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