package philip.com.data.mapper

import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import philip.com.data.models.CourseEntity
import philip.com.data.test.factory.CourseFactory
import philip.com.domain.model.Course

@RunWith(JUnit4::class)
class CourseMapperTest {

    private lateinit var courseMapper: CourseMapper

    @Before
    fun setUp() {
        courseMapper = CourseMapper()
    }

    @Test
    fun mapFromEntityMapsData() {
        val courseEntity = CourseFactory.makeCourseEntity()
        val course = courseMapper.mapFromEntity(courseEntity)

        assertCourseDataEquality(courseEntity, course)
    }

    @Test
    fun mapToEntityMapsData() {
        val cachedCourse = CourseFactory.makeCourse()
        val courseEntity = courseMapper.mapToEntity(cachedCourse)

        assertCourseDataEquality(courseEntity, cachedCourse)
    }

    private fun assertCourseDataEquality(courseEntity: CourseEntity,
                                           course: Course) {
        assertEquals(courseEntity.name, course.name)
        assertEquals(courseEntity.fieldOfStudy, course.fieldOfStudy)
    }

}