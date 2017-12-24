package philip.com.data

import io.reactivex.Completable
import io.reactivex.Flowable
import philip.com.data.mapper.CourseMapper
import philip.com.data.models.CourseEntity
import philip.com.data.source.course.CourseDataStoreFactory
import philip.com.domain.model.Course
import philip.com.domain.repository.CourseRepository

/**
 * Provides an implementation of the [CourseRepository] interface for communicating to and from
 * data sources
 */
class CourseDataRepository(private val factory: CourseDataStoreFactory,
                           private val courseMapper: CourseMapper) :
        CourseRepository {

    override fun saveCourses(courses: List<Course>): Completable {
        val courseEntities = mutableListOf<CourseEntity>()
        courses.map { courseEntities.add(courseMapper.mapToEntity(it)) }
        return factory.retrieveCacheDataStore().saveCourses(courseEntities)
    }

    override fun clearCourses(): Completable {
        return factory.retrieveCacheDataStore().clearCourses()
    }

    override fun getCourses(): Flowable<List<Course>> {
        return factory.retrieveCacheDataStore().isCached()
                .flatMapPublisher {
                    factory.retrieveDataStore(it).getCourses()
                }
                .flatMap {
                    Flowable.just(it.map { courseMapper.mapFromEntity(it) })
                }
                .flatMap {
                    saveCourses(it).toSingle { it }.toFlowable()
                }
    }

    override fun getCoursesForStudent(studentNumber: String): Flowable<List<Course>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}