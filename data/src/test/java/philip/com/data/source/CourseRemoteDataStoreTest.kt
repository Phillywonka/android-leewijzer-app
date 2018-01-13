package philip.com.data.source

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import philip.com.data.models.CourseEntity
import philip.com.data.repository.course.CourseRemote
import philip.com.data.source.course.CourseRemoteDataStore
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
        courseRemoteDataStore.clearCourses().test()
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
        val testObserver = courseRemote.getSelectedCourses("1085328").test()
        testObserver.assertComplete()
    }
    //</editor-fold>

    //<editor-fold desc="Stub helper methods">
    private fun stubCourseCacheGetCourses(single: Flowable<List<CourseEntity>>) {
        whenever(courseRemote.getSelectedCourses("1085328"))
                .thenReturn(single)
    }
    //</editor-fold>

}