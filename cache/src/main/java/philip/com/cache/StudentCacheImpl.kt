package philip.com.cache

import android.util.Log
import io.reactivex.Single
import philip.com.cache.database.CacheDatabase
import philip.com.cache.mapper.StudentEntityMapper
import philip.com.data.repository.student.StudentCache

/**
 * @author Philip Wong
 * @since 09-03-18
 **/
class StudentCacheImpl(val coursesDatabase: CacheDatabase,
                       private val entityMapper: StudentEntityMapper,
                       private val preferencesHelper: PreferencesHelper) :
        StudentCache {

    private val EXPIRATION_TIME = (60 * 10 * 1000).toLong()

    /**
     * Retrieve an instance from the database, used for tests.
     */
    internal fun getDatabase(): CacheDatabase {
        return coursesDatabase
    }

    /**
     * Check whether there are instances of [CachedCourse] stored in the cache.
     */
    override fun isCached(): Single<Boolean> {
        return Single.defer {
            Log.d("Application", "CourseCacheImpl: isCached: " + coursesDatabase.cachedCourseDao().loadSelectedCourses().isNotEmpty())
            Single.just(coursesDatabase.cachedCourseDao().loadSelectedCourses().isNotEmpty())
        }
    }

    /**
     * Set a point in time at when the cache was last updated.
     */
    override fun setLastCacheTime(lastCache: Long) {
        preferencesHelper.lastCacheTime = lastCache
    }

    /**
     * Check whether the current cached data exceeds the defined [EXPIRATION_TIME] time.
     */
    override fun isExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = this.getLastCacheUpdateTimeMillis()
        return currentTime - lastUpdateTime > EXPIRATION_TIME
    }

    /**
     * Get in millis, the last time the cache was accessed.
     */
    private fun getLastCacheUpdateTimeMillis(): Long {
        return preferencesHelper.lastCacheTime
    }

}