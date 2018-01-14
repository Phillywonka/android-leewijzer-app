package com.philip.presentation.section

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.philip.presentation.mapper.SectionMapper
import philip.com.domain.interactor.sections.GetSections

open class GetSelectedSectionsViewModelFactory(
        private val getSections: GetSections,
        private val sectionMapper: SectionMapper) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GetSectionsViewModel::class.java)) {
            return GetSectionsViewModel(getSections, sectionMapper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}