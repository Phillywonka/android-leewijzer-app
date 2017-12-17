package com.philip.presentation.course

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.philip.presentation.mapper.CourseMapper
import philip.com.domain.interactor.browse.GetCourses

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