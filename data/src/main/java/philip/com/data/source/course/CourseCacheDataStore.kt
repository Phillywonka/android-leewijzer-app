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
        println("Application: clear courses")
        return courseCache.clearCourses()
    }

    /**
     * Save a given [List] of [CourseEntity] instances to the cache
     */
    override fun saveCourses(courses: List<CourseEntity>): Completable {
        println("Application: save courses to cache")
        return courseCache.saveCourses(courses)
                .doOnComplete {
                    courseCache.setLastCacheTime(System.currentTimeMillis())
                }
    }

    /**
     * Retrieve a list of [CourseEntity] instance from the cache
     */
    override fun getSelectedCourses(studentNumber: String): Flowable<List<CourseEntity>> {
        println("Application: get selected courses from cache")
        return courseCache.getCourses()
    }

    /**
     * Retrieve a list of all available [CourseEntity] instance from the cache
     */
    override fun getAllCourses(): Flowable<List<CourseEntity>> {
        throw UnsupportedOperationException()
    }

    /**
     * Retrieve a list of [CourseEntity] instance from the cache
     */
    override fun isCached(): Single<Boolean> {
        return courseCache.isCached()
    }

    override fun addCourse(studentNumber: String, courseName: String): Completable {
        throw UnsupportedOperationException()
    }
}