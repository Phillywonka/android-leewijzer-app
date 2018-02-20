package philip.com.data

import io.reactivex.Completable
import io.reactivex.Flowable
import philip.com.data.mapper.SectionMapper
import philip.com.data.models.SectionEntity
import philip.com.data.source.section.SectionDataStoreFactory
import philip.com.domain.model.Section
import philip.com.domain.repository.SectionRepository

/**
 * Provides an implementation of the [SectionRepository] interface for communicating to and from
 * data sources
 */
class SectionDataRepository(private val factory: SectionDataStoreFactory,
                            private val sectionMapper: SectionMapper) :
        SectionRepository {

    override fun selectSection(studentNumber: String, courseName: String, sectionId: Int): Flowable<Int> {
        return factory.retrieveRemoteDataStore().selectSection(studentNumber, courseName, sectionId)
                .flatMap { clearSections().toSingle { it }.toFlowable() }
    }

    override fun clearSections(): Completable {
        return factory.retrieveCacheDataStore().clearSections()
    }

    override fun saveSections(sections: List<Section>): Completable {
        val sectionEntities = mutableListOf<SectionEntity>()
        sections.map { sectionEntities.add(sectionMapper.mapToEntity(it)) }
        return factory.retrieveCacheDataStore().saveSections(sectionEntities)
    }

    override fun getSections(studentNumber: String, courseName: String): Flowable<List<Section>> {
        return factory.retrieveCacheDataStore().isCached(courseName)
                .flatMapPublisher {
                    factory.retrieveDataStore(it).getSections(studentNumber, courseName)
                }
                .flatMap {
                    Flowable.just(it.map { sectionMapper.mapFromEntity(it) })
                }
                .flatMap {
                    saveSections(it).toSingle { it }.toFlowable()
                }
    }

}