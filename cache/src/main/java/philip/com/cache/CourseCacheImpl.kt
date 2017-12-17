package philip.com.cache

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import philip.com.cache.database.CacheDatabase
import philip.com.cache.mapper.CourseEntityMapper
import philip.com.data.models.CourseEntity
import philip.com.data.repository.CourseCache

/**
 * Cached implementation for retrieving and saving Course instances. This class implements the
 * [CourseCache] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class CourseCacheImpl (val coursesDatabase: CacheDatabase,
                       private val entityMapper: CourseEntityMapper,
                       private val preferencesHelper: PreferencesHelper) :
        CourseCache {

    private val EXPIRATION_TIME = (60 * 10 * 1000).toLong()

    /**
     * Retrieve an instance from the database, used for tests.
     */
    internal fun getDatabase(): CacheDatabase {
        return coursesDatabase
    }

    /**
     * Remove all the data from all the tables in the database.
     */
    override fun clearCourses(): Completable {
        return Completable.defer {
            coursesDatabase.cachedCourseDao().clearCourses()
            Completable.complete()
        }
    }

    /**
     * Save the given list of [CourseEntity] instances to the database.
     */
    override fun saveCourses(courses: List<CourseEntity>): Completable {
        return Completable.defer {
            courses.forEach {
                coursesDatabase.cachedCourseDao().insertCourse(
                        entityMapper.mapToCached(it))
            }
            Completable.complete()
        }
    }

    /**
     * Retrieve a list of [CourseEntity] instances from the database.
     */
    override fun getCourses(): Flowable<List<CourseEntity>> {
        return Flowable.defer {
            Flowable.just(coursesDatabase.cachedCourseDao().loadAllCourses())
        }.map {
            it.map { entityMapper.mapFromCached(it) }
        }
    }

    /**
     * Check whether there are instances of [CachedCourse] stored in the cache.
     */
    override fun isCached(): Single<Boolean> {
        return Single.defer {
            Single.just(coursesDatabase.cachedCourseDao().loadAllCourses().isNotEmpty())
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