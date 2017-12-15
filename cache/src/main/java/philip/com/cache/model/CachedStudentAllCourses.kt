package philip.com.cache.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation

/**
 * @author Philip Wong
 * @since 15-12-17
 **/
data class CachedStudentAllCourses(

        @Embedded
        val student: CachedStudent,

        @Relation(parentColumn = "id", entityColumn = "studentNumber", entity = CachedCourse::class)
        val courses: List<CachedCourse>
)
