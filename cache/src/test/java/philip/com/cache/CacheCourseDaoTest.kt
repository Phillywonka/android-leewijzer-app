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
import philip.com.cache.database.CourseDatabase
import philip.com.cache.factory.CourseFactory

@RunWith(RobolectricTestRunner::class)
open class CacheCourseDaoTest {

    private lateinit var coursesDatabase: CourseDatabase

    @Before
    fun initDb() {
        coursesDatabase = Room.inMemoryDatabaseBuilder(
                RuntimeEnvironment.application.baseContext,
                CourseDatabase::class.java)
                .allowMainThreadQueries()
                .build()
    }

    @After
    fun closeDb() {
        coursesDatabase.close()
    }

    @Test
    fun insertCoursesSavesData() {
        val cachedCourse = CourseFactory.makeCachedCourse()
        coursesDatabase.cachedCourseDao().insertCourse(cachedCourse)

        val students = coursesDatabase.cachedCourseDao().loadAllCourses()
        assertThat(students.isNotEmpty(), `is`(true))
    }

    @Test
    fun getCourseRetrievesData() {
        val cachedCourses = CourseFactory.makeCachedCourseList(5)

        cachedCourses.forEach {
            coursesDatabase.cachedCourseDao().insertCourse(it)
        }

        val retrievedCourses = coursesDatabase.cachedCourseDao().loadAllCourses()
        assertThat(retrievedCourses, `is`(cachedCourses))
    }

    @Test
    fun removeCourseDeletesData() {
        val cachedCourse = CourseFactory.makeCachedCourse()
        coursesDatabase.cachedCourseDao().insertCourse(cachedCourse)
        coursesDatabase.cachedCourseDao().clearCourses()
        assertThat(coursesDatabase.cachedCourseDao().loadAllCourses().isEmpty(), `is`(true))
    }
}