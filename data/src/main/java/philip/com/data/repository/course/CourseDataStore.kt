package philip.com.data.repository.course

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import philip.com.data.models.CourseEntity

/**
 * Interface defining methods for the data operations related to Courses.
 * This is to be implemented by external data source layers, setting the requirements for the
 * operations that need to be implemented
 */
interface CourseDataStore {

    fun clearCourses(): Completable

    fun saveCourses(courses: List<CourseEntity>): Completable

    fun getSelectedCourses(studentNumber: String): Flowable<List<CourseEntity>>

    fun getAllCourses(): Flowable<List<CourseEntity>>

    fun isCached(): Single<Boolean>

    fun addCourse(studentNumber: String, courseName: String): Completable
}