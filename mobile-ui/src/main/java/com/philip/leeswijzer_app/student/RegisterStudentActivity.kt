package com.philip.leeswijzer_app.student

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.philip.leeswijzer_app.R
import com.philip.leeswijzer_app.UiThread
import com.philip.presentation.data.Resource
import com.philip.presentation.data.ResourceState
import com.philip.presentation.student.RegisterViewModel
import com.philip.presentation.student.RegisterViewModelFactory
import philip.com.data.StudentDataRepository
import philip.com.data.executor.JobExecutor
import philip.com.data.mapper.StudentMapper
import philip.com.data.source.student.StudentDataStoreFactory
import philip.com.data.source.student.StudentRemoteDataStore
import philip.com.domain.interactor.authentication.Register
import philip.com.remote.StudentRemoteImpl
import philip.com.remote.StudentServiceFactory

/**
 * @author Philip Wong
 * @since 05-04-18
 **/
open class RegisterStudentActivity : AppCompatActivity() {

    companion object {
        const val TAG = "registerStudentActivity"
    }

    private lateinit var registerViewModelFactory: RegisterViewModelFactory
    private lateinit var registerViewModel: RegisterViewModel

    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var studentNumberNameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var passwordVerificationEditText: EditText
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_register_student)

        this.initViewModels()

        this.setupInputFields()

        this.registerButton = findViewById(R.id.register_button)
        this.registerButton.setOnClickListener(onRegisterButtonClickListener)
    }

    private fun initViewModels() {
        registerViewModelFactory = RegisterViewModelFactory(Register(StudentDataRepository(
                StudentDataStoreFactory(null, null, StudentRemoteDataStore(StudentRemoteImpl(
                        StudentServiceFactory.makeStudentService(true),
                        philip.com.remote.mapper.StudentEntityMapper()
                ))), StudentMapper()),
                JobExecutor(), UiThread()))


        registerViewModel = ViewModelProviders.of(this, registerViewModelFactory)
                .get(RegisterViewModel::class.java)

    }

    private fun setupInputFields() {
        this.firstNameEditText = findViewById(R.id.first_name_edit_text)
        this.firstNameEditText.onFocusChangeListener = this.onFocusChangeListener
        this.lastNameEditText = findViewById(R.id.last_name_edit_text)
        this.lastNameEditText.onFocusChangeListener = this.onFocusChangeListener
        this.studentNumberNameEditText = findViewById(R.id.student_number_edit_text)
        this.studentNumberNameEditText.onFocusChangeListener = this.onFocusChangeListener
        this.passwordEditText = findViewById(R.id.password_edit_text)
        this.passwordEditText.onFocusChangeListener = this.onFocusChangeListener
        this.passwordVerificationEditText = findViewById(R.id.password_verification_edit_text)
        this.passwordVerificationEditText.onFocusChangeListener = this.onFocusChangeListener
    }

    private fun handleDataState(resourceState: ResourceState, message: String?) {
        when (resourceState) {
            ResourceState.LOADING -> Log.d("Application", "SelectSectionFragment: handleDataState: loading")
            ResourceState.SUCCESS -> {
                this.setResult(android.app.Activity.RESULT_OK)
                this.finish()
            }
            ResourceState.ERROR -> Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
        }
    }

    private val onRegisterButtonClickListener = View.OnClickListener {
        if (this.validate()) {
            this.registerViewModel.register(
                    this.firstNameEditText.text.toString(),
                    this.lastNameEditText.text.toString(),
                    this.studentNumberNameEditText.text.toString(),
                    this.passwordEditText.text.toString()
            ).observe(this,
                    Observer<Resource<Void>> {
                        if (it != null) this.handleDataState(it.status, it.message)
                    })
        } else {
            Toast.makeText(applicationContext, "Account aanmaken mislukt", Toast.LENGTH_LONG).show()
        }
    }

    private val onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
        if (!hasFocus) {
            (view as EditText).error = null
        }
    }

    private fun validate(): Boolean {

        val emptyFieldWarningText = "Veld mag niet leeg zijn"
        val invalidPasswordWarningText = "Wachtwoorden komen niet overeen"

        return when {
            this.firstNameEditText.text.isBlank() -> {
                this.firstNameEditText.error = emptyFieldWarningText
                false
            }
            this.lastNameEditText.text.isBlank() -> {
                this.lastNameEditText.error = emptyFieldWarningText
                false
            }
            this.studentNumberNameEditText.text.isBlank() -> {
                this.studentNumberNameEditText.error = emptyFieldWarningText
                false
            }
            this.passwordEditText.text.isBlank() -> {
                this.passwordEditText.error = emptyFieldWarningText
                false
            }
            this.passwordVerificationEditText.text.isBlank() -> {
                this.passwordVerificationEditText.error = emptyFieldWarningText
                false
            }
            this.passwordEditText.text.toString() != passwordVerificationEditText.text.toString() -> {
                this.passwordEditText.error = invalidPasswordWarningText
                false
            }
            else -> true
        }
    }
}
