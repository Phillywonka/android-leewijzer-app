package philip.com.domain

import philip.com.domain.factory.DataFactory.Factory.randomBoolean
import philip.com.domain.factory.DataFactory.Factory.randomLong
import philip.com.domain.factory.DataFactory.Factory.randomUuid
import philip.com.domain.model.Section

/**
 * Factory class for Course related instances
 */
class SectionFactory {

    companion object Factory {

        fun makeSectionList(count: Int): List<Section> {
            val sections = mutableListOf<Section>()
            repeat(count) {
                sections.add(makeSection())
            }
            return sections
        }

        fun makeSection(): Section {
            return Section(randomLong(), randomUuid(), randomBoolean(), randomUuid())
        }

    }

}