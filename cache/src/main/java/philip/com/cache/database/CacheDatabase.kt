package philip.com.cache.database

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.migration.Migration
import android.content.Context
import com.philip.leeswijzer_app.storage.cache.CachedStudentDao
import philip.com.cache.dao.CachedCourseDao
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

    private var INSTANCE: philip.com.cache.database.CacheDatabase? = null
    val MIGRATION_1_2: Migration = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // Room uses an own database hash to uniquely identify the database
            // Since version 1 does not use Room, it doesn't have the database hash associated.
            // By implementing a Migration class, we're telling Room that it should use the data
            // from version 1 to version 2.
            // If no migration is provided, then the tables will be dropped and recreated.
            // Since we didn't alter the table, there's nothing else to do here.
        }
    }
    private val sLock = Any()

    fun getInstance(context: Context): philip.com.cache.database.CacheDatabase {
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



