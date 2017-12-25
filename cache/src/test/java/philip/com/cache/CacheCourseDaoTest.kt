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
import philip.com.cache.factory.CourseFactory

@RunWith(RobolectricTestRunner::class)
open class CacheCourseDaoTest {

    private lateinit var coursesCacheDatabase: CacheDatabase

    @Before
    fun initDb() {
        coursesCacheDatabase = Room.inMemoryDatabaseBuilder(
                RuntimeEnvironment.application.baseContext,
                CacheDatabase::class.java)
                .allowMainThreadQueries()
                .build()
    }

    @After
    fun closeDb() {
        coursesCacheDatabase.close()
    }

    @Test
    fun insertCoursesSavesData() {
        val cachedCourse = CourseFactory.makeCachedCourse()
        coursesCacheDatabase.cachedCourseDao().insertCourse(cachedCourse)

        val students = coursesCacheDatabase.cachedCourseDao().loadSelectedCourses()
        assertThat(students.isNotEmpty(), `is`(true))
    }

    @Test
    fun getCourseRetrievesData() {
        val cachedCourses = CourseFactory.makeCachedCourseList(5)

        cachedCourses.forEach {
            coursesCacheDatabase.cachedCourseDao().insertCourse(it)
        }

        val retrievedCourses = coursesCacheDatabase.cachedCourseDao().loadSelectedCourses()
        assertThat(retrievedCourses, `is`(cachedCourses))
    }

    @Test
    fun removeCourseDeletesData() {
        val cachedCourse = CourseFactory.makeCachedCourse()
        coursesCacheDatabase.cachedCourseDao().insertCourse(cachedCourse)
        coursesCacheDatabase.cachedCourseDao().clearCourses()
        assertThat(coursesCacheDatabase.cachedCourseDao().loadSelectedCourses().isEmpty(), `is`(true))
    }
}