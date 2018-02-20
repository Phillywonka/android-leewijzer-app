package philip.com.data.repository.section

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import philip.com.data.models.SectionEntity

/**
 * Interface defining methods for the data operations related to Section.
 * This is to be implemented by external data source layers, setting the requirements for the
 * operations that need to be implemented
 */
interface SectionDataStore {

    fun clearSections(): Completable

    fun saveSections(sections: List<SectionEntity>): Completable

    fun getSections(studentNumber: String, courseName: String): Flowable<List<SectionEntity>>

    fun selectSection(studentNumber: String, courseName: String, sectionId: Int): Flowable<Int>

    fun isCached(courseName: String): Single<Boolean>

}