package com.philip.presentation.course

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.philip.presentation.mapper.CourseMapper
import philip.com.domain.interactor.course.GetAllCourses

open class GetAllCoursesViewModelFactory(
        private val getAllCourses: GetAllCourses,
        private val courseMapper: CourseMapper) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GetAllCoursesViewModel::class.java)) {
            return GetAllCoursesViewModel(getAllCourses, courseMapper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}