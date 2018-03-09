package com.philip.presentation.student

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.philip.presentation.data.Resource
import com.philip.presentation.data.ResourceState
import philip.com.domain.interactor.authentication.Login

/**
 * @author Philip Wong
 * @since 09-03-18
 **/
open class LoginViewModel(private val login: Login) : ViewModel() {

    private val loginLiveData: MutableLiveData<Resource<Void>> = MutableLiveData()

    fun login(studentNumber: String, password: String): LiveData<Resource<Void>> {
        this.executeLoginCompletable(studentNumber, password)
        return loginLiveData
    }

    private fun executeLoginCompletable(studentNumber: String, password: String) {
        loginLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        val map = HashMap<String, String>()
        map["student_number"] = studentNumber
        map["password"] = password

        login.execute(map).subscribe({
            loginLiveData.postValue(Resource(ResourceState.SUCCESS, null,
                    "Succesvol ingelogd"))
        }, { error ->
            loginLiveData.postValue(Resource(ResourceState.ERROR, null, error.message))
        })
    }

}
