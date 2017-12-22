package philip.com.data.test.factory

import philip.com.data.test.factory.DataFactory.Factory.randomBoolean
import philip.com.data.test.factory.DataFactory.Factory.randomLong
import philip.com.data.test.factory.DataFactory.Factory.randomUuid
import philip.com.data.models.SectionEntity
import philip.com.domain.model.Section

/**
 * Factory class for Section related instances
 */
class SectionFactory {

    companion object Factory {

        fun makeSectionEntity(): SectionEntity {
            return SectionEntity(randomLong(), randomUuid(), randomBoolean(), randomUuid())
        }

        fun makeSection(): Section {
            return Section(randomLong(), randomUuid(), randomBoolean(), randomUuid())
        }

        fun makeSectionEntityList(count: Int): List<SectionEntity> {
            val sectionEntities = mutableListOf<SectionEntity>()
            repeat(count) {
                sectionEntities.add(makeSectionEntity())
            }
            return sectionEntities
        }

        fun makeSectionList(count: Int): List<Section> {
            val sections = mutableListOf<Section>()
            repeat(count) {
                sections.add(makeSection())
            }
            return sections
        }

    }

}