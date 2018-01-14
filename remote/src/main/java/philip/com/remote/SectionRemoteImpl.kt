package philip.com.remote

import io.reactivex.Completable
import io.reactivex.Flowable
import philip.com.data.models.SectionEntity
import philip.com.data.repository.section.SectionRemote
import philip.com.remote.mapper.SectionEntityMapper

/**
 * Remote implementation for retrieving Section instances. This class implements the
 * [SectionRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class SectionRemoteImpl(private val sectionService: SectionService,
                        private val entityMapper: SectionEntityMapper) : SectionRemote {

    override fun getSelectedSectionsForCourse(studentNumber: String, courseName: String): Flowable<List<SectionEntity>> {

        return sectionService.getSections(studentNumber, courseName)
                .map { it.sections }
                .map {
                    val entities = mutableListOf<SectionEntity>()
                    it.forEach {
                        entities.add(entityMapper.mapFromRemote(it))
                    }
                    entities
                }
    }

    override fun getAllSectionsForCourse(courseName: String): Flowable<List<SectionEntity>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addSection(studentNumber: String, courseName: String): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}