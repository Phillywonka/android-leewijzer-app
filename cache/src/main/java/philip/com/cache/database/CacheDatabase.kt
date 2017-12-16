package philip.com.cache.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.philip.leeswijzer_app.storage.cache.CachedCourseDao
import com.philip.leeswijzer_app.storage.cache.CachedStudentDao
import philip.com.cache.model.CachedCourse
import philip.com.cache.model.CachedStudent

/**
 * @author Philip Wong
 * @since 09-12-17
 */
@Database(entities = [CachedCourse::class, CachedStudent::class], version = 1)
abstract class CacheDatabase : RoomDatabase() {

    abstract fun cachedCourseDao(): CachedCourseDao
    abstract fun cachedStudentDao(): CachedStudentDao

    private var INSTANCE: philip.com.cache.database.CacheDatabase? = null

    private val sLock = Any()

    fun getInstance(context: Context): philip.com.cache.database.CacheDatabase {
        if (INSTANCE == null) {
            synchronized(sLock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            CacheDatabase::class.java, "courses.db")
                            .build()
                }
                return INSTANCE!!
            }
        }
        return INSTANCE!!
    }


}



