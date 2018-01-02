package philip.com.data.repository.course

import io.reactivex.Completable
import io.reactivex.Flowable
import philip.com.data.models.CourseEntity

/**
 * Interface defining methods for the caching of Courses. This is to be implemented by the
 * cache layer, using this interface as a way of communicating.
 */
interface CourseRemote {

    /**
     * Retrieve a list of Courses, from the remote
     */
    fun getSelectedCourses(studentNumber: String): Flowable<List<CourseEntity>>

    /**
     * Retrieve a list all available of Courses, from the remote
     */
    fun getAllCourses(): Flowable<List<CourseEntity>>

    /**
     * Add a new course to a student
     */
    fun addCourse(studentNumber: String, courseName: String): Completable

}