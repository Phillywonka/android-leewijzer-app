package philip.com.data.mapper

import junit.framework.Assert.assertEquals
import org.buffer.android.boilerplate.domain.model.Course
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import philip.com.data.models.CourseEntity
import philip.com.data.test.factory.CourseFactory

@RunWith(JUnit4::class)
class CourseMapperTest {

    private lateinit var courseMapper: CourseMapper

    @Before
    fun setUp() {
        courseMapper = CourseMapper(SectionMapper())
    }

    @Test
    fun mapFromEntityMapsData() {
        val bufferooEntity = CourseFactory.makeCourseEntity()
        val bufferoo = courseMapper.mapFromEntity(bufferooEntity)

        assertBufferooDataEquality(bufferooEntity, bufferoo)
    }

    @Test
    fun mapToEntityMapsData() {
        val cachedBufferoo = CourseFactory.makeCourse()
        val bufferooEntity = courseMapper.mapToEntity(cachedBufferoo)

        assertBufferooDataEquality(bufferooEntity, cachedBufferoo)
    }

    private fun assertBufferooDataEquality(courseEntity: CourseEntity,
                                           course: Course) {
        assertEquals(courseEntity.name, course.name)
        assertEquals(courseEntity.fieldOfStudy, course.fieldOfStudy)
    }

}