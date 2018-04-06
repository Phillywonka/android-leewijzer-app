package philip.com.data

import io.reactivex.Completable
import io.reactivex.Flowable
import philip.com.data.mapper.StudentMapper
import philip.com.data.source.student.StudentDataStoreFactory
import philip.com.domain.model.Course
import philip.com.domain.model.Student
import philip.com.domain.repository.StudentRepository
import javax.naming.OperationNotSupportedException

/**
 * Provides an implementation of the [StudentRepository] interface for communicating to and from
 * data sources
 */
class StudentDataRepository(private val factory: StudentDataStoreFactory,
                            private val studentMapper: StudentMapper) :
        StudentRepository {

    override fun getStudentSignedIn(): Flowable<Student> {
        return factory.retrieveCacheDataStore()!!.getSignedInStudent()
    }

    override fun askForLogout(): Completable {
        return Completable.complete()
    }

    override fun getCoursesForStudent(studentNumber: String): Flowable<List<Course>> {
        throw OperationNotSupportedException()
    }

    override fun askForLogin(studentNumber: String, password: String): Completable {
        return factory.retrieveRemoteDataStore()!!.askForLogin(studentNumber, password)
    }

    override fun register(studentNumber: String, firstName: String, lastName: String,
                          password: String): Completable {
        return factory.retrieveRemoteDataStore()!!.register(studentNumber, firstName, lastName, password)
    }
}