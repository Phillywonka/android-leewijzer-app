package philip.com.data.mapper

import org.buffer.android.boilerplate.data.mapper.Mapper
import philip.com.data.models.CourseEntity
import philip.com.domain.model.Course


/**
 * Map a [CourseEntity] to and from a [Course] instance when data is moving between
 * this later and the Domain layer
 */
open class CourseMapper : Mapper<CourseEntity, Course> {


    /**
     * Map a [CourseEntity] instance to a [Course] instance
     */
    override fun mapFromEntity(type: CourseEntity): Course {
        return Course(type.name, type.fieldOfStudy)
    }

    /**
     * Map a [Course] instance to a [CourseEntity] instance
     */
    override fun mapToEntity(type: Course): CourseEntity {

        return CourseEntity(type.name, type.fieldOfStudy)
    }


}