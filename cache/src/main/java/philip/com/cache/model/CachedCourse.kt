package philip.com.cache.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import philip.com.cache.constants.CourseConstants

/**
 * @author Philip Wong
 * @since 12-12-17
 **/
@Entity(tableName = CourseConstants.TABLE_NAME)
data class CachedCourse(

        @PrimaryKey
        val name: String,
        val fieldOfStudy: String)
