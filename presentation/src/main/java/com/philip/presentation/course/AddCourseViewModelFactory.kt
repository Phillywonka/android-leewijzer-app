package com.philip.presentation.course

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import philip.com.domain.interactor.course.AddCourse

open class AddCourseViewModelFactory(
        private val addCourse: AddCourse) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddCourseViewModel::class.java)) {
            return AddCourseViewModel(addCourse) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}