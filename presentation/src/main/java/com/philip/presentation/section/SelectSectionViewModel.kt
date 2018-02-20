package com.philip.presentation.section

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.philip.presentation.data.Resource
import com.philip.presentation.data.ResourceState
import com.philip.presentation.mapper.SectionMapper
import com.philip.presentation.model.SectionView
import io.reactivex.subscribers.DisposableSubscriber
import philip.com.domain.interactor.sections.SelectSection

open class SelectSectionViewModel(
        private val selectSection: SelectSection,
        private val sectionMapper: SectionMapper) : ViewModel() {

    private val selectSectionLiveData: MutableLiveData<Resource<Int>> = MutableLiveData()

    fun selectSection(sectionView: SectionView): LiveData<Resource<Int>> {
        executeSelectSectionCompletable(sectionView)
        return selectSectionLiveData
    }

    private fun executeSelectSectionCompletable(sectionView: SectionView) {
        selectSectionLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        val map = HashMap<String, Any>()
        map.put("student_number", "1085328")
        map.put("course_name", sectionView.courseName)
        map.put("section_id", sectionView.id)
        map.put("selected", sectionView.isChecked)

        selectSection.execute(SelectSectionSubscriber(), map)
    }

    inner class SelectSectionSubscriber : DisposableSubscriber<Int>() {

        override fun onComplete() {}

        override fun onNext(sectionId: Int) {
            selectSectionLiveData.postValue(Resource(ResourceState.SUCCESS,
                    sectionId, null))
        }

        override fun onError(exception: Throwable) {
            selectSectionLiveData.postValue(Resource(ResourceState.ERROR, null, exception.message))
        }
    }

}
