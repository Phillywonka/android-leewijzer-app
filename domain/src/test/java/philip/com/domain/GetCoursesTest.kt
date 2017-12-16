package philip.com.domain

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import org.buffer.android.boilerplate.domain.executor.PostExecutionThread
import org.buffer.android.boilerplate.domain.interactor.browse.GetCourses
import org.buffer.android.boilerplate.domain.model.Course
import org.buffer.android.boilerplate.domain.repository.CourseRepository
import org.buffer.android.boilerplate.domain.test.factory.CourseFactory
import org.junit.Before
import org.junit.Test
import philip.com.domain.executor.ThreadExecutor

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