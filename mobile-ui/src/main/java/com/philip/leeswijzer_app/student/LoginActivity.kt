package com.philip.leeswijzer_app.student

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.philip.leeswijzer_app.R

/**
 * @author Philip Wong
 * @since 09-03-18
 **/
class LoginActivity : Activity() {

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

    private val onLoginButtonClickListener = View.OnClickListener {
        this.askForLogin()
//        val intent = Intent(this.applicationContext, CoursesActivity::class.java)
//        this.startActivity(intent)
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

}