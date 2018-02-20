package com.philip.presentation.section

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.philip.presentation.mapper.SectionMapper
import philip.com.domain.interactor.sections.SelectSection

open class SelectSectionsViewModelFactory(
        private val selectSections: SelectSection,
        private val sectionMapper: SectionMapper) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SelectSectionViewModel::class.java)) {
            return SelectSectionViewModel(selectSections, sectionMapper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}