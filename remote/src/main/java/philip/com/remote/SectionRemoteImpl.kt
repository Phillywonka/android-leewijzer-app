package philip.com.remote

import io.reactivex.Flowable
import io.reactivex.functions.BiFunction
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
                .map {
                    val entities = mutableListOf<SectionEntity>()
                    it.sections.forEach {
                        val entity = entityMapper.mapFromRemote(it)
                        entity.isChecked = true
                        entities.add(entity)
                    }
                    entities
                }.zipWith(getAllSectionsForCourse(courseName),
                        BiFunction<List<SectionEntity>, List<SectionEntity>, List<SectionEntity>> { a, b ->
                            (a + b).distinctBy { it.id }
                        })
    }

    override fun getAllSectionsForCourse(courseName: String): Flowable<List<SectionEntity>> {
        return sectionService.getAllSectionsForCourse(courseName)
                .map { it.sections }
                .map {
                    val entities = mutableListOf<SectionEntity>()
                    it.forEach {
                        entities.add(entityMapper.mapFromRemote(it))
                    }
                    entities
                }
    }

    override fun selectSection(studentNumber: String,
                               courseName: String,
                               sectionId: Int,
                               selected: Boolean): Flowable<Int> {
        return if (selected) {
            sectionService.selectSection(studentNumber, courseName, sectionId)
        } else {
            sectionService.deSelectSection(studentNumber, sectionId)
        }
    }

}