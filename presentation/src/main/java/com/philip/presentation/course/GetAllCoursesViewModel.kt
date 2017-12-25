package com.philip.presentation.course

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.philip.presentation.data.Resource
import com.philip.presentation.data.ResourceState
import com.philip.presentation.mapper.CourseMapper
import com.philip.presentation.model.CourseView
import io.reactivex.subscribers.DisposableSubscriber
import philip.com.domain.interactor.course.GetAllCourses
import philip.com.domain.model.Course

open class GetAllCoursesViewModel(
        private val getCourses: GetAllCourses,
        private val courseMapper: CourseMapper) : ViewModel() {

    private val coursesLiveData: MutableLiveData<Resource<List<CourseView>>> =
            MutableLiveData()

    init {
        fetchCourses()
    }

    override fun onCleared() {
        getCourses.dispose()
        super.onCleared()
    }

    fun getCourses(): LiveData<Resource<List<CourseView>>> {
        return coursesLiveData
    }

    fun fetchCourses() {
        coursesLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        return getCourses.execute(CourseSubscriber(), null)
    }

    inner class CourseSubscriber : DisposableSubscriber<List<Course>>() {

        override fun onComplete() {}

        override fun onNext(t: List<Course>) {
            coursesLiveData.postValue(Resource(ResourceState.SUCCESS,
                    t.map { courseMapper.mapToView(it) }, null))
        }

        override fun onError(exception: Throwable) {
            coursesLiveData.postValue(Resource(ResourceState.ERROR, null, exception.message))
        }

    }

}