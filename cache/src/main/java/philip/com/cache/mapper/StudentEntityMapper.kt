package philip.com.cache.mapper

import philip.com.cache.model.CachedSection
import philip.com.cache.model.CachedStudent
import philip.com.data.models.SectionEntity
import philip.com.data.models.StudentEntity

/**
 * @author Philip Wong
 * @since 09-03-18
 *
 * Map a [CachedSection] instance to and from a [SectionEntity] instance when data is moving between
 * this later and the Data layer
 */
open class StudentEntityMapper : EntityMapper<CachedStudent, StudentEntity> {

    /**
     * Map a [StudentEntity] instance to a [CachedStudent] instance
     */
    override fun mapToCached(type: StudentEntity): CachedStudent {
        return CachedStudent(type.number, type.fistName, type.lastName, type.password)
    }

    /**
     * Map a [CachedStudent] instance to a [StudentnEntity] instance
     */
    override fun mapFromCached(type: CachedStudent): StudentEntity {
        return StudentEntity(type.number, type.firstName, type.lastName, type.password)
    }

}