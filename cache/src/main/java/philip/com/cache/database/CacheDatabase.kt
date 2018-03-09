package philip.com.cache.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.philip.leeswijzer_app.storage.cache.CachedStudentDao
import philip.com.cache.dao.CachedCourseDao
import philip.com.cache.dao.CachedSectionDao
import philip.com.cache.model.CachedCourse
import philip.com.cache.model.CachedSection
import philip.com.cache.model.CachedStudent

/**
 * @author Philip Wong
 * @since 09-12-17
 */
@Database(entities = [CachedCourse::class, CachedStudent::class, CachedSection::class], version = 2)
abstract class CacheDatabase : RoomDatabase() {

    abstract fun cachedCourseDao(): CachedCourseDao
    abstract fun cachedStudentDao(): CachedStudentDao
    abstract fun cachedSectionDao(): CachedSectionDao

    private var INSTANCE: CacheDatabase? = null

    private val sLock = Any()

    fun getInstance(context: Context): CacheDatabase {
        if (INSTANCE == null) {
            synchronized(sLock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            CacheDatabase::class.java, "leeswijzer.db")
                            .fallbackToDestructiveMigration()
                            .build()
                }
                return INSTANCE!!
            }
        }
        return INSTANCE!!
    }


}



