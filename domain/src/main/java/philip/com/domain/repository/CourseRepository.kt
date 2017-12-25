package philip.com.domain.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import philip.com.domain.model.Course

/**
 * Interface defining methods for how the data layer can pass data to and from the Domain layer.
 * This is to be implemented by the data layer, setting the requirements for the
 * operations that need to be implemented
 */
interface CourseRepository {

    fun clearCourses(): Completable

    fun saveCourses(courses: List<Course>): Completable

    fun getCourses(): Flowable<List<Course>>

    fun getAllCourses(): Flowable<List<Course>>

    fun getCoursesForStudent(studentNumber: String): Flowable<List<Course>>

}