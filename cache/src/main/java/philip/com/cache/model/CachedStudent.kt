package philip.com.cache.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import philip.com.cache.constants.StudentConstants

/**
 * @author Philip Wong
 * @since 12-12-17
 **/
@Entity(tableName = StudentConstants.TABLE_NAME)
data class CachedStudent(

        @PrimaryKey
        val number: String,
        val firstName: String,
        val lastName: String,
        val password: String
)
