package philip.com.data.source

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import philip.com.data.models.CourseEntity
import philip.com.data.repository.CourseCache
import philip.com.data.test.factory.CourseFactory

@RunWith(JUnit4::class)
class CourseCacheDataStoreTest {

    private lateinit var courseCacheDataStore: CourseCacheDataStore

    private lateinit var courseCache: CourseCache

    @Before
    fun setUp() {
        courseCache = mock()
        courseCacheDataStore = CourseCacheDataStore(courseCache)
    }

    //<editor-fold desc="Clear Courses">
    @Test
    fun clearCoursesCompletes() {
        stubCourseCacheClearCourses(Completable.complete())
        val testObserver = courseCacheDataStore.clearCourses().test()
        testObserver.assertComplete()
    }
    //</editor-fold>

    //<editor-fold desc="Save Courses">
    @Test
    fun saveCoursesCompletes() {
        stubCourseCacheSaveCourses(Completable.complete())
        val testObserver = courseCacheDataStore.saveCourses(
                CourseFactory.makeCourseEntityList(2)).test()
        testObserver.assertComplete()
    }
    //</editor-fold>

    //<editor-fold desc="Get Courses">
    @Test
    fun getCoursesCompletes() {
        stubCourseCacheGetCourses(Flowable.just(CourseFactory.makeCourseEntityList(2)))
        val testObserver = courseCacheDataStore.getCourses().test()
        testObserver.assertComplete()
    }
    //</editor-fold>

    //<editor-fold desc="Stub helper methods">
    private fun stubCourseCacheSaveCourses(completable: Completable) {
        whenever(courseCache.saveCourses(any()))
                .thenReturn(completable)
    }

    private fun stubCourseCacheGetCourses(single: Flowable<List<CourseEntity>>) {
        whenever(courseCache.getCourses())
                .thenReturn(single)
    }

    private fun stubCourseCacheClearCourses(completable: Completable) {
        whenever(courseCache.clearCourses())
                .thenReturn(completable)
    }
    //</editor-fold>

}