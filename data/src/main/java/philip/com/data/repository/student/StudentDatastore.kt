package philip.com.data.repository.student

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import philip.com.domain.model.Student

/**
 * Interface defining methods for the data operations related to Students.
 * This is to be implemented by external data source layers, setting the requirements for the
 * operations that need to be implemented
 */
interface StudentDataStore {

    fun getSignedInStudent(): Flowable<Student>

    fun register(studentNumber: String, firstName: String, lastName: String, password: String): Completable

    fun askForLogin(studentNumber: String, password: String): Completable

    fun askForLogout(): Completable

    fun getStudentsForStudent(studentNumber: String): Flowable<List<Student>>

    fun isCached(): Single<Boolean>
}