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
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.philip.leeswijzer_app.CredentialProvider
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
    lateinit var mChart2: BarChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.initViewModels()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view = inflater!!.inflate(R.layout.fragment_statistics_overview, container, false)

        this.setupPieChart(view)
        this.setupBarChart(view)

        return view
    }

    override fun onStart() {
        super.onStart()

        getSectionsViewModel.getSelectedSections(
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

        getSelectedSectionsViewModelFactory = GetSelectedSectionsViewModelFactory(
                CredentialProvider(context).getStudentNumber(),
                GetSections(SectionDataRepository(
                SectionDataStoreFactory(sectionCache, SectionCacheDataStore(
                        sectionCache), SectionRemoteDataStore(SectionRemoteImpl(
                        SectionServiceFactory.makeSectionService(true), philip.com.remote.mapper.SectionEntityMapper()
                ))), SectionMapper()),
                JobExecutor(), UiThread()), com.philip.presentation.mapper.SectionMapper())

        selectSectionsViewModelFactory = SelectSectionsViewModelFactory(
                CredentialProvider(context).getStudentNumber(),
                SelectSection(SectionDataRepository(
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

    private fun setupPieChart(parent: View) {
        mChart = parent.findViewById(R.id.pieChart1) as PieChart
        mChart.getDescription().setEnabled(false)

        mChart.setCenterText(generateCenterText())
        mChart.setCenterTextSize(10f)

        // radius of the center hole in percent of maximum radius
        mChart.setHoleRadius(45f)
        mChart.setTransparentCircleRadius(50f)

        val legend1 = mChart.getLegend()
        legend1.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP)
        legend1.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT)
        legend1.orientation = Legend.LegendOrientation.VERTICAL
        legend1.setDrawInside(false)
        mChart.transparentCircleRadius = 50f
    }

    private fun setupBarChart(parent: View) {

        mChart2 = parent.findViewById(R.id.pieChart2) as BarChart
        mChart2.description.isEnabled = false
        mChart2.isClickable = false

        this.mChart2.xAxis.granularity = 1f
        this.mChart2.axisLeft.isEnabled = false
        this.mChart2.axisRight.isEnabled = false
        mChart2.legend.isEnabled = false
        val description = Description()
        description.textColor = Color.WHITE
        description.text = "Behaalde leerdoelen per vak"
        mChart2.description = description
        mChart2.setDrawGridBackground(false)

    }

    private fun handleDataState(resourceState: ResourceState, data: List<SectionView>?,
                                message: String?) {
        when (resourceState) {
            ResourceState.LOADING -> Log.d("Application", "SelectSectionFragment: handleDataState: loading")
            ResourceState.SUCCESS -> {
                Toast.makeText(context, "Sections loaded", Toast.LENGTH_LONG).show()
                if (data != null) {
                    mChart.data = this.generatePieData(data)
                    mChart2.data = this.generateBarChartData(data)
                }

                mChart.invalidate()
                mChart2.invalidate()
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

    /**
     * generates less data (1 DataSet, 4 values)
     * @return the generated PieData
     */
    private fun generateBarChartData(data: List<SectionView>): BarData {

        val entries = ArrayList<BarEntry>()
        val courseNames: List<String> = data.distinctBy { it.courseName }.map { it.courseName }
        courseNames.forEachIndexed({ index, element ->
            entries.add(BarEntry(index.toFloat(), data.count {
                it.isChecked && element == it.courseName
            }.toFloat()))
        })

        // Tel voor alle verschillende courses de hoeveelheden bij elkaar op.


        val barDataSet = BarDataSet(entries, null)
        barDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)
        barDataSet.valueTextColor = Color.WHITE
        barDataSet.valueTextSize = 12f
        barDataSet.stackLabels = courseNames.toTypedArray()
        this.mChart2.xAxis.valueFormatter = IndexAxisValueFormatter(courseNames)

        return BarData(barDataSet)
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