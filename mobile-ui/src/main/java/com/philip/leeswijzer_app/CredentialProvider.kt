package com.philip.leeswijzer_app

import android.content.Context
import android.util.Log



/**
 * @author Philip Wong
 * @since 06-04-18
 **/
class CredentialProvider(context: Context) {

    private val credentialStore = context.getSharedPreferences("GLOBAL_CONF", Context.MODE_PRIVATE)!!

    private val STUDENT_NUMBER_KEY = "1"

    fun setStudentNumber(studentNumber: String) {
        Log.d("Application", "CredentialProvider: setStudentNumber: $studentNumber")
        val editor = credentialStore.edit()
        editor.putString(STUDENT_NUMBER_KEY, studentNumber)
        editor.apply()
    }

    fun getStudentNumber(): String {
        return credentialStore.getString(STUDENT_NUMBER_KEY, "")
    }

}