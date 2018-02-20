package philip.com.data.source.section

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import philip.com.data.models.SectionEntity
import philip.com.data.repository.section.SectionDataStore
import philip.com.data.repository.section.SectionRemote

/**
 * Implementation of the [SectionDataStore] interface to provide a means of communicating
 * with the remote data source
 */
open class SectionRemoteDataStore(private val sectionRemote: SectionRemote)
    : SectionDataStore {

    override fun clearSections(): Completable {
        throw UnsupportedOperationException()
    }

    override fun saveSections(sections: List<SectionEntity>): Completable {
        throw UnsupportedOperationException()
    }

    override fun getSections(studentNumber: String, courseName: String): Flowable<List<SectionEntity>> {
        return sectionRemote.getSelectedSectionsForCourse(studentNumber, courseName)
    }

    override fun selectSection(studentNumber: String, courseName: String, sectionId: Int, selected: Boolean): Flowable<Int> {
        return sectionRemote.selectSection(studentNumber, courseName, sectionId, selected)
    }

    override fun isCached(courseName: String): Single<Boolean> {
        throw UnsupportedOperationException()
    }
}
