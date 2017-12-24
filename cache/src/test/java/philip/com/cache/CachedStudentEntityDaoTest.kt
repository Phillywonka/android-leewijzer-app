package philip.com.cache

import android.arch.persistence.room.Room
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import philip.com.cache.database.CacheDatabase
import philip.com.cache.factory.StudentFactory

@RunWith(RobolectricTestRunner::class)
open class CachedStudentEntityDaoTest {

    private lateinit var cacheDatabase: CacheDatabase

    @Before
    fun initDb() {
        cacheDatabase = Room.inMemoryDatabaseBuilder(
                RuntimeEnvironment.application.baseContext,
                CacheDatabase::class.java)
                .allowMainThreadQueries()
                .build()
    }

    @After
    fun closeDb() {
        cacheDatabase.close()
    }

    @Test
    fun insertStudentsSavesData() {
        val cachedStudent = StudentFactory.makeCachedStudent()
        cacheDatabase.cachedStudentDao().insertStudent(cachedStudent)

        val students = cacheDatabase.cachedStudentDao().loadAllStudents()
        assertThat(students.isNotEmpty(), `is`(true))
    }

    @Test
    fun getStudentRetrievesData() {
        val cachedStudents = StudentFactory.makeCachedStudentList(5)

        cachedStudents.forEach {
            cacheDatabase.cachedStudentDao().insertStudent(it)
        }

        val retrievedStudents = cacheDatabase.cachedStudentDao().loadAllStudents()
        assertThat(retrievedStudents, `is`(cachedStudents))
    }

    @Test
    fun removeStudentDeletesData() {
        val cachedStudent = StudentFactory.makeCachedStudent()
        cacheDatabase.cachedStudentDao().insertStudent(cachedStudent)
        cacheDatabase.cachedStudentDao().clearStudents()
        assertThat(cacheDatabase.cachedStudentDao().loadAllStudents().isEmpty(), `is`(true))
    }
}