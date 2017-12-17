package com.philip.leeswijzer_app.courses

import android.app.FragmentTransaction
import android.arch.lifecycle.ViewModelProviders
import android.arch.persistence.room.Room
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.philip.leeswijzer_app.R
import com.philip.leeswijzer_app.sections.SelectSectionsFragment
import com.philip.presentation.course.SelectCourseViewModel
import philip.com.cache.PreferencesHelper
import philip.com.cache.mapper.CourseEntityMapper
import org.buffer.android.boilerplate.data.CourseDataRepository
import org.buffer.android.boilerplate.data.source.CourseCacheDataStore
import org.buffer.android.boilerplate.domain.interactor.browse.GetCourses
import org.buffer.android.boilerplate.presentation.browse.SelectCourseViewModelFactory
import com.philip.presentation.mapper.CourseMapper
import org.buffer.android.boilerplate.data.source.CourseRemoteDataStore
import org.buffer.android.boilerplate.presentation.model.CourseView
import philip.com.cache.CourseCacheImpl
import philip.com.cache.database.CacheDatabase
import philip.com.data.models.SectionEntity
import philip.com.data.source.CourseDataStoreFactory

/**
 * @author Philip Wong
 * @since 25-11-17
 **/
class SelectCourseFragment : Fragment() {

    private lateinit var coursesRecyclerViewAdapter: SelectCourseRecyclerViewAdapter
    lateinit var viewModelFactory: SelectCourseViewModelFactory
    private lateinit var selectCourseViewModel: SelectCourseViewModel

    companion object {
        val TAG = "selectCourseFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val courseCache = CourseCacheImpl(
                this.buildDataBase(),
                CourseEntityMapper(),
                PreferencesHelper(context))

        viewModelFactory = SelectCourseViewModelFactory(GetCourses(CourseDataRepository(
                CourseDataStoreFactory(courseCache, CourseCacheDataStore(
                        courseCache), CourseRemoteDataStore()), CourseMapper())))

        selectCourseViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(SelectCourseViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.fragment_select_course, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.activity.title = "Mijn vakken"
        this.setupCoursesRecyclerView(view)
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


        val sections = ArrayList<SectionEntity>()
        sections.add(SectionEntity(1L, "Polymorfism", false))

        for (i in 1..10) {
            coursesRecyclerViewAdapter.add(CourseView("IOPR$i", "Software Engineering"))
        }


    }

    fun buildDataBase(): CacheDatabase {
        return Room.databaseBuilder(this.context,
                CacheDatabase::class.java, "leeswijzer.db")
                .build()
    }
}