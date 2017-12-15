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
import philip.com.cache.database.StudentDatabase
import philip.com.cache.factory.StudentFactory

@RunWith(RobolectricTestRunner::class)
open class CachedStudentDaoTest {

    private lateinit var coursesDatabase: StudentDatabase

    @Before
    fun initDb() {
        coursesDatabase = Room.inMemoryDatabaseBuilder(
                RuntimeEnvironment.application.baseContext,
                StudentDatabase::class.java)
                .allowMainThreadQueries()
                .build()
    }

    @After
    fun closeDb() {
        coursesDatabase.close()
    }

    @Test
    fun insertStudentsSavesData() {
        val cachedCourse = StudentFactory.makeCachedStudent()
        coursesDatabase.cachedStudentDao().insertStudent(cachedCourse)

        val students = coursesDatabase.cachedStudentDao().loadAllStudents()
        assertThat(students.isNotEmpty(), `is`(true))
    }

    @Test
    fun getStudentRetrievesData() {
        val cachedStudents = StudentFactory.makeCachedStudentList(5)

        cachedStudents.forEach {
            coursesDatabase.cachedStudentDao().insertStudent(it)
        }

        val retrievedCourses = coursesDatabase.cachedStudentDao().loadAllStudents()
        assertThat(retrievedCourses, `is`(cachedStudents))
    }

    @Test
    fun removeStudentDeletesData() {
        val cachedStudent = StudentFactory.makeCachedStudent()
        coursesDatabase.cachedStudentDao().insertStudent(cachedStudent)
        coursesDatabase.cachedStudentDao().clearStudents()
        assertThat(coursesDatabase.cachedStudentDao().loadAllStudents().isEmpty(), `is`(true))
    }
}