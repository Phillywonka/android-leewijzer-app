package philip.com.remote

import io.reactivex.Completable
import io.reactivex.Flowable
import philip.com.data.models.CourseEntity
import philip.com.data.models.SectionEntity
import philip.com.data.repository.CourseRemote
import philip.com.remote.mapper.SectionEntityMapper

/**
 * Remote implementation for retrieving Course instances. This class implements the
 * [CourseRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class SectionRemoteImpl(private val sectionService: SectionService,
                        private val entityMapper: SectionEntityMapper) {

    fun setSelectedSections(sections: List<SectionEntity>,
                            studentNumber: String,
                            courseName: String): Completable {

        return sectionService.setSections(sections, studentNumber, courseName)
    }

    /**
     * Retrieve a list of [CourseEntity] instances from the [CourseService].
     */
    fun getSections(courseName: String, studentNumber: String): Flowable<List<SectionEntity>> {
        return sectionService.getSections(courseName, studentNumber).map {
            it.sections
        }.map {
            val entities = mutableListOf<SectionEntity>()
            it.forEach { entities.add(entityMapper.mapFromRemote(it)) }
            entities
        }
    }
}