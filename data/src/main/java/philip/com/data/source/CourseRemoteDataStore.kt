package philip.com.data.source

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import org.buffer.android.boilerplate.data.repository.CourseDataStore
import org.buffer.android.boilerplate.data.repository.CourseRemote
import philip.com.data.models.CourseEntity

/**
 * Implementation of the [CourseDataStore] interface to provide a means of communicating
 * with the remote data source
 */
open class CourseRemoteDataStore constructor(private val bufferooRemote: CourseRemote) :
        CourseDataStore {

    override fun clearCourses(): Completable {
        throw UnsupportedOperationException()
    }

    override fun saveCourses(courses: List<CourseEntity>): Completable {
        throw UnsupportedOperationException()
    }

    /**
     * Retrieve a list of [CourseEntity] instances from the API
     */
    override fun getCourses(): Flowable<List<CourseEntity>> {
        return bufferooRemote.getCourses()
    }

    override fun isCached(): Single<Boolean> {
        throw UnsupportedOperationException()
    }

}