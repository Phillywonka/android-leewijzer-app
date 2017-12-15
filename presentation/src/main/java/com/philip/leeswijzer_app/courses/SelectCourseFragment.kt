package com.philip.leeswijzer_app.courses

import android.app.Fragment
import android.app.FragmentTransaction
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.philip.leeswijzer_app.R
import com.philip.leeswijzer_app.sections.SelectSectionsFragment
import philip.com.data.models.Course
import philip.com.data.models.Section

/**
 * @author Philip Wong
 * @since 25-11-17
 **/
class SelectCourseFragment : Fragment() {

    private lateinit var coursesRecyclerViewAdapter: SelectCourseRecyclerViewAdapter

    companion object {
        val TAG = "selectCourseFragment"
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
        coursesRecyclerViewAdapter = SelectCourseRecyclerViewAdapter(this.activity.applicationContext)
        coursesRecyclerViewAdapter.setOnItemClickLister(View.OnClickListener {
            Log.d("Application", "SelectCourseFragment: setupCoursesRecyclerView: ")
            val selectSectionsFragment = SelectSectionsFragment()

            val fragmentTransaction = fragmentManager.beginTransaction()

            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.replace(R.id.fragment_container,
                    selectSectionsFragment,
                    SelectSectionsFragment.TAG)
            fragmentTransaction.addToBackStack(SelectSectionsFragment.TAG);
            fragmentTransaction.commit()
        })

        coursesRecyclerView.adapter = coursesRecyclerViewAdapter
        coursesRecyclerView.layoutManager = LinearLayoutManager(this.activity)


        val sections = ArrayList<Section>()
        sections.add(Section("Polymorfism"))

        for (i in 1..10) {
            coursesRecyclerViewAdapter.add(Course("IOPR$i", "Software Engineering", sections))
        }


    }
}