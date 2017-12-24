package com.philip.leeswijzer_app.courses


import android.os.Bundle
import android.view.MenuItem
import com.philip.leeswijzer_app.BaseActivity
import com.philip.leeswijzer_app.R

/**
 * Activity for presenting Course related data.
 *
 * @author Philip Wong
 * @since 24-11-17
 **/
class CoursesActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addSelectCourseFragment()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                this.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (fragment !is SelectCourseFragment) {
            supportFragmentManager.popBackStack()
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            supportActionBar!!.title = SelectCourseFragment.TITLE
        }
    }

    private fun addSelectCourseFragment() {
        val selectCourseFragment = SelectCourseFragment()

        this.supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, selectCourseFragment, SelectCourseFragment.TAG)
                .commit()
    }
}
