package philip.com.remote

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import philip.com.data.models.CourseEntity
import philip.com.remote.mapper.CourseEntityMapper
import philip.com.remote.test.factory.CourseFactory

@RunWith(JUnit4::class)
class CourseRemoteImplTest {

    private lateinit var entityMapper: CourseEntityMapper
    private lateinit var courseService: CourseService

    private lateinit var courseRemoteImpl: CourseRemoteImpl

    @Before
    fun setup() {
        entityMapper = mock()
        courseService = mock()
        courseRemoteImpl = CourseRemoteImpl(courseService, entityMapper)
    }

    //<editor-fold desc="Get Courses">
    @Test
    fun getCoursesCompletes() {
        stubCourseServiceGetCourses(Flowable.just(CourseFactory.makeCourseResponse()))
        val testObserver = courseRemoteImpl.getSelectedCourses().test()
        testObserver.assertComplete()
    }

    @Test
    fun getCoursesReturnsData() {
        val courseResponse = CourseFactory.makeCourseResponse()
        stubCourseServiceGetCourses(Flowable.just(courseResponse))
        val courseEntities = mutableListOf<CourseEntity>()
        courseResponse.courses.forEach {
            courseEntities.add(entityMapper.mapFromRemote(it))
        }

        val testObserver = courseRemoteImpl.getSelectedCourses().test()
        testObserver.assertValue(courseEntities)
    }
    //</editor-fold>

    private fun stubCourseServiceGetCourses(observable:
                                                Flowable<CourseService.CourseResponse>) {
        whenever(courseService.getSelectedCourses())
                .thenReturn(observable)
    }
}