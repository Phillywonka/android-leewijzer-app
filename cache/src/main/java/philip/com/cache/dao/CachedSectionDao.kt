package philip.com.cache.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import philip.com.cache.constants.SectionConstants
import philip.com.cache.model.CachedCourse


/**
 * @author Philip Wong
 * @since 09-12-17
 **/
@Dao
interface CachedSectionDao {

    @Query(SectionConstants.QUERY_SECTIONS)
    fun loadAllSections(): List<CachedCourse>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSection(student: CachedCourse)

    @Query(SectionConstants.DELETE_ALL_SECTIONS)
    fun clearSections()
}
