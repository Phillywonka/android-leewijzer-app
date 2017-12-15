package philip.com.cache.factory

import philip.com.cache.factory.DataFactory.Factory.randomUuid
import philip.com.cache.model.CachedStudent

/**
 * Factory class for Student related instances
 */
class StudentFactory {

    companion object Factory {

        fun makeCachedStudent(): CachedStudent {
            return CachedStudent(randomUuid(), randomUuid(), randomUuid(), randomUuid())
        }

        fun makeCachedStudentList(count: Int): List<CachedStudent> {
            val cachedStudents = mutableListOf<CachedStudent>()
            repeat(count) {
                cachedStudents.add(makeCachedStudent())
            }
            return cachedStudents
        }

    }

}