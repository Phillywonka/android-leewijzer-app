package com.philip.presentation.student

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.philip.presentation.data.Resource
import com.philip.presentation.data.ResourceState
import philip.com.domain.interactor.authentication.Register

/**
 * @author Philip Wong
 * @since 09-03-18
 **/
open class RegisterViewModel(private val register: Register) : ViewModel() {

    private val registerLiveData: MutableLiveData<Resource<Void>> = MutableLiveData()

    fun register(
            firstName: String, lastName: String,
            studentNumber: String, password: String
    ): LiveData<Resource<Void>> {
        this.executeLoginCompletable(firstName, lastName, studentNumber, password)
        return registerLiveData
    }

    private fun executeLoginCompletable(firstName: String, lastName: String,
                                        studentNumber: String, password: String) {
        registerLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        val map = HashMap<String, String>()
        map["first_name"] = firstName
        map["last_name"] = lastName
        map["student_number"] = studentNumber
        map["password"] = password

        register.execute(map).subscribe({
            registerLiveData.postValue(Resource(ResourceState.SUCCESS, null,
                    "Succesvol geregistreerd"))
        }, { error ->
            registerLiveData.postValue(Resource(ResourceState.ERROR, null, error.toString()))
        })
    }

}
