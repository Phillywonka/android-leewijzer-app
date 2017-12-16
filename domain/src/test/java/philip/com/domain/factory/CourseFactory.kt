package philip.com.domain.factory

import org.buffer.android.boilerplate.domain.model.Course
import philip.com.domain.SectionFactory
import philip.com.domain.factory.DataFactory.Factory.randomInt
import philip.com.domain.factory.DataFactory.Factory.randomUuid

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
            return Course(randomUuid(), randomUuid(), SectionFactory.makeSectionList(randomInt()))
        }

    }

}