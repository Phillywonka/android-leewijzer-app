package philip.com.cache.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import philip.com.cache.constants.SectionConstants

/**
 * @author Philip Wong
 * @since 12-12-17
 **/
@Entity(tableName = SectionConstants.TABLE_NAME)
data class CachedSection(

        @PrimaryKey
        val id: Int,
        @ColumnInfo(name = "name")
        val name: String,
        @ColumnInfo(name = "isChecked")
        val isChecked: Boolean,
        @ColumnInfo(name = "courseName")
        val courseName: String)

