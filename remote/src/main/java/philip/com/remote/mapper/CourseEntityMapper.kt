package philip.com.remote.mapper

import philip.com.data.models.CourseEntity
import philip.com.remote.model.CourseModel


/**
 * Map a [CourseModel] to and from a [CourseEntity] instance when data is moving between
 * this later and the Data layer
 */
open class CourseEntityMapper: EntityMapper<CourseModel, philip.com.data.models.CourseEntity> {

    /**
     * Map an instance of a [CourseModel] to a [CourseEntity] model
     */
    override fun mapFromRemote(type: CourseModel): CourseEntity {
        return CourseEntity(type.name, type.fieldOfStudy)
    }

}