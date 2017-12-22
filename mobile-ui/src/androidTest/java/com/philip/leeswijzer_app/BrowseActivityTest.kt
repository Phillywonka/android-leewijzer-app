package com.philip.leeswijzer_app

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.philip.leeswijzer_app.courses.CoursesActivity
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import philip.com.domain.model.Course
import philip.com.domain.repository.CourseRepository


@RunWith(AndroidJUnit4::class)
class BrowseActivityTest {

    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule<CoursesActivity>(CoursesActivity::class.java, false, false)

    @Before
    fun init() {
        activityTestRule.activity.supportFragmentManager.beginTransaction()
    }

    @Test
    fun activityLaunches() {
        stubCourseRepositoryGetCourses(Flowable.just(CourseFactory.makeCourseList(2)))
        activityTestRule.launchActivity(null)
    }

    @Test
    fun coursesDisplay() {
        val courses = CourseFactory.makeCourseList(1)
        stubCourseRepositoryGetCourses(Flowable.just(courses))
        activityTestRule.launchActivity(null)

        checkCourseDetailsDisplay(courses[0], 0)
    }

    @Test
    fun coursesAreScrollable() {
        val courses = CourseFactory.makeCourseList(20)
        stubCourseRepositoryGetCourses(Flowable.just(courses))
        activityTestRule.launchActivity(null)

        courses.forEachIndexed { index, course ->
            onView(withId(R.id.courses_recycler_view)).perform(RecyclerViewActions.
                    scrollToPosition<RecyclerView.ViewHolder>(index))
            checkCourseDetailsDisplay(course, index)
        }
    }

    private fun checkCourseDetailsDisplay(course: Course, position: Int) {
        onView(RecyclerViewMatcher.withRecyclerView(R.id.courses_recycler_view).atPosition(position))
                .check(matches(hasDescendant(withText(course.name))))
        onView(RecyclerViewMatcher.withRecyclerView(R.id.courses_recycler_view).atPosition(position))
                .check(matches(hasDescendant(withText(course.name))))
    }

    private fun stubCourseRepositoryGetCourses(single: Flowable<List<Course>>) {
        val mockCourseRepository: CourseRepository = mock()
        whenever(mockCourseRepository.getCourses())
                .thenReturn(single)
    }

}