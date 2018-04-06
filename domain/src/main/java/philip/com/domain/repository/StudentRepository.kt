package philip.com.domain.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import philip.com.domain.model.Course
import philip.com.domain.model.Student

/**
 * Interface defining methods for how the data layer can pass data to and from the Domain layer.
 * This is to be implemented by the data layer, setting the requirements for the
 * operations that need to be implemented
 */
interface StudentRepository {

    fun askForLogin(studentNumber: String, password: String): Completable

    fun register(studentNumber: String, firstName: String, lastName: String, password: String): Completable

    fun askForLogout(): Completable

    fun getStudentSignedIn(): Flowable<Student>

    fun getCoursesForStudent(studentNumber: String): Flowable<List<Course>>

}