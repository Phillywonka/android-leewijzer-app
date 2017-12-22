package philip.com.cache.mapper

import philip.com.cache.model.CachedSection
import philip.com.data.models.SectionEntity

/**
 * Map a [CachedSection] instance to and from a [SectionEntity] instance when data is moving between
 * this later and the Data layer
 */
open class SectionEntityMapper : EntityMapper<CachedSection, SectionEntity> {

    /**
     * Map a [SectionEntity] instance to a [CachedSection] instance
     */
    override fun mapToCached(type: SectionEntity): CachedSection {
        return CachedSection(type.id, type.name, type.isChecked, type.courseName)
    }

    /**
     * Map a [CachedSection] instance to a [SectionEntity] instance
     */
    override fun mapFromCached(type: CachedSection): SectionEntity {
        return SectionEntity(type.id, type.name, type.isChecked, type.courseName)
    }

}