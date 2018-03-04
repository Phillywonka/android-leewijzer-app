package com.philip.leeswijzer_app.statistics

import android.os.Bundle
import com.philip.leeswijzer_app.BaseActivity
import com.philip.leeswijzer_app.R

/**
 * Activity for presenting the statistics of all the Course data.
 *
 * @author Philip Wong
 * @since 24-11-17
 **/
class StatisticsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val statisticsOverViewFragment = StatisticsOverViewFragment()

        this.supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, statisticsOverViewFragment, StatisticsOverViewFragment.TAG)
                .commit();
    }
}

