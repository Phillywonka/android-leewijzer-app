package philip.com.data.test.factory

import org.buffer.android.boilerplate.data.test.factory.DataFactory.Factory.randomInt
import org.buffer.android.boilerplate.data.test.factory.DataFactory.Factory.randomUuid
import org.buffer.android.boilerplate.domain.model.Course
import philip.com.data.models.CourseEntity

/**
 * Factory class for Course related instances
 */
class CourseFactory {

    companion object Factory {

        fun makeCourseEntity(): CourseEntity {
            return CourseEntity(
                    randomUuid(),
                    randomUuid(),
                    SectionFactory.makeSectionEntityList(randomInt()))
        }

        fun makeCourse(): Course {
            return Course(randomUuid(), randomUuid(), SectionFactory.makeSectionList(randomInt()))
        }

        fun makeCourseEntityList(count: Int): List<CourseEntity> {
            val courseEntities = mutableListOf<CourseEntity>()
            repeat(count) {
                courseEntities.add(makeCourseEntity())
            }
            return courseEntities
        }

        fun makeCourseList(count: Int): List<Course> {
            val courses = mutableListOf<Course>()
            repeat(count) {
                courses.add(makeCourse())
            }
            return courses
        }

    }

}