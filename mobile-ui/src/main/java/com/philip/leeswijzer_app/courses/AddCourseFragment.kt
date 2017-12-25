package com.philip.leeswijzer_app.courses

import android.app.FragmentTransaction
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.persistence.room.Room
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
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
import com.philip.presentation.course.GetAllCoursesViewModel
import com.philip.presentation.course.GetAllCoursesViewModelFactory
import com.philip.presentation.data.Resource
import com.philip.presentation.data.ResourceState
import com.philip.presentation.model.CourseView
import philip.com.cache.CourseCacheImpl
import philip.com.cache.PreferencesHelper
import philip.com.cache.database.CacheDatabase
import philip.com.data.CourseDataRepository
import philip.com.data.executor.JobExecutor
import philip.com.data.mapper.CourseMapper
import philip.com.data.models.SectionEntity
import philip.com.data.source.course.CourseCacheDataStore
import philip.com.data.source.course.CourseDataStoreFactory
import philip.com.data.source.course.CourseRemoteDataStore
import philip.com.domain.interactor.course.GetAllCourses
import philip.com.remote.CourseRemoteImpl
import philip.com.remote.CourseServiceFactory
import philip.com.remote.mapper.CourseEntityMapper

/**
 * @author Philip Wong
 * @since 25-11-17
 **/
class AddCourseFragment : Fragment() {

    private lateinit var coursesRecyclerViewAdapter: SelectCourseRecyclerViewAdapter
    private lateinit var viewModelFactory: GetAllCoursesViewModelFactory
    private lateinit var getAllCoursesViewModel: GetAllCoursesViewModel

    companion object {
        val TAG = "addCourseFragment"
        val TITLE = "Vak toevoegen"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.initViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.fragment_add_course, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.setupCoursesRecyclerView(view)
        this.setupActionBar()
    }

    override fun onStart() {
        super.onStart()
        getAllCoursesViewModel.getAllCourses().observe(this,
                Observer<Resource<List<CourseView>>> {
                    if (it != null) this.handleDataState(it.status, it.data, it.message)
                })
    }

    private fun setupActionBar() {
        val supportActionBar = (this.activity as AppCompatActivity).supportActionBar!!
        supportActionBar.setDisplayHomeAsUpEnabled(true)
        supportActionBar.title = TITLE

    }

    private fun handleDataState(resourceState: ResourceState, data: List<CourseView>?,
                                message: String?) {
        when (resourceState) {
            ResourceState.LOADING -> Log.d("Application", "SelectCourseFragment: handleDataState: loading")
            ResourceState.SUCCESS -> this.coursesRecyclerViewAdapter.addAll(data)
            ResourceState.ERROR -> Log.d("Application", "SelectCourseFragment: handleDataState: " + message)
        }
    }

    private fun initViewModel() {
        val courseCache = CourseCacheImpl(
                this.buildDataBase(),
                philip.com.cache.mapper.CourseEntityMapper(),
                PreferencesHelper(context))

        viewModelFactory = GetAllCoursesViewModelFactory(GetAllCourses(CourseDataRepository(
                CourseDataStoreFactory(courseCache, CourseCacheDataStore(
                        courseCache), CourseRemoteDataStore(CourseRemoteImpl(
                        CourseServiceFactory.makeCourseService(true), CourseEntityMapper()
                ))), CourseMapper()),
                JobExecutor(), UiThread()), com.philip.presentation.mapper.CourseMapper())

        getAllCoursesViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(GetAllCoursesViewModel::class.java)
    }

    private fun setupCoursesRecyclerView(parent: View) {

        val coursesRecyclerView = parent.findViewById<RecyclerView>(R.id.courses_recycler_view)
        coursesRecyclerViewAdapter = SelectCourseRecyclerViewAdapter(this.activity.applicationContext)
        coursesRecyclerViewAdapter.setOnItemClickListener(onCourseItemClickListener)

        coursesRecyclerView.adapter = coursesRecyclerViewAdapter
        coursesRecyclerView.layoutManager = LinearLayoutManager(this.activity)

        val sections = ArrayList<SectionEntity>()
        sections.add(SectionEntity(1L, "Polymorfism", false, "IOPR1"))
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
}