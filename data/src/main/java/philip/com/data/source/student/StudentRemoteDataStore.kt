package philip.com.data.source.student

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import philip.com.data.repository.student.StudentDatastore
import philip.com.data.repository.student.StudentRemote
import philip.com.domain.model.Student

/**
 * Implementation of the [StudentDataStore] interface to provide a means of communicating
 * with the remote data source
 */
open class StudentRemoteDataStore(private val studentRemote: StudentRemote) :
        StudentDatastore {

    override fun getSignedInStudent(): Flowable<Student> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun askForLogin(studentNumber: String, password: String): Completable {
        return studentRemote.login(studentNumber, password)
    }

    override fun askForLogout(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getStudentsForStudent(studentNumber: String): Flowable<List<Student>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun isCached(): Single<Boolean> {
        throw UnsupportedOperationException()
    }

}