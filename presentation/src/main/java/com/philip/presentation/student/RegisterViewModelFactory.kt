package com.philip.presentation.student

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import philip.com.domain.interactor.authentication.Register

/**
 * @author Philip Wong
 * @since 09-03-18
 **/
open class RegisterViewModelFactory(
        private val register: Register) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(register) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
