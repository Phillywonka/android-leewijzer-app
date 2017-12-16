package org.buffer.android.boilerplate.data

import io.reactivex.Completable
import io.reactivex.Flowable
import org.buffer.android.boilerplate.data.mapper.CourseMapper
import philip.com.data.source.CourseDataStoreFactory
import org.buffer.android.boilerplate.domain.repository.CourseRepository
import philip.com.data.models.CourseEntity

/**
 * Provides an implementation of the [CourseRepository] interface for communicating to and from
 * data sources
 */
class CourseDataRepository(private val factory: CourseDataStoreFactory,
                           private val courseMapper: CourseMapper) :
        CourseRepository {
    override fun saveCourses(courses: List<org.buffer.android.boilerplate.domain.model.Course>): Completable {
        val courseEntities = mutableListOf<CourseEntity>()
        courses.map { courseEntities.add(courseMapper.mapToEntity(it)) }
        return factory.retrieveCacheDataStore().saveCourses(courseEntities)
    }

    override fun clearCourses(): Completable {
        return factory.retrieveCacheDataStore().clearCourses()
    }

    override fun getCourses(): Flowable<List<org.buffer.android.boilerplate.domain.model.Course>> {
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

}