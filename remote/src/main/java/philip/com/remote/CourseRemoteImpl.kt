package philip.com.remote

import io.reactivex.Completable
import io.reactivex.Flowable
import philip.com.data.models.CourseEntity
import philip.com.data.repository.course.CourseRemote
import philip.com.remote.mapper.CourseEntityMapper
import javax.xml.ws.http.HTTPException

/**
 * Remote implementation for retrieving Course instances. This class implements the
 * [CourseRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class CourseRemoteImpl(private val courseService: CourseService,
                       private val entityMapper: CourseEntityMapper) :
        CourseRemote {

    /**
     * Retrieve a list of selected [CourseEntity] instances from the [CourseService].
     */
    override fun getSelectedCourses(studentNumber: String): Flowable<List<CourseEntity>> {
        return courseService.getSelectedCourses(studentNumber)
                .map { it.courses }
                .map {
                    val entities = mutableListOf<CourseEntity>()
                    it.forEach {
                        entities.add(entityMapper.mapFromRemote(it))
                    }
                    entities
                }
    }

    /**
     * Retrieve a list of all available [CourseEntity] instances from the [CourseService].
     */
    override fun getAllCourses(): Flowable<List<CourseEntity>> {
        return courseService.getAllCourses()
                .map { it.courses }
                .map {
                    val entities = mutableListOf<CourseEntity>()
                    it.forEach {
                        entities.add(entityMapper.mapFromRemote(it))
                    }
                    entities
                }
    }

    /**
     * Add a new course to a student
     */
    override fun addCourse(studentNumber: String, courseName: String): Completable {

        return try {
            courseService.addCourse(studentNumber, courseName)
        } catch (error: HTTPException) {
            Completable.error(error)
        }
    }

}