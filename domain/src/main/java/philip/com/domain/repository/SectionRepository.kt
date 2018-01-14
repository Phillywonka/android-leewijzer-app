package philip.com.domain.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import philip.com.domain.model.Section

/**
 * Interface defining methods for how the data layer can pass data to and from the Domain layer.
 * This is to be implemented by the data layer, setting the requirements for the
 * operations that need to be implemented
 */
interface SectionRepository {

    fun clearSections(): Completable

    fun saveSections(sections: List<Section>): Completable

    fun getSections(studentNumber: String, courseName: String): Flowable<List<Section>>

}