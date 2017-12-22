package philip.com.remote.test.factory

import philip.com.remote.SectionService
import philip.com.remote.model.SectionModel
import philip.com.remote.test.factory.DataFactory.Factory.randomBoolean
import philip.com.remote.test.factory.DataFactory.Factory.randomLong
import philip.com.remote.test.factory.DataFactory.Factory.randomUuid

/**
 * Factory class for Section related instances
 */
class SectionFactory {

    companion object Factory {

        fun makeSectionResponse(): SectionService.SectionResponse {
            val sectionResponse = SectionService.SectionResponse()
            sectionResponse.sections = makeSectionModelList(5)
            return sectionResponse
        }

        fun makeSectionModelList(count: Int): List<SectionModel> {
            val sectionEntities = mutableListOf<SectionModel>()
            repeat(count) {
                sectionEntities.add(makeSectionModel())
            }
            return sectionEntities
        }

        fun makeSectionModel(): SectionModel {
            return SectionModel(randomLong(), randomUuid(), randomBoolean(), randomUuid())
        }
    }

}