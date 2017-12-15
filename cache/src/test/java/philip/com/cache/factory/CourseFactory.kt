package philip.com.cache.factory

import philip.com.cache.factory.DataFactory.Factory.randomUuid
import philip.com.cache.model.CachedCourse

/**
 * Factory class for Course related instances
 */
class CourseFactory {

    companion object Factory {

        fun makeCachedCourse(): CachedCourse {
            return CachedCourse(randomUuid(), randomUuid())
        }

        fun makeCachedCourseList(count: Int): List<CachedCourse> {
            val cachedCourses = mutableListOf<CachedCourse>()
            repeat(count) {
                cachedCourses.add(makeCachedCourse())
            }
            return cachedCourses
        }

    }

}