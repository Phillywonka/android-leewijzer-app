package com.philip.presentation.course

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.*
import com.philip.presentation.data.ResourceState
import com.philip.presentation.mapper.CourseMapper
import com.philip.presentation.model.CourseView
import com.philip.presentation.test.factory.CourseFactory
import com.philip.presentation.test.factory.DataFactory
import io.reactivex.subscribers.DisposableSubscriber
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Captor
import org.mockito.Mock
import org.robolectric.RobolectricTestRunner
import philip.com.domain.interactor.course.GetSelectedCourses
import philip.com.domain.model.Course

@RunWith(RobolectricTestRunner::class)
class SelectCourseViewModelTest {

    @get:Rule var instanttaskexecutorrule = InstantTaskExecutorRule()

    @Mock lateinit var getSelectedCourses: GetSelectedCourses
    @Mock lateinit var courseMapper: CourseMapper

    @Captor
    private lateinit var captor: KArgumentCaptor<DisposableSubscriber<List<Course>>>

    private lateinit var coursesViewModel: SelectCourseViewModel

    @Before
    fun setUp() {
        captor = argumentCaptor<DisposableSubscriber<List<Course>>>()
        getSelectedCourses = mock()
        courseMapper = mock()
        coursesViewModel = SelectCourseViewModel(getSelectedCourses, courseMapper)
    }

    @Test
    fun getCoursesExecutesUseCase() {
        coursesViewModel.getSelectedCourses()

        verify(getSelectedCourses, times(1)).execute(any(), anyOrNull())
    }

    //<editor-fold desc="Success">
    @Test
    fun getCoursesReturnsSuccess() {
        val list = CourseFactory.makeCourseList(2)
        val viewList = CourseFactory.makeCourseViewList(2)
        stubCourseMapperMapToView(viewList[0], list[0])
        stubCourseMapperMapToView(viewList[1], list[1])

        coursesViewModel.getSelectedCourses()

        verify(getSelectedCourses).execute(captor.capture(), eq("1085328"))
        captor.firstValue.onNext(list)

        assertThat(coursesViewModel.getSelectedCourses().value?.status, `is`(ResourceState.SUCCESS))
    }

    @Test
    fun getCoursesReturnsDataOnSuccess() {
        val list = CourseFactory.makeCourseList(2)
        val viewList = CourseFactory.makeCourseViewList(2)

        stubCourseMapperMapToView(viewList[0], list[0])
        stubCourseMapperMapToView(viewList[1], list[1])

        coursesViewModel.getSelectedCourses()

        verify(getSelectedCourses).execute(captor.capture(), eq("1085328"))
        captor.firstValue.onNext(list)

        assert(coursesViewModel.getSelectedCourses().value?.data == viewList)
    }

    @Test
    fun getCoursesReturnsNoMessageOnSuccess() {
        val list = CourseFactory.makeCourseList(2)
        val viewList = CourseFactory.makeCourseViewList(2)

        stubCourseMapperMapToView(viewList[0], list[0])
        stubCourseMapperMapToView(viewList[1], list[1])

        coursesViewModel.getSelectedCourses()

        verify(getSelectedCourses).execute(captor.capture(), eq("1085328"))
        captor.firstValue.onNext(list)

        assert(coursesViewModel.getSelectedCourses().value?.message == null)
    }
    //</editor-fold>

    //<editor-fold desc="Error">
    @Test
    fun getCoursesReturnsError() {
        coursesViewModel.getSelectedCourses()

        verify(getSelectedCourses).execute(captor.capture(), eq("1085328"))
        captor.firstValue.onError(RuntimeException())

        assert(coursesViewModel.getSelectedCourses().value?.status == ResourceState.ERROR)
    }

    @Test
    fun getCoursesFailsAndContainsNoData() {
        coursesViewModel.getSelectedCourses()

        verify(getSelectedCourses).execute(captor.capture(), eq("1085328"))
        captor.firstValue.onError(RuntimeException())

        assert(coursesViewModel.getSelectedCourses().value?.data == null)
    }

    @Test
    fun getCoursesFailsAndContainsMessage() {
        val errorMessage = DataFactory.randomUuid()
        coursesViewModel.getSelectedCourses()

        verify(getSelectedCourses).execute(captor.capture(), eq("1085328"))
        captor.firstValue.onError(RuntimeException(errorMessage))

        assert(coursesViewModel.getSelectedCourses().value?.message == errorMessage)
    }
    //</editor-fold>

    //<editor-fold desc="Loading">
    @Test
    fun getCoursesReturnsLoading() {
        coursesViewModel.getSelectedCourses()

        assert(coursesViewModel.getSelectedCourses().value?.status == ResourceState.LOADING)
    }

    @Test
    fun getCoursesContainsNoDataWhenLoading() {
        coursesViewModel.getSelectedCourses()

        assert(coursesViewModel.getSelectedCourses().value?.data == null)
    }

    @Test
    fun getCoursesContainsNoMessageWhenLoading() {
        coursesViewModel.getSelectedCourses()

        assert(coursesViewModel.getSelectedCourses().value?.data == null)
    }
    //</editor-fold>

    private fun stubCourseMapperMapToView(courseView: CourseView,
                                          course: Course) {
        whenever(courseMapper.mapToView(course))
                .thenReturn(courseView)
    }

}