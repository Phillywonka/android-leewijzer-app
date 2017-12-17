package com.philip.presentation.course

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.*
import com.philip.presentation.test.factory.CourseFactory
import com.philip.presentation.test.factory.DataFactory
import io.reactivex.subscribers.DisposableSubscriber
import philip.com.domain.interactor.browse.GetCourses
import org.buffer.android.boilerplate.domain.model.Course
import org.buffer.android.boilerplate.presentation.data.ResourceState
import com.philip.presentation.mapper.CourseMapper
import com.philip.presentation.model.CourseView
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Captor
import org.mockito.Mock

@RunWith(JUnit4::class)
class SelectCourseViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock lateinit var getCourses: GetCourses
    @Mock lateinit var courseMapper: CourseMapper

    @Captor
    private lateinit var captor: KArgumentCaptor<DisposableSubscriber<List<Course>>>

    private lateinit var coursesViewModel: SelectCourseViewModel

    @Before
    fun setUp() {
        captor = argumentCaptor<DisposableSubscriber<List<Course>>>()
        getCourses = mock()
        courseMapper = mock()
        coursesViewModel = SelectCourseViewModel(getCourses, courseMapper)
    }

    @Test
    fun getCoursesExecutesUseCase() {
        coursesViewModel.getCourses()

        verify(getCourses, times(1)).execute(any(), anyOrNull())
    }

    //<editor-fold desc="Success">
    @Test
    fun getCoursesReturnsSuccess() {
        val list = CourseFactory.makeCourseList(2)
        val viewList = CourseFactory.makeCourseViewList(2)
        stubCourseMapperMapToView(viewList[0], list[0])
        stubCourseMapperMapToView(viewList[1], list[1])

        coursesViewModel.getCourses()

        verify(getCourses).execute(captor.capture(), eq(null))
        captor.firstValue.onNext(list)

        assertThat(coursesViewModel.getCourses().value?.status, `is`(ResourceState.SUCCESS))
    }

    @Test
    fun getCoursesReturnsDataOnSuccess() {
        val list = CourseFactory.makeCourseList(2)
        val viewList = CourseFactory.makeCourseViewList(2)

        stubCourseMapperMapToView(viewList[0], list[0])
        stubCourseMapperMapToView(viewList[1], list[1])

        coursesViewModel.getCourses()

        verify(getCourses).execute(captor.capture(), eq(null))
        captor.firstValue.onNext(list)

        assertThat(coursesViewModel.getCourses().value?.data, `is`(viewList))
    }

    @Test
    fun getCoursesReturnsNoMessageOnSuccess() {
        val list = CourseFactory.makeCourseList(2)
        val viewList = CourseFactory.makeCourseViewList(2)

        stubCourseMapperMapToView(viewList[0], list[0])
        stubCourseMapperMapToView(viewList[1], list[1])

        coursesViewModel.getCourses()

        verify(getCourses).execute(captor.capture(), eq(null))
        captor.firstValue.onNext(list)

        assertThat(coursesViewModel.getCourses().value?.message, `is`(nullValue()))
    }
    //</editor-fold>

    //<editor-fold desc="Error">
    @Test
    fun getCoursesReturnsError() {
        coursesViewModel.getCourses()

        verify(getCourses).execute(captor.capture(), eq(null))
        captor.firstValue.onError(RuntimeException())

        assertThat(coursesViewModel.getCourses().value?.status, `is`(ResourceState.ERROR))
    }

    @Test
    fun getCoursesFailsAndContainsNoData() {
        coursesViewModel.getCourses()

        verify(getCourses).execute(captor.capture(), eq(null))
        captor.firstValue.onError(RuntimeException())

        assertThat(coursesViewModel.getCourses().value?.data, `is`(nullValue()))
    }

    @Test
    fun getCoursesFailsAndContainsMessage() {
        val errorMessage = DataFactory.randomUuid()
        coursesViewModel.getCourses()

        verify(getCourses).execute(captor.capture(), eq(null))
        captor.firstValue.onError(RuntimeException(errorMessage))

        assertThat(coursesViewModel.getCourses().value?.message, `is`(errorMessage))
    }
    //</editor-fold>

    //<editor-fold desc="Loading">
    @Test
    fun getCoursesReturnsLoading() {
        coursesViewModel.getCourses()

        assertThat(coursesViewModel.getCourses().value?.status, `is`(ResourceState.LOADING))
    }

    @Test
    fun getCoursesContainsNoDataWhenLoading() {
        coursesViewModel.getCourses()

        assertThat(coursesViewModel.getCourses().value?.data, `is`(nullValue()))
    }

    @Test
    fun getCoursesContainsNoMessageWhenLoading() {
        coursesViewModel.getCourses()

        assertThat(coursesViewModel.getCourses().value?.data, `is`(nullValue()))
    }
    //</editor-fold>

    private fun stubCourseMapperMapToView(courseView: CourseView,
                                          course: Course) {
        whenever(courseMapper.mapToView(course))
                .thenReturn(courseView)
    }

}