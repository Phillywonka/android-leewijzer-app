package philip.com.data.source.course

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import philip.com.data.models.CourseEntity
import philip.com.data.repository.course.CourseCache
import philip.com.data.repository.course.CourseDataStore

/**
 * Implementation of the [CourseDataStore] interface to provide a means of communicating
 * with the local data source
 */
open class CourseCacheDataStore(private val courseCache: CourseCache) : CourseDataStore {

    /**
     * Clear all Courses from the cache
     */
    override fun clearCourses(): Completable {
        return courseCache.clearCourses()
    }

    /**
     * Save a given [List] of [CourseEntity] instances to the cache
     */
    override fun saveCourses(courses: List<CourseEntity>): Completable {
        return courseCache.saveCourses(courses)
                .doOnComplete {
                    courseCache.setLastCacheTime(System.currentTimeMillis())
                }
    }

    /**
     * Retrieve a list of [CourseEntity] instance from the cache
     */
    override fun getCourses(): Flowable<List<CourseEntity>> {
        return courseCache.getCourses()
    }

    override fun getAllCourses(): Flowable<List<CourseEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Retrieve a list of [CourseEntity] instance from the cache
     */
    override fun isCached(): Single<Boolean> {
        return courseCache.isCached()
    }

}