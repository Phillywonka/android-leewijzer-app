package com.philip.leeswijzer_app

import com.philip.presentation.model.CourseView
import philip.com.domain.model.Course

/**
 * Factory class for Course related instances
 */
object CourseFactory {

    fun makeCourseView(): CourseView {
        return CourseView(DataFactory.randomUuid(), DataFactory.randomUuid())
    }

    fun makeCourseList(count: Int): List<Course> {
        val courses = mutableListOf<Course>()
        repeat(count) {
            courses.add(makeCourseModel())
        }
        return courses
    }

    fun makeCourseModel(): Course {
        return Course(DataFactory.randomUuid(), DataFactory.randomUuid())
    }

}