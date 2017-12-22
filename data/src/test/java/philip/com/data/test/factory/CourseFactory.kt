package philip.com.data.test.factory

import philip.com.data.test.factory.DataFactory.Factory.randomUuid
import philip.com.data.models.CourseEntity
import philip.com.domain.model.Course

/**
 * Factory class for Course related instances
 */
class CourseFactory {

    companion object Factory {

        fun makeCourseEntity(): CourseEntity {
            return CourseEntity(
                    randomUuid(),
                    randomUuid())
        }

        fun makeCourse(): Course {
            return Course(randomUuid(), randomUuid())
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