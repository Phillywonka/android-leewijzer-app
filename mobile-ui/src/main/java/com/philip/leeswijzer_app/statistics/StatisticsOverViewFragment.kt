package com.philip.leeswijzer_app.statistics

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.persistence.room.Room
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.philip.leeswijzer_app.R
import com.philip.leeswijzer_app.UiThread
import com.philip.presentation.data.Resource
import com.philip.presentation.data.ResourceState
import com.philip.presentation.model.SectionView
import com.philip.presentation.section.GetSectionsViewModel
import com.philip.presentation.section.GetSelectedSectionsViewModelFactory
import com.philip.presentation.section.SelectSectionViewModel
import com.philip.presentation.section.SelectSectionsViewModelFactory
import philip.com.cache.PreferencesHelper
import philip.com.cache.SectionCacheImpl
import philip.com.cache.database.CacheDatabase
import philip.com.cache.mapper.SectionEntityMapper
import philip.com.data.SectionDataRepository
import philip.com.data.executor.JobExecutor
import philip.com.data.mapper.SectionMapper
import philip.com.data.source.section.SectionCacheDataStore
import philip.com.data.source.section.SectionDataStoreFactory
import philip.com.data.source.section.SectionRemoteDataStore
import philip.com.domain.interactor.sections.GetSections
import philip.com.domain.interactor.sections.SelectSection
import philip.com.remote.SectionRemoteImpl
import philip.com.remote.SectionServiceFactory


/**
 * @author Philip Wong
 * @since 25-11-17
 **/
open class StatisticsOverViewFragment : Fragment() {

    companion object {
        val TAG = "statisticsFragment"
    }

    private lateinit var getSelectedSectionsViewModelFactory: GetSelectedSectionsViewModelFactory
    private lateinit var selectSectionsViewModelFactory: SelectSectionsViewModelFactory
    private lateinit var getSectionsViewModel: GetSectionsViewModel
    private lateinit var selectSectionViewModel: SelectSectionViewModel

    lateinit var mChart: PieChart
    private val mLabels = arrayOf("Company A", "Company B", "Company C", "Company D", "Company E", "Company F")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.initViewModels()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view = inflater!!.inflate(R.layout.fragment_statistics_overview, container, false)

        this.mChart = view.findViewById(R.id.pieChart1)

        mChart = view.findViewById(R.id.pieChart1) as PieChart
        mChart.getDescription().setEnabled(false)

        mChart.setCenterText(generateCenterText())
        mChart.setCenterTextSize(10f)

        // radius of the center hole in percent of maximum radius
        mChart.setHoleRadius(45f)
        mChart.setTransparentCircleRadius(50f)

        val l = mChart.getLegend()
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP)
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT)
        l.setOrientation(Legend.LegendOrientation.VERTICAL)
        l.setDrawInside(false)

        return view
    }

    override fun onStart() {
        super.onStart()

        getSectionsViewModel.getSelectedSections("1085328",
                "all").observe(this,
                Observer<Resource<List<SectionView>>> {
                    if (it != null) this.handleDataState(it.status, it.data, it.message)
                })
    }

    private fun initViewModels() {
        val sectionCache = SectionCacheImpl(
                this.buildDataBase(),
                SectionEntityMapper(),
                PreferencesHelper(context))

        getSelectedSectionsViewModelFactory = GetSelectedSectionsViewModelFactory(GetSections(SectionDataRepository(
                SectionDataStoreFactory(sectionCache, SectionCacheDataStore(
                        sectionCache), SectionRemoteDataStore(SectionRemoteImpl(
                        SectionServiceFactory.makeSectionService(true), philip.com.remote.mapper.SectionEntityMapper()
                ))), SectionMapper()),
                JobExecutor(), UiThread()), com.philip.presentation.mapper.SectionMapper())

        selectSectionsViewModelFactory = SelectSectionsViewModelFactory(SelectSection(SectionDataRepository(
                SectionDataStoreFactory(sectionCache, SectionCacheDataStore(
                        sectionCache), SectionRemoteDataStore(SectionRemoteImpl(
                        SectionServiceFactory.makeSectionService(true), philip.com.remote.mapper.SectionEntityMapper()
                ))), SectionMapper()),
                JobExecutor(), UiThread()), com.philip.presentation.mapper.SectionMapper())

        getSectionsViewModel = ViewModelProviders.of(this, getSelectedSectionsViewModelFactory)
                .get(GetSectionsViewModel::class.java)

        selectSectionViewModel = ViewModelProviders.of(this, selectSectionsViewModelFactory)
                .get(SelectSectionViewModel::class.java)
    }

    private fun handleDataState(resourceState: ResourceState, data: List<SectionView>?,
                                message: String?) {
        when (resourceState) {
            ResourceState.LOADING -> Log.d("Application", "SelectSectionFragment: handleDataState: loading")
            ResourceState.SUCCESS -> {
                Toast.makeText(context, "Sections loaded", Toast.LENGTH_LONG).show()
                if (data != null) {
                    mChart.data = this.generatePieData(data)
                }

                mChart.invalidate()
            }
            ResourceState.ERROR -> Log.d("Application", "SelectSectionFragment: handleDataState: ERROR: $message")
        }
    }

    /**
     * generates less data (1 DataSet, 4 values)
     * @return the generated PieData
     */
    private fun generatePieData(data: List<SectionView>): PieData {

        val entries = ArrayList<PieEntry>()

        entries.add(PieEntry(data.count { it.isChecked }.toFloat(), "Behaald"))
        entries.add(PieEntry(data.count { !it.isChecked }.toFloat(), "Nog te doen"))

        val pieDataSet = PieDataSet(entries, null)
        pieDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)
        pieDataSet.sliceSpace = 2f
        pieDataSet.valueTextColor = Color.WHITE
        pieDataSet.valueTextSize = 12f

        return PieData(pieDataSet)
    }

    private fun generateCenterText(): SpannableString {
        val s = SpannableString("Leer items")
        s.setSpan(RelativeSizeSpan(2f), 0, 10, 0)
        s.setSpan(ForegroundColorSpan(Color.GRAY), 10, s.length, 0)
        return s
    }

    fun buildDataBase(): CacheDatabase {
        return Room.databaseBuilder(this.context,
                CacheDatabase::class.java, "leeswijzer.db")
                .build().getInstance(this.context)
    }

}