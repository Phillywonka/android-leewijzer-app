package philip.com.cache.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.philip.leeswijzer_app.storage.cache.CachedStudentDao
import philip.com.cache.model.CachedStudent

/**
 * @author Philip Wong
 * @since 09-12-17
 */
@Database(entities = [(CachedStudent::class)], version = 1)
abstract class StudentDatabase: RoomDatabase() {

    abstract fun cachedStudentDao(): CachedStudentDao

    private var INSTANCE: StudentDatabase? = null

    private val sLock = Any()

    fun getInstance(context: Context): StudentDatabase {
        if (INSTANCE == null) {
            synchronized(sLock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            StudentDatabase::class.java, "students.db")
                            .build()
                }
                return INSTANCE!!
            }
        }
        return INSTANCE!!
    }


}



