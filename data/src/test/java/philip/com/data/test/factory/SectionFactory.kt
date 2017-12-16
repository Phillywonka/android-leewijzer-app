package philip.com.data.test.factory

import org.buffer.android.boilerplate.data.test.factory.DataFactory.Factory.randomBoolean
import org.buffer.android.boilerplate.data.test.factory.DataFactory.Factory.randomLong
import org.buffer.android.boilerplate.data.test.factory.DataFactory.Factory.randomUuid
import org.buffer.android.boilerplate.domain.model.Section
import philip.com.data.models.SectionEntity

/**
 * Factory class for Section related instances
 */
class SectionFactory {

    companion object Factory {

        fun makeSectionEntity(): SectionEntity {
            return SectionEntity(randomLong(), randomUuid(), randomBoolean())
        }

        fun makeSection(): Section {
            return Section(randomLong(), randomUuid(), randomBoolean())
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