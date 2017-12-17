package philip.com.remote

import io.reactivex.Flowable
import org.buffer.android.boilerplate.data.repository.CourseRemote
import philip.com.data.models.CourseEntity
import philip.com.remote.mapper.CourseEntityMapper

/**
 * Remote implementation for retrieving Course instances. This class implements the
 * [CourseRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class CourseRemoteImpl (private val courseService: CourseService,
                        private val entityMapper: CourseEntityMapper):
        CourseRemote {

    /**
     * Retrieve a list of [CourseEntity] instances from the [CourseService].
     */
    override fun getCourses(): Flowable<List<CourseEntity>> {
        return courseService.getCourses()
                .map { it.team }
                .map {
                    val entities = mutableListOf<CourseEntity>()
                    it.forEach { entities.add(entityMapper.mapFromRemote(it)) }
                    entities
                }
    }

}