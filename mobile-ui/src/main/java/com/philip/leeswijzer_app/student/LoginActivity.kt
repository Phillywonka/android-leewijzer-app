package com.philip.leeswijzer_app.student

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.persistence.room.Room
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.philip.leeswijzer_app.R
import com.philip.leeswijzer_app.UiThread
import com.philip.leeswijzer_app.courses.CoursesActivity
import com.philip.presentation.data.Resource
import com.philip.presentation.data.ResourceState
import com.philip.presentation.student.LoginViewModel
import com.philip.presentation.student.LoginViewModelFactory
import philip.com.cache.PreferencesHelper
import philip.com.cache.StudentCacheImpl
import philip.com.cache.database.CacheDatabase
import philip.com.data.StudentDataRepository
import philip.com.data.executor.JobExecutor
import philip.com.data.mapper.StudentMapper
import philip.com.data.source.student.StudentCacheDataStore
import philip.com.data.source.student.StudentDataStoreFactory
import philip.com.data.source.student.StudentRemoteDataStore
import philip.com.domain.interactor.authentication.Login
import philip.com.remote.StudentRemoteImpl
import philip.com.remote.StudentServiceFactory

/**
 * @author Philip Wong
 * @since 09-03-18
 **/
class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModelFactory: LoginViewModelFactory
    private lateinit var loginViewModel: LoginViewModel

    lateinit var studentNumberEditText: EditText
    lateinit var passwordEditText: EditText
    lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_login)

        this.setupLoginButton()
        this.setupStudentNumberEditText()
        this.setupPasswordEditText()
    }

    override fun onStart() {
        super.onStart()
        initViewModel()
    }

    private fun initViewModel() {
        val studentCache = StudentCacheImpl(
                this.buildDataBase(),
                philip.com.cache.mapper.StudentEntityMapper(),
                PreferencesHelper(this.applicationContext))

        loginViewModelFactory = LoginViewModelFactory(Login(StudentDataRepository(
                StudentDataStoreFactory(studentCache, StudentCacheDataStore(
                        studentCache), StudentRemoteDataStore(StudentRemoteImpl(
                        StudentServiceFactory.makeStudentService(true),
                        philip.com.remote.mapper.StudentEntityMapper()
                ))), StudentMapper()),
                JobExecutor(), UiThread()))


        loginViewModel = ViewModelProviders.of(this, loginViewModelFactory)
                .get(LoginViewModel::class.java)
    }

    private fun setupStudentNumberEditText() {
        this.studentNumberEditText = this.findViewById(R.id.student_number_edit_text)
        this.studentNumberEditText.addTextChangedListener(LoginFieldTextWatcher(studentNumberEditText))
    }

    private fun setupPasswordEditText() {
        this.passwordEditText = this.findViewById(R.id.password_edit_text)
    }

    private fun setupLoginButton() {
        this.loginButton = this.findViewById(R.id.login_button)
        this.loginButton.setOnClickListener(this.onLoginButtonClickListener)
    }

    private fun askForLogin() {
        if (this.studentNumberEditText.text.isEmpty() && this.studentNumberEditText.hasFocus()) {
            this.studentNumberEditText.error = "Dit veld is verplicht"
        } else if (this.passwordEditText.text.isEmpty() && this.passwordEditText.hasFocus()) {
            this.passwordEditText.error = "Dit veld is verplicht"
        }
    }

    private fun handleDataState(resourceState: ResourceState,
                                message: String?) {
        when (resourceState) {
            ResourceState.LOADING -> Log.d("Application", ": handleDataState: loading")

            ResourceState.SUCCESS -> {
                Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
                val intent = Intent(this.applicationContext, CoursesActivity::class.java)
                this.startActivity(intent)
            }

            ResourceState.ERROR -> Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
        }
    }


    private val onLoginButtonClickListener = View.OnClickListener {
        this.askForLogin()

        this.loginViewModel.login(studentNumberEditText.text.toString(),
                passwordEditText.text.toString()).observe(this,
                Observer<Resource<Void>> {
                    if (it != null) this.handleDataState(it.status, it.message)
                })
    }


    class LoginFieldTextWatcher(val field: EditText) : TextWatcher {

        override fun afterTextChanged(s: Editable) {
            if (s.isEmpty()) {
                field.error = "Veld mag niet leeg zijn"
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }

    private fun buildDataBase(): CacheDatabase {
        return Room.databaseBuilder(this.applicationContext,
                CacheDatabase::class.java, "leeswijzer.db")
                .build().getInstance(this.applicationContext)
    }


}