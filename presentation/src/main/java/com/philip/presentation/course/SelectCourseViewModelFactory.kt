package org.buffer.android.boilerplate.presentation.browse

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.philip.presentation.course.SelectCourseViewModel
import org.buffer.android.boilerplate.domain.interactor.browse.GetCourses
import org.buffer.android.boilerplate.presentation.mapper.CourseMapper

open class SelectCourseViewModelFactory(
        private val getCourses: GetCourses,
        private val courseMapper: CourseMapper) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SelectCourseViewModel::class.java)) {
            return SelectCourseViewModel(getCourses, courseMapper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}