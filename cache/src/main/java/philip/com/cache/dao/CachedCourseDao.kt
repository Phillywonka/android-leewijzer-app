package philip.com.cache.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import philip.com.cache.constants.CourseConstants
import philip.com.cache.model.CachedCourse


/**
 * @author Philip Wong
 * @since 09-12-17
 **/
@Dao
interface CachedCourseDao {

    @Query(CourseConstants.QUERY_COURSES)
    fun loadSelectedCourses(): List<CachedCourse>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCourse(student: CachedCourse)

    @Query(CourseConstants.DELETE_ALL_COURSES)
    fun clearCourses()
}
