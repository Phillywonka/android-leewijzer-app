package com.philip.presentation.section

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.philip.presentation.data.Resource
import com.philip.presentation.data.ResourceState
import com.philip.presentation.mapper.SectionMapper
import com.philip.presentation.model.SectionView
import io.reactivex.subscribers.DisposableSubscriber
import philip.com.domain.interactor.sections.GetSections
import philip.com.domain.model.Section

open class GetSectionsViewModel(
        private val getSections: GetSections,
        private val sectionMapper: SectionMapper) : ViewModel() {

    private val sectionsLiveData: MutableLiveData<Resource<List<SectionView>>> =
            MutableLiveData()

    override fun onCleared() {
        getSections.dispose()
        super.onCleared()
    }

    fun getSelectedSections(studentNumber: String, courseName: String): LiveData<Resource<List<SectionView>>> {
        this.fetchSection(studentNumber, courseName)
        return sectionsLiveData
    }

    private fun fetchSection(studentNumber: String, courseName: String) {
        sectionsLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        val dataBundle = HashMap<String, String>()
        dataBundle.put("student_number", studentNumber)
        dataBundle.put("course_name", courseName)
        return getSections.execute(SectionSubscriber(), dataBundle)
    }

    inner class SectionSubscriber : DisposableSubscriber<List<Section>>() {

        override fun onComplete() {}

        override fun onNext(t: List<Section>) {
            sectionsLiveData.postValue(Resource(ResourceState.SUCCESS,
                    t.map { sectionMapper.mapToView(it) }, null))
        }

        override fun onError(exception: Throwable) {
            sectionsLiveData.postValue(Resource(ResourceState.ERROR, null, exception.message))
        }

    }

}