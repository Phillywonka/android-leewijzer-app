package philip.com.data.mapper

import org.buffer.android.boilerplate.data.mapper.Mapper
import org.buffer.android.boilerplate.domain.model.Course
import org.buffer.android.boilerplate.domain.model.Section
import philip.com.data.models.CourseEntity
import philip.com.data.models.SectionEntity


/**
 * Map a [CourseEntity] to and from a [Course] instance when data is moving between
 * this later and the Domain layer
 */
open class CourseMapper(private val sectionMapper: SectionMapper) : Mapper<CourseEntity, Course> {


    /**
     * Map a [CourseEntity] instance to a [Course] instance
     */
    override fun mapFromEntity(type: CourseEntity): Course {
        val sections = ArrayList<Section>()
        type.sections.mapTo(sections) { sectionMapper.mapFromEntity(it) }
        return Course(type.name, type.fieldOfStudy, sections)
    }

    /**
     * Map a [Course] instance to a [CourseEntity] instance
     */
    override fun mapToEntity(type: Course): CourseEntity {
        val sections = ArrayList<SectionEntity>()
        type.sections.mapTo(sections) { sectionMapper.mapToEntity(it) }

        return CourseEntity(type.name, type.fieldOfStudy, sections)
    }


}