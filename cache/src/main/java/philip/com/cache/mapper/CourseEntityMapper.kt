package philip.com.cache.mapper

import org.buffer.android.boilerplate.cache.mapper.EntityMapper
import philip.com.cache.model.CachedCourse
import philip.com.data.models.CourseEntity

/**
 * Map a [CachedCourse] instance to and from a [CourseEntity] instance when data is moving between
 * this later and the Data layer
 */
open class CourseEntityMapper: EntityMapper<CachedCourse, CourseEntity> {

    /**
     * Map a [CourseEntity] instance to a [CachedCourse] instance
     */
    override fun mapToCached(type: CourseEntity): CachedCourse {
        return CachedCourse(type.name, type.fieldOfStudy)
    }

    /**
     * Map a [CachedCourse] instance to a [CourseEntity] instance
     */
    override fun mapFromCached(type: CachedCourse): CourseEntity {
        return CourseEntity(type.name, type.fieldOfStudy)
    }

}