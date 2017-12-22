package philip.com.remote.mapper

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import philip.com.remote.test.factory.CourseFactory
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class CourseEntityMapperTest {

    private lateinit var courseEntityMapper: CourseEntityMapper

    @Before
    fun setUp() {
        courseEntityMapper = CourseEntityMapper()
    }

    @Test
    fun mapFromRemoteMapsData() {
        val courseModel = CourseFactory.makeCourseModel()
        val courseEntity = courseEntityMapper.mapFromRemote(courseModel)

        assertEquals(courseModel.name, courseEntity.name)
    }

}