package com.philip.leeswijzer_app.student

import android.arch.persistence.room.Room
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.philip.leeswijzer_app.R
import com.philip.presentation.data.ResourceState
import com.philip.presentation.model.SectionView
import com.philip.presentation.section.GetSectionsViewModel
import com.philip.presentation.section.GetSelectedSectionsViewModelFactory
import com.philip.presentation.section.SelectSectionViewModel
import com.philip.presentation.section.SelectSectionsViewModelFactory
import philip.com.cache.database.CacheDatabase

/**
 * @author Philip Wong
 * @since 05-04-18
 **/
open class RegisterStudentActivity : AppCompatActivity() {

    companion object {
        const val TAG = "registerStudentActivity"
    }

    private lateinit var getSelectedSectionsViewModelFactory: GetSelectedSectionsViewModelFactory
    private lateinit var selectSectionsViewModelFactory: SelectSectionsViewModelFactory
    private lateinit var getSectionsViewModel: GetSectionsViewModel
    private lateinit var selectSectionViewModel: SelectSectionViewModel

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

    private fun handleDataState(resourceState: ResourceState, data: List<SectionView>?,
                                message: String?) {
        when (resourceState) {
            ResourceState.LOADING -> Log.d("Application", "SelectSectionFragment: handleDataState: loading")
            ResourceState.SUCCESS -> {
//                Toast.makeText(context, "Sections loaded", Toast.LENGTH_LONG).show()
            }
            ResourceState.ERROR -> Log.d("Application", "SelectSectionFragment: handleDataState: ERROR: $message")
        }
    }

    private fun onRegisterSuccess() {

        this.setResult(android.app.Activity.RESULT_OK)
        this.finish()
    }

    private val onRegisterButtonClickListener = View.OnClickListener {
        if (this.validate()) {
            this.onRegisterSuccess()
        } else {
            Toast.makeText(applicationContext, "Account aanmaken mislukt", Toast.LENGTH_LONG).show()
        }
    }

    private val onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
        if (!hasFocus) {
            (view as EditText).error = null
        }
    }


    fun buildDataBase(): CacheDatabase {
        return Room.databaseBuilder(applicationContext,
                CacheDatabase::class.java, "leeswijzer.db")
                .build().getInstance(applicationContext)
    }


    private fun validate(): Boolean {

        val warningText = "Veld mag niet leeg zijn"

        return when {
            this.firstNameEditText.text.isBlank() -> {
                this.firstNameEditText.error = warningText
                false
            }
            this.lastNameEditText.text.isBlank() -> {
                this.lastNameEditText.error = warningText
                false
            }
            this.studentNumberNameEditText.text.isBlank() -> {
                this.studentNumberNameEditText.error = warningText
                false
            }
            this.passwordEditText.text.isBlank() -> {
                this.passwordEditText.error = warningText
                false
            }
            this.passwordVerificationEditText.text.isBlank() -> {
                this.passwordVerificationEditText.error = warningText
                false
            }
            else -> true
        }
    }
}
