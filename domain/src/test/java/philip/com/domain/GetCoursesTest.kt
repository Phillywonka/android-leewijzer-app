package philip.com.domain

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import philip.com.domain.executor.PostExecutionThread
import philip.com.domain.executor.ThreadExecutor
import philip.com.domain.factory.CourseFactory
import philip.com.domain.interactor.browse.GetCourses
import philip.com.domain.model.Course
import philip.com.domain.repository.CourseRepository

class GetCoursesTest {

    private lateinit var getCourses: GetCourses

    private lateinit var mockThreadExecutor: ThreadExecutor
    private lateinit var mockPostExecutionThread: PostExecutionThread
    private lateinit var mockCourseRepository: CourseRepository

    @Before
    fun setUp() {
        mockThreadExecutor = mock()
        mockPostExecutionThread = mock()
        mockCourseRepository = mock()
        getCourses = GetCourses(mockCourseRepository, mockThreadExecutor,
                mockPostExecutionThread)
    }

    @Test
    fun buildUseCaseObservableCallsRepository() {
        getCourses.buildUseCaseObservable(null)
        verify(mockCourseRepository).getCourses()
    }

    @Test
    fun buildUseCaseObservableCompletes() {
        stubCourseRepositoryGetCourses(Flowable.just(CourseFactory.makeCourseList(2)))
        val testObserver = getCourses.buildUseCaseObservable(null).test()
        testObserver.assertComplete()
    }

    @Test
    fun buildUseCaseObservableReturnsData() {
        val courses = CourseFactory.makeCourseList(2)
        stubCourseRepositoryGetCourses(Flowable.just(courses))
        val testObserver = getCourses.buildUseCaseObservable(null).test()
        testObserver.assertValue(courses)
    }

    private fun stubCourseRepositoryGetCourses(single: Flowable<List<Course>>) {
        whenever(mockCourseRepository.getCourses())
                .thenReturn(single)
    }

}