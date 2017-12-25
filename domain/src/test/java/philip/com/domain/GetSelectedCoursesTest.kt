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
import philip.com.domain.interactor.course.GetSelectedCourses
import philip.com.domain.model.Course
import philip.com.domain.repository.CourseRepository

class GetSelectedCoursesTest {

    private lateinit var getSelectedCourses: GetSelectedCourses

    private lateinit var mockThreadExecutor: ThreadExecutor
    private lateinit var mockPostExecutionThread: PostExecutionThread
    private lateinit var mockCourseRepository: CourseRepository

    @Before
    fun setUp() {
        mockThreadExecutor = mock()
        mockPostExecutionThread = mock()
        mockCourseRepository = mock()
        getSelectedCourses = GetSelectedCourses(mockCourseRepository, mockThreadExecutor,
                mockPostExecutionThread)
    }

    @Test
    fun buildUseCaseObservableCallsRepository() {
        getSelectedCourses.buildUseCaseObservable("1085328")
        verify(mockCourseRepository).getSelectedCourses("1085328")
    }

    @Test
    fun buildUseCaseObservableCompletes() {
        stubCourseRepositoryGetCourses(Flowable.just(CourseFactory.makeCourseList(2)))
        val testObserver = getSelectedCourses.buildUseCaseObservable("1085328").test()
        testObserver.assertComplete()
    }

    @Test
    fun buildUseCaseObservableReturnsData() {
        val courses = CourseFactory.makeCourseList(2)
        stubCourseRepositoryGetCourses(Flowable.just(courses))
        val testObserver = getSelectedCourses.buildUseCaseObservable("1085328").test()
        testObserver.assertValue(courses)
    }

    private fun stubCourseRepositoryGetCourses(single: Flowable<List<Course>>) {
        whenever(mockCourseRepository.getSelectedCourses("1085328"))
                .thenReturn(single)
    }

}