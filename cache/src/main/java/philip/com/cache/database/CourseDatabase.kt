package philip.com.cache.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.philip.leeswijzer_app.storage.cache.CachedCourseDao
import philip.com.cache.model.CachedCourse

/**
 * @author Philip Wong
 * @since 09-12-17
 */
@Database(entities = [(CachedCourse::class)], version = 1)
abstract class CourseDatabase : RoomDatabase() {

    abstract fun cachedCourseDao(): CachedCourseDao

    private var INSTANCE: CourseDatabase? = null

    private val sLock = Any()

    fun getInstance(context: Context): CourseDatabase {
        if (INSTANCE == null) {
            synchronized(sLock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            CourseDatabase::class.java, "courses.db")
                            .build()
                }
                return INSTANCE!!
            }
        }
        return INSTANCE!!
    }


}



