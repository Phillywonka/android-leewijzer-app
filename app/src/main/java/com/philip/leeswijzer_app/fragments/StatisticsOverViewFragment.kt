package com.philip.leeswijzer_app.fragments

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.philip.leeswijzer_app.R

/**
 * @author Philip Wong
 * @since 25-11-17
 **/
class StatisticsOverViewFragment : Fragment() {

    companion object {
        val TAG = "statisticsFragment"
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.fragment_statistics_overview, container, false)
    }
}