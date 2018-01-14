package com.philip.leeswijzer_app.sections

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
import com.philip.presentation.data.Resource
import com.philip.presentation.data.ResourceState
import com.philip.presentation.mapper.SectionMapper
import com.philip.presentation.model.SectionView
import com.philip.presentation.section.GetSectionsViewModel
import com.philip.presentation.section.GetSelectedSectionsViewModelFactory
import philip.com.cache.PreferencesHelper
import philip.com.cache.SectionCacheImpl
import philip.com.cache.database.CacheDatabase
import philip.com.data.SectionDataRepository
import philip.com.data.executor.JobExecutor
import philip.com.data.source.section.SectionCacheDataStore
import philip.com.data.source.section.SectionDataStoreFactory
import philip.com.data.source.section.SectionRemoteDataStore
import philip.com.domain.interactor.sections.GetSections
import philip.com.remote.SectionRemoteImpl
import philip.com.remote.SectionServiceFactory
import philip.com.remote.mapper.SectionEntityMapper

/**
 * @author Philip Wong
 * @since 01-12-17
 **/
class SelectSectionsFragment : Fragment() {

    private lateinit var sectionsRecyclerViewAdapter: SelectSectionRecyclerViewAdapter
    private lateinit var viewModelFactory: GetSelectedSectionsViewModelFactory
    private lateinit var selectSectionViewModel: GetSectionsViewModel

    companion object {
        val TAG = "selectSectionFragment"

        fun newInstance(sectionName: String): SelectSectionsFragment {

            val selectSectionsFragment = SelectSectionsFragment()
            val bundle = Bundle()
            bundle.putString("course_name", sectionName)
            selectSectionsFragment.arguments = bundle

            return selectSectionsFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.initViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.fragment_select_sections, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.setupActionBar()
        this.setupSectionsRecyclerView(view!!)
    }

    override fun onStart() {
        super.onStart()
        selectSectionViewModel.getSelectedSections("1085328",
                arguments.getString("course_name")).observe(this,
                Observer<Resource<List<SectionView>>> {
                    if (it != null) this.handleDataState(it.status, it.data, it.message)
                })

    }

    private fun handleDataState(resourceState: ResourceState, data: List<SectionView>?,
                                message: String?) {
        when (resourceState) {
            ResourceState.LOADING -> Log.d("Application", "SelectSectionFragment: handleDataState: loading")
            ResourceState.SUCCESS -> {
                Log.d("Application", "SelectSectionFragment: handleDataState: section added")
                this.sectionsRecyclerViewAdapter.clear()
                this.sectionsRecyclerViewAdapter.addAll(data!!)
            }
            ResourceState.ERROR -> Log.d("Application", "SelectSectionFragment: handleDataState: ERROR: " + message)
        }
    }

    private fun initViewModel() {
        val sectionCache = SectionCacheImpl(
                this.buildDataBase(),
                philip.com.cache.mapper.SectionEntityMapper(),
                PreferencesHelper(context))

        viewModelFactory = GetSelectedSectionsViewModelFactory(GetSections(SectionDataRepository(
                SectionDataStoreFactory(sectionCache, SectionCacheDataStore(
                        sectionCache), SectionRemoteDataStore(SectionRemoteImpl(
                        SectionServiceFactory.makeSectionService(true), SectionEntityMapper()
                ))), philip.com.data.mapper.SectionMapper()),
                JobExecutor(), UiThread()), SectionMapper())

        selectSectionViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(GetSectionsViewModel::class.java)
    }

    fun buildDataBase(): CacheDatabase {
        return Room.databaseBuilder(this.context,
                CacheDatabase::class.java, "leeswijzer.db")
                .build().getInstance(this.context)
    }

    private fun setupActionBar() {
        val supportActionBar = (this.activity as AppCompatActivity).supportActionBar!!
        supportActionBar.setDisplayHomeAsUpEnabled(true)
        supportActionBar.title = this.arguments.getString("course_name")

    }

    private fun setupSectionsRecyclerView(parent: View) {

        val sectionsRecyclerView = parent.findViewById<RecyclerView>(R.id.sections_recycler_view)
        sectionsRecyclerViewAdapter = SelectSectionRecyclerViewAdapter(this.activity)
        sectionsRecyclerView.adapter = sectionsRecyclerViewAdapter
        sectionsRecyclerView.layoutManager = LinearLayoutManager(this.activity)
    }


}