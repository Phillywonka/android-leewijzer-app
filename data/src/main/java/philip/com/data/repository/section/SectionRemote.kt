package philip.com.data.repository.section

import io.reactivex.Completable
import io.reactivex.Flowable
import philip.com.data.models.SectionEntity

/**
 * Interface defining methods for the caching of Sections. This is to be implemented by the
 * cache layer, using this interface as a way of communicating.
 */
interface SectionRemote {

    /**
     * Retrieve a list of Sections, from the remote
     */
    fun getSelectedSectionsForCourse(studentNumber: String, courseName: String): Flowable<List<SectionEntity>>

    fun getAllSectionsForCourse(courseName: String): Flowable<List<SectionEntity>>

    /**
     * Add a new course to a student
     */
    fun addSection(studentNumber: String, courseName: String): Completable

}