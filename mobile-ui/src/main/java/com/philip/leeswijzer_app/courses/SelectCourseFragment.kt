package com.philip.leeswijzer_app.courses

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.persistence.room.Room
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.philip.leeswijzer_app.R
import com.philip.leeswijzer_app.UiThread
import com.philip.leeswijzer_app.courses.SelectCourseRecyclerViewAdapter.OnCourseViewItemClickListener
import com.philip.leeswijzer_app.sections.SelectSectionsFragment
import com.philip.presentation.course.SelectCourseViewModel
import com.philip.presentation.course.SelectCourseViewModelFactory
import com.philip.presentation.data.Resource
import com.philip.presentation.data.ResourceState
import com.philip.presentation.model.CourseView
import philip.com.cache.CourseCacheImpl
import philip.com.cache.PreferencesHelper
import philip.com.cache.database.CacheDatabase
import philip.com.cache.mapper.CourseEntityMapper
import philip.com.data.CourseDataRepository
import philip.com.data.executor.JobExecutor
import philip.com.data.mapper.CourseMapper
import philip.com.data.source.course.CourseCacheDataStore
import philip.com.data.source.course.CourseDataStoreFactory
import philip.com.data.source.course.CourseRemoteDataStore
import philip.com.remote.CourseRemoteImpl
import philip.com.remote.CourseServiceFactory

/**
 * @author Philip Wong
 * @since 25-11-17
 **/
class SelectCourseFragment : Fragment() {

    private lateinit var coursesRecyclerViewAdapter: SelectCourseRecyclerViewAdapter
    private lateinit var viewModelFactory: SelectCourseViewModelFactory
    private lateinit var selectCourseViewModel: SelectCourseViewModel
    private lateinit var addCourseButton: FloatingActionButton

    companion object {
        val TAG = "selectCourseFragment"
        val TITLE = "Mijn vakken"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.initViewModel()
    }

    override fun onStart() {
        super.onStart()
        selectCourseViewModel.getCourses().observe(this,
                Observer<Resource<List<CourseView>>> {
                    if (it != null) this.handleDataState(it.status, it.data, it.message)
                })
    }

    private fun handleDataState(resourceState: ResourceState, data: List<CourseView>?,
                                message: String?) {
        when (resourceState) {
            ResourceState.LOADING -> Log.d("Application", "SelectCourseFragment: handleDataState: loading")
            ResourceState.SUCCESS -> this.coursesRecyclerViewAdapter.addAll(data)
            ResourceState.ERROR -> Log.d("Application", "SelectCourseFragment: handleDataState: " + message)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.fragment_select_course, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.setupCoursesRecyclerView(view)
        this.setupAddCourseButton(view)
    }

    private fun initViewModel() {
        val courseCache = CourseCacheImpl(
                this.buildDataBase(),
                CourseEntityMapper(),
                PreferencesHelper(context))

        viewModelFactory = SelectCourseViewModelFactory(GetSelectedCourses(CourseDataRepository(
                CourseDataStoreFactory(courseCache, CourseCacheDataStore(
                        courseCache), CourseRemoteDataStore(CourseRemoteImpl(
                        CourseServiceFactory.makeCourseService(true), philip.com.remote.mapper.CourseEntityMapper()
                ))), CourseMapper()),
                JobExecutor(), UiThread()), com.philip.presentation.mapper.CourseMapper())

        selectCourseViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(SelectCourseViewModel::class.java)
    }

    private fun setupCoursesRecyclerView(parent: View) {

        val coursesRecyclerView = parent.findViewById<RecyclerView>(R.id.courses_recycler_view)
        coursesRecyclerViewAdapter = SelectCourseRecyclerViewAdapter(this.activity.applicationContext)
        coursesRecyclerViewAdapter.setOnItemClickListener(onCourseItemClickListener)

        coursesRecyclerView.adapter = coursesRecyclerViewAdapter
        coursesRecyclerView.layoutManager = LinearLayoutManager(this.activity)
    }

    private fun setupAddCourseButton(parent: View) {
        val addCourseButton = parent.findViewById<FloatingActionButton>(R.id.add_course_button)
        addCourseButton.setOnClickListener(onAddCourseButtonClickListener)
        this.addCourseButton = addCourseButton
    }

    fun buildDataBase(): CacheDatabase {
        return Room.databaseBuilder(this.context,
                CacheDatabase::class.java, "leeswijzer.db")
                .build().getInstance(this.context)
    }

    private val onCourseItemClickListener = object : OnCourseViewItemClickListener {

        override fun onClick(courseView: CourseView) {
            val selectSectionsFragment = SelectSectionsFragment.newInstance(courseView.name)

            val fragmentTransaction = fragmentManager.beginTransaction()

            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            fragmentTransaction.add(R.id.fragment_container,
                    selectSectionsFragment,
                    SelectSectionsFragment.TAG)
            fragmentTransaction.addToBackStack(SelectCourseFragment.TAG)
            fragmentTransaction.commit()
        }
    }

    private val onAddCourseButtonClickListener = View.OnClickListener {
        val addCourseFragment = AddCourseFragment()

        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        fragmentTransaction.add(R.id.fragment_container,
                addCourseFragment,
                AddCourseFragment.TAG)
        fragmentTransaction.addToBackStack(SelectCourseFragment.TAG)
        fragmentTransaction.commit()
    }
}