package philip.com.data

import com.nhaarman.mockito_kotlin.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import philip.com.data.mapper.CourseMapper
import philip.com.data.models.CourseEntity
import philip.com.data.source.course.CourseCacheDataStore
import philip.com.data.source.course.CourseDataStoreFactory
import philip.com.data.source.course.CourseRemoteDataStore
import philip.com.data.test.factory.CourseFactory
import philip.com.domain.model.Course

@RunWith(JUnit4::class)
class CourseDataRepositoryTest {

    private lateinit var courseDataRepository: CourseDataRepository

    private lateinit var courseDataStoreFactory: CourseDataStoreFactory
    private lateinit var courseMapper: CourseMapper
    private lateinit var courseCacheDataStore: CourseCacheDataStore
    private lateinit var courseRemoteDataStore: CourseRemoteDataStore

    @Before
    fun setUp() {

        courseDataStoreFactory = mock()
        courseMapper = mock()
        courseCacheDataStore = mock()
        courseRemoteDataStore = mock()
        courseDataRepository = CourseDataRepository(courseDataStoreFactory, courseMapper)

        stubCourseDataStoreFactoryRetrieveCacheDataStore()
        stubCourseDataStoreFactoryRetrieveRemoteDataStore()
    }

    //<editor-fold desc="Clear Courses">
    @Test
    fun clearCoursesCompletes() {
        stubCourseCacheClearCourses(Completable.complete())
        val testObserver = courseDataRepository.clearCourses().test()
        testObserver.assertComplete()
    }

    @Test
    fun clearCoursesCallsCacheDataStore() {
        stubCourseCacheClearCourses(Completable.complete())
        courseDataRepository.clearCourses().test()
        verify(courseCacheDataStore).clearCourses()
    }

    @Test
    fun clearCoursesNeverCallsRemoteDataStore() {
        stubCourseCacheClearCourses(Completable.complete())
        courseDataRepository.clearCourses().test()
        verify(courseRemoteDataStore, never()).clearCourses()
    }
    //</editor-fold>

    //<editor-fold desc="Save Courses">
    @Test
    fun saveCoursesCompletes() {
        stubCourseCacheSaveCourses(Completable.complete())
        val testObserver = courseDataRepository.saveCourses(
                CourseFactory.makeCourseList(2)).test()
        testObserver.assertComplete()
    }

    @Test
    fun saveCoursesCallsCacheDataStore() {
        stubCourseCacheSaveCourses(Completable.complete())
        courseDataRepository.saveCourses(CourseFactory.makeCourseList(2)).test()
        verify(courseCacheDataStore).saveCourses(any())
    }

    @Test
    fun saveCoursesNeverCallsRemoteDataStore() {
        stubCourseCacheSaveCourses(Completable.complete())
        courseDataRepository.saveCourses(CourseFactory.makeCourseList(2)).test()
        verify(courseRemoteDataStore, never()).saveCourses(any())
    }
    //</editor-fold>

    //<editor-fold desc="Get Courses">
    @Test
    fun getCoursesCompletes() {
        stubCourseCacheDataStoreIsCached(Single.just(true))
        stubCourseDataStoreFactoryRetrieveDataStore(courseCacheDataStore)
        stubCourseCacheDataStoreGetCourses(Flowable.just(
                CourseFactory.makeCourseEntityList(2)))
        stubCourseCacheSaveCourses(Completable.complete())
        val testObserver = courseDataRepository.getSelectedCourses("1085328").test()
        testObserver.assertComplete()
    }

    @Test
    fun getCoursesReturnsData() {
        stubCourseCacheDataStoreIsCached(Single.just(true))
        stubCourseDataStoreFactoryRetrieveDataStore(courseCacheDataStore)
        stubCourseCacheSaveCourses(Completable.complete())
        val courses = CourseFactory.makeCourseList(2)
        val courseEntities = CourseFactory.makeCourseEntityList(2)
        courses.forEachIndexed { index, course ->
            stubCourseMapperMapFromEntity(courseEntities[index], course) }
        stubCourseCacheDataStoreGetCourses(Flowable.just(courseEntities))

        val testObserver = courseDataRepository.getSelectedCourses("1085328").test()
        testObserver.assertValue(courses)
    }

    @Test
    fun getCoursesSavesCoursesWhenFromCacheDataStore() {
        stubCourseDataStoreFactoryRetrieveDataStore(courseCacheDataStore)
        stubCourseCacheSaveCourses(Completable.complete())
        courseDataRepository.saveCourses(CourseFactory.makeCourseList(2)).test()
        verify(courseCacheDataStore).saveCourses(any())
    }

    @Test
    fun getCoursesNeverSavesCoursesWhenFromRemoteDataStore() {
        stubCourseDataStoreFactoryRetrieveDataStore(courseRemoteDataStore)
        stubCourseCacheSaveCourses(Completable.complete())
        courseDataRepository.saveCourses(CourseFactory.makeCourseList(2)).test()
        verify(courseRemoteDataStore, never()).saveCourses(any())
    }
    //</editor-fold>

    //<editor-fold desc="Stub helper methods">
    private fun stubCourseCacheSaveCourses(completable: Completable) {
        whenever(courseCacheDataStore.saveCourses(any()))
                .thenReturn(completable)
    }

    private fun stubCourseCacheDataStoreIsCached(single: Single<Boolean>) {
        whenever(courseCacheDataStore.isCached())
                .thenReturn(single)
    }

    private fun stubCourseCacheDataStoreGetCourses(single: Flowable<List<CourseEntity>>) {
        whenever(courseCacheDataStore.getSelectedCourses("1085328"))
                .thenReturn(single)
    }

    private fun stubCourseRemoteDataStoreGetCourses(single: Flowable<List<CourseEntity>>) {
        whenever(courseRemoteDataStore.getSelectedCourses("1085328"))
                .thenReturn(single)
    }

    private fun stubCourseCacheClearCourses(completable: Completable) {
        whenever(courseCacheDataStore.clearCourses())
                .thenReturn(completable)
    }

    private fun stubCourseDataStoreFactoryRetrieveCacheDataStore() {
        whenever(courseDataStoreFactory.retrieveCacheDataStore())
                .thenReturn(courseCacheDataStore)
    }

    private fun stubCourseDataStoreFactoryRetrieveRemoteDataStore() {
        whenever(courseDataStoreFactory.retrieveRemoteDataStore())
                .thenReturn(courseCacheDataStore)
    }

    private fun stubCourseDataStoreFactoryRetrieveDataStore(dataStore: philip.com.data.repository.course.CourseDataStore) {
        whenever(courseDataStoreFactory.retrieveDataStore(any()))
                .thenReturn(dataStore)
    }

    private fun stubCourseMapperMapFromEntity(courseEntity: CourseEntity,
                                                course: Course) {
        whenever(courseMapper.mapFromEntity(courseEntity))
                .thenReturn(course)
    }
    //</editor-fold>

}