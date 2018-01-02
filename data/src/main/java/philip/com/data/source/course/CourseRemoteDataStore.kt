package philip.com.data.source.course

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import philip.com.data.models.CourseEntity
import philip.com.data.repository.course.CourseDataStore
import philip.com.data.repository.course.CourseRemote

/**
 * Implementation of the [CourseDataStore] interface to provide a means of communicating
 * with the remote data source
 */
open class CourseRemoteDataStore(private val courseRemote: CourseRemote)
    : CourseDataStore {

    override fun clearCourses(): Completable {
        throw UnsupportedOperationException()
    }

    override fun saveCourses(courses: List<CourseEntity>): Completable {
        throw UnsupportedOperationException()
    }

    /**
     * Retrieve a list of [CourseEntity] instances from the API
     */
    override fun getSelectedCourses(studentNumber: String): Flowable<List<CourseEntity>> {
        return courseRemote.getSelectedCourses(studentNumber)
    }

    override fun addCourse(studentNumber: String, courseName: String): Completable {
        return courseRemote.addCourse(studentNumber, courseName)
    }

    override fun getAllCourses(): Flowable<List<CourseEntity>> {
        return courseRemote.getAllCourses()
    }

    override fun isCached(): Single<Boolean> {
        throw UnsupportedOperationException()
    }
}
