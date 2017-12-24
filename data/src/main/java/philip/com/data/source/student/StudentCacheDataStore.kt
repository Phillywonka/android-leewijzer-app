package philip.com.data.source.student

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import philip.com.data.models.StudentEntity
import philip.com.data.repository.student.StudentCache
import philip.com.data.repository.student.StudentDatastore
import philip.com.domain.model.Student

/**
 * Implementation of the [StudentDataStore] interface to provide a means of communicating
 * with the local data source
 */
open class StudentCacheDataStore(private val studentCache: StudentCache) :
        StudentDatastore {

    override fun getSignedInStudent(): Flowable<Student> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getStudentsForStudent(studentNumber: String): Flowable<List<Student>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun askForLogin(studentNumber: String, password: String): Completable {
        throw UnsupportedOperationException()
    }

    override fun askForLogout(): Completable {
        throw UnsupportedOperationException()
    }

    /**
     * Retrieve a list of [StudentEntity] instance from the cache
     */
    override fun isCached(): Single<Boolean> {
        return studentCache.isCached()
    }

}