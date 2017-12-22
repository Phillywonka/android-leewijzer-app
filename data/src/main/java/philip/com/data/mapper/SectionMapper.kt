package philip.com.data.mapper

import org.buffer.android.boilerplate.data.mapper.Mapper
import philip.com.data.models.CourseEntity
import philip.com.data.models.SectionEntity
import philip.com.domain.model.Course
import philip.com.domain.model.Section


/**
 * Map a [CourseEntity] to and from a [Course] instance when data is moving between
 * this later and the Domain layer
 */
open class SectionMapper : Mapper<SectionEntity, Section> {


    /**
     * Map a [CourseEntity] instance to a [Course] instance
     */
    override fun mapFromEntity(type: SectionEntity): Section {
        return Section(type.id, type.name, type.isChecked, type.courseName)
    }

    /**
     * Map a [Course] instance to a [CourseEntity] instance
     */
    override fun mapToEntity(type: Section): SectionEntity {
        return SectionEntity(type.id, type.name, type.isChecked, type.courseName)
    }


}