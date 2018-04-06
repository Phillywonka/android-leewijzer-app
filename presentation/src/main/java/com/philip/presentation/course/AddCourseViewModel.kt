package com.philip.presentation.course

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.philip.presentation.data.Resource
import com.philip.presentation.data.ResourceState
import com.philip.presentation.model.CourseView
import philip.com.domain.interactor.course.AddCourse

open class AddCourseViewModel(private val studentNumber: String, private val addCourse: AddCourse) : ViewModel() {

    private val addCourseLiveData: MutableLiveData<Resource<Void>> = MutableLiveData()

    fun addNewCourse(courseView: CourseView): LiveData<Resource<Void>> {
        executeAddCourseCompletable(courseView)
        return addCourseLiveData
    }

    private fun executeAddCourseCompletable(courseView: CourseView) {
        addCourseLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        val map = HashMap<String, String>()
        map["student_number"] = studentNumber
        map["course_name"] = courseView.name

        addCourse.execute(map).subscribe({
            addCourseLiveData.postValue(Resource(ResourceState.SUCCESS, null,
                    "Vak is toegevoegd"))
        }, { error ->
            addCourseLiveData.postValue(Resource(ResourceState.ERROR, null, error.message))
        })
    }

}