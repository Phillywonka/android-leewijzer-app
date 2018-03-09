package com.philip.presentation.student

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import philip.com.domain.interactor.authentication.Login

/**
 * @author Philip Wong
 * @since 09-03-18
 **/
open class LoginViewModelFactory(
        private val login: Login) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(login) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
