package philip.com.data.source

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import philip.com.data.repository.course.CourseCache
import philip.com.data.source.course.CourseCacheDataStore
import philip.com.data.source.course.CourseDataStoreFactory
import philip.com.data.source.course.CourseRemoteDataStore

@RunWith(JUnit4::class)
class CourseDataStoreFactoryTest {

    private lateinit var courseDataStoreFactory: CourseDataStoreFactory

    private lateinit var courseCache: CourseCache
    private lateinit var courseCacheDataStore: CourseCacheDataStore
    private lateinit var courseRemoteDataStore: CourseRemoteDataStore

    @Before
    fun setUp() {
        courseCache = mock()
        courseCacheDataStore = mock()
        courseRemoteDataStore = mock()
        courseDataStoreFactory = CourseDataStoreFactory(courseCache,
                courseCacheDataStore, courseRemoteDataStore)
    }

    //<editor-fold desc="Retrieve Data Store">
    @Test
    fun retrieveDataStoreWhenNotCachedReturnsRemoteDataStore() {
        stubCourseCacheIsCached(Single.just(false))
        val courseDataStore = courseDataStoreFactory.retrieveDataStore(false)
        assert(courseDataStore is CourseRemoteDataStore)
    }

    @Test
    fun retrieveDataStoreWhenCacheExpiredReturnsRemoteDataStore() {
        stubCourseCacheIsCached(Single.just(true))
        stubCourseCacheIsExpired(true)
        val courseDataStore = courseDataStoreFactory.retrieveDataStore(true)
        assert(courseDataStore is CourseRemoteDataStore)
    }

    @Test
    fun retrieveDataStoreReturnsCacheDataStore() {
        stubCourseCacheIsCached(Single.just(true))
        stubCourseCacheIsExpired(false)
        val courseDataStore = courseDataStoreFactory.retrieveDataStore(true)
        assert(courseDataStore is CourseCacheDataStore)
    }
    //</editor-fold>

    //<editor-fold desc="Retrieve Remote Data Store">
    @Test
    fun retrieveRemoteDataStoreReturnsRemoteDataStore() {
        val courseDataStore = courseDataStoreFactory.retrieveRemoteDataStore()
        assert(courseDataStore is CourseRemoteDataStore)
    }
    //</editor-fold>

    //<editor-fold desc="Retrieve Cache Data Store">
    @Test
    fun retrieveCacheDataStoreReturnsCacheDataStore() {
        val courseDataStore = courseDataStoreFactory.retrieveCacheDataStore()
        assert(courseDataStore is CourseCacheDataStore)
    }
    //</editor-fold>

    //<editor-fold desc="Stub helper methods">
    private fun stubCourseCacheIsCached(single: Single<Boolean>) {
        whenever(courseCache.isCached())
                .thenReturn(single)
    }

    private fun stubCourseCacheIsExpired(isExpired: Boolean) {
        whenever(courseCache.isExpired())
                .thenReturn(isExpired)
    }
    //</editor-fold>

}