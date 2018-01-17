package philip.com.remote.mapper

import philip.com.data.models.CourseEntity
import philip.com.data.models.SectionEntity
import philip.com.remote.model.CourseModel
import philip.com.remote.model.SectionModel


/**
 * Map a [CourseModel] to and from a [CourseEntity] instance when data is moving between
 * this later and the Data layer
 */
open class SectionEntityMapper : EntityMapper<SectionModel, SectionEntity> {

    /**
     * Map an instance of a [CourseModel] to a [CourseEntity] model
     */
    override fun mapFromRemote(type: SectionModel): SectionEntity {
        return SectionEntity(type.id, type.name, false, type.courseName)
    }

}