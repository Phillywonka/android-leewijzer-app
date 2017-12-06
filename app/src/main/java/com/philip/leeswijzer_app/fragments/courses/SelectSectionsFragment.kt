package com.philip.leeswijzer_app.fragments.courses

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.philip.leeswijzer_app.R
import com.philip.leeswijzer_app.adapters.SelectSectionRecyclerViewAdapter
import com.philip.leeswijzer_app.models.Course
import com.philip.leeswijzer_app.models.Section

/**
 * @author Philip Wong
 * @since 01-12-17
 **/
class SelectSectionsFragment : Fragment() {

    private lateinit var coursesRecyclerViewAdapter: SelectSectionRecyclerViewAdapter

    companion object {
        val TAG = "selectSectionFragment"
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.fragment_select_course, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.activity.title = "Mijn vakken"
        this.setupCoursesRecyclerView(view!!)
    }

    private fun setupCoursesRecyclerView(parent: View) {

        val coursesRecyclerView = parent.findViewById<RecyclerView>(R.id.courses_recycler_view)
        coursesRecyclerViewAdapter = SelectSectionRecyclerViewAdapter(this.activity)
        coursesRecyclerView.adapter = coursesRecyclerViewAdapter
        coursesRecyclerView.layoutManager = LinearLayoutManager(this.activity)

        val sections = ArrayList<Section>()
        sections.add(Section("Polymorfism"))

        for (i in 1..10) {
            coursesRecyclerViewAdapter.add(Course("IOPR$i", "Software Engineering", sections))
        }
    }


}