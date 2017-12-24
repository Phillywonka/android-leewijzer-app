package philip.com.data

import io.reactivex.Completable
import io.reactivex.Flowable
import philip.com.data.mapper.StudentMapper
import philip.com.data.source.student.StudentDataStoreFactory
import philip.com.domain.model.Course
import philip.com.domain.model.Student
import philip.com.domain.repository.StudentRepository

/**
 * Provides an implementation of the [StudentRepository] interface for communicating to and from
 * data sources
 */
class StudentDataRepository(private val factory: StudentDataStoreFactory,
                            private val studentMapper: StudentMapper) :
        StudentRepository {

    override fun getStudentSignedIn(): Flowable<Student> {
        return factory.retrieveCacheDataStore().getSignedInStudent()
    }

    override fun askForLogout(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCoursesForStudent(studentNumber: String): Flowable<List<Course>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun askForLogin(studentNumber: String, password: String): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}