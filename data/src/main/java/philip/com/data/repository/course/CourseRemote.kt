package philip.com.data.repository.course

import io.reactivex.Completable
import io.reactivex.Flowable
import philip.com.data.models.CourseEntity

/**
 * Interface defining methods for retrieving courses from the cloud.
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