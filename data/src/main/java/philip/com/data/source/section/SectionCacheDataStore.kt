package philip.com.data.source.section

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import philip.com.data.models.SectionEntity
import philip.com.data.repository.section.SectionCache
import philip.com.data.repository.section.SectionDataStore

/**
 * Implementation of the [SectionDataStore] interface to provide a means of communicating
 * with the local data source
 */
open class SectionCacheDataStore(private val sectionCache: SectionCache) : SectionDataStore {

    override fun clearSections(): Completable {
        return sectionCache.clearSections()
    }

    override fun saveSections(sections: List<SectionEntity>): Completable {
        return sectionCache.saveSections(sections)
                .doOnComplete {
                    sectionCache.setLastCacheTime(System.currentTimeMillis())
                }
    }

    override fun getSections(studentNumber: String, courseName: String): Flowable<List<SectionEntity>> {
        return sectionCache.getSections(courseName)
    }

    override fun isCached(courseName: String): Single<Boolean> {
        return sectionCache.isCached(courseName)
    }
}