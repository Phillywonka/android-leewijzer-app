package philip.com.data.repository.student

import io.reactivex.Completable

/**
 * Interface defining methods for retrieving Students from the cloud.
 */
interface StudentRemote {

    /**
     * Login a student
     */
    fun login(studentNumber: String, password: String): Completable

    /**
     * Register a new student
     */
    fun register(studentNumber: String, firstName: String, lastName: String, password: String): Completable
}