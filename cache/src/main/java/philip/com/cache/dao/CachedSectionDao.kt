package philip.com.cache.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import philip.com.cache.constants.SectionConstants
import philip.com.cache.model.CachedSection


/**
 * @author Philip Wong
 * @since 09-12-17
 **/
@Dao
interface CachedSectionDao {

    @Query(SectionConstants.QUERY_SECTIONS)
    fun getSelectedSections(courseName: String): List<CachedSection>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSection(student: CachedSection)

    @Query(SectionConstants.DELETE_ALL_SECTIONS)
    fun clearSections()
}
