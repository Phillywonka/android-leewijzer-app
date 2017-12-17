package philip.com.data.source

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import philip.com.data.models.CourseEntity
import philip.com.data.repository.CourseRemote
import philip.com.data.test.factory.CourseFactory

@RunWith(JUnit4::class)
class CourseRemoteDataStoreTest {

    private lateinit var courseRemoteDataStore: CourseRemoteDataStore

    private lateinit var courseRemote: CourseRemote

    @Before
    fun setUp() {
        courseRemote = mock()
        courseRemoteDataStore = CourseRemoteDataStore(courseRemote)
    }

    //<editor-fold desc="Clear Courses">
    @Test(expected = UnsupportedOperationException::class)
    fun clearCoursesThrowsException() {
        courseRemoteDataStore.getCourses().test()
    }
    //</editor-fold>

    //<editor-fold desc="Save Courses">
    @Test(expected = UnsupportedOperationException::class)
    fun saveCoursesThrowsException() {
        courseRemoteDataStore.saveCourses(CourseFactory.makeCourseEntityList(2)).test()
    }
    //</editor-fold>

    //<editor-fold desc="Get Courses">
    @Test
    fun getCoursesCompletes() {
        stubCourseCacheGetCourses(Flowable.just(CourseFactory.makeCourseEntityList(2)))
        val testObserver = courseRemote.getCourses().test()
        testObserver.assertComplete()
    }
    //</editor-fold>

    //<editor-fold desc="Stub helper methods">
    private fun stubCourseCacheGetCourses(single: Flowable<List<CourseEntity>>) {
        whenever(courseRemote.getCourses())
                .thenReturn(single)
    }
    //</editor-fold>

}