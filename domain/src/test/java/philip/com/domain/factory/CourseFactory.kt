package philip.com.domain.factory

import philip.com.domain.factory.DataFactory.Factory.randomUuid
import philip.com.domain.model.Course

/**
 * Factory class for Course related instances
 */
class CourseFactory {

    companion object Factory {

        fun makeCourseList(count: Int): List<Course> {
            val courses = mutableListOf<Course>()
            repeat(count) {
                courses.add(makeCourse())
            }
            return courses
        }

        fun makeCourse(): Course {
            return Course(randomUuid(), randomUuid())
        }

    }

}