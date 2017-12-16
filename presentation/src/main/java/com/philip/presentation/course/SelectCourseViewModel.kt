package com.philip.presentation.course

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.subscribers.DisposableSubscriber
import org.buffer.android.boilerplate.domain.interactor.browse.GetCourses
import org.buffer.android.boilerplate.domain.model.Course
import org.buffer.android.boilerplate.presentation.data.Resource
import org.buffer.android.boilerplate.presentation.data.ResourceState
import org.buffer.android.boilerplate.presentation.mapper.CourseMapper
import org.buffer.android.boilerplate.presentation.model.CourseView

open class SelectCourseViewModel internal constructor(
        private val getCourses: GetCourses,
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
        return getCourses.execute(CourseSubscriber())
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