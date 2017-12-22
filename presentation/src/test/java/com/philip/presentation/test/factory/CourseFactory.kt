package com.philip.presentation.test.factory

import com.philip.presentation.model.CourseView
import com.philip.presentation.test.factory.DataFactory.Factory.randomUuid
import philip.com.domain.model.Course

/**
 * Factory class for Course related instances
 */
class CourseFactory {

    companion object Factory {

        fun makeCourseList(count: Int): List<Course> {
            val courses = mutableListOf<Course>()
            repeat(count) {
                courses.add(makeCourseModel())
            }
            return courses
        }

        fun makeCourseModel(): Course {
            return Course(randomUuid(), randomUuid())
        }

        fun makeCourseViewList(count: Int): List<CourseView> {
            val courses = mutableListOf<CourseView>()
            repeat(count) {
                courses.add(makeCourseView())
            }
            return courses
        }

        fun makeCourseView(): CourseView {
            return CourseView(randomUuid(), randomUuid())
        }

    }

}