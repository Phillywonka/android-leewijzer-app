package philip.com.remote.mapper

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import philip.com.remote.test.factory.SectionFactory
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class SectionEntityMapperTest {

    private lateinit var sectionEntityMapper: SectionEntityMapper

    @Before
    fun setUp() {
        sectionEntityMapper = SectionEntityMapper()
    }

    @Test
    fun mapFromRemoteMapsData() {
        val sectionModel = SectionFactory.makeSectionModel()
        val sectionEntity = sectionEntityMapper.mapFromRemote(sectionModel)

        assertEquals(sectionModel.id, sectionEntity.id)
        assertEquals(sectionModel.name, sectionEntity.name)
        assertEquals(sectionModel.courseName, sectionEntity.courseName)
        assertEquals(sectionModel.isSelected, sectionEntity.isChecked)
    }

}