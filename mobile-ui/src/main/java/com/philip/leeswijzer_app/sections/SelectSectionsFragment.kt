package com.philip.leeswijzer_app.sections

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.philip.leeswijzer_app.R
import com.philip.presentation.model.SectionView

/**
 * @author Philip Wong
 * @since 01-12-17
 **/
class SelectSectionsFragment : Fragment() {

    private lateinit var coursesRecyclerViewAdapter: SelectSectionRecyclerViewAdapter

    companion object {
        val TAG = "selectSectionFragment"

        fun newInstance(courseName: String): SelectSectionsFragment {

            val selectSectionsFragment = SelectSectionsFragment()
            val bundle = Bundle()
            bundle.putString("course_name", courseName)
            selectSectionsFragment.arguments = bundle

            return selectSectionsFragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.fragment_select_sections, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.activity.title = this.arguments.getString("course_name")
        this.setupCoursesRecyclerView(view!!)
    }

    private fun setupCoursesRecyclerView(parent: View) {

        val coursesRecyclerView = parent.findViewById<RecyclerView>(R.id.sections_recycler_view)
        coursesRecyclerViewAdapter = SelectSectionRecyclerViewAdapter(this.activity)
        coursesRecyclerView.adapter = coursesRecyclerViewAdapter
        coursesRecyclerView.layoutManager = LinearLayoutManager(this.activity)

        val list = ArrayList<SectionView>()
        for (i in 1..10) {
            list.add(SectionView(12123L, "Polymorfisme", false, "IOPR1"))
        }

        coursesRecyclerViewAdapter.addAll(list)
    }


}