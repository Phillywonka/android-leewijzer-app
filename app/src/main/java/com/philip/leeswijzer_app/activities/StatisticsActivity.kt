package com.philip.leeswijzer_app.activities

import android.os.Bundle
import com.philip.leeswijzer_app.R
import com.philip.leeswijzer_app.fragments.StatisticsOverViewFragment

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

        this.fragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, statisticsOverViewFragment, StatisticsOverViewFragment.TAG)
                .commit();
    }
}
