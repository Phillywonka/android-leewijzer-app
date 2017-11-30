package com.philip.leeswijzer_app.activities

import android.os.Bundle
import com.philip.leeswijzer_app.R
import com.philip.leeswijzer_app.fragments.SelectCourseFragment

/**
 * Activity for presenting the statistics of all the Course data.
 *
 * @author Philip Wong
 * @since 24-11-17
 **/
class StatisticsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val selectCourseFragment = SelectCourseFragment()

        this.fragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, selectCourseFragment, SelectCourseFragment.TAG)
                .commit();
    }

    override fun onDestroy() {
        super.onDestroy()
        this.fragmentManager.popBackStack()
    }
}
