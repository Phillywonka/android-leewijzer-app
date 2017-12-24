package com.philip.leeswijzer_app.statistics

import android.app.Fragment
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.philip.leeswijzer_app.R


/**
 * @author Philip Wong
 * @since 25-11-17
 **/
class StatisticsOverViewFragment : Fragment() {

    companion object {
        val TAG = "statisticsFragment"
    }

    lateinit var mChart: PieChart
    private val mLabels = arrayOf("Company A", "Company B", "Company C", "Company D", "Company E", "Company F")


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

        mChart.setData(this.generatePieData())

        return view
    }

    /**
     * generates less data (1 DataSet, 4 values)
     * @return
     */
    protected fun generatePieData(): PieData {

        val count = 2

        val entries1 = ArrayList<PieEntry>()

        for (i in 0 until count) {
            entries1.add(PieEntry((Math.random() * 60 + 40).toFloat(), "Quarter " + (i + 1)))
        }

        val ds1 = PieDataSet(entries1, "Quarterly Revenues 2015")
        ds1.setColors(*ColorTemplate.COLORFUL_COLORS)
        ds1.sliceSpace = 2f
        ds1.valueTextColor = Color.WHITE
        ds1.valueTextSize = 12f

        val d = PieData(ds1)

        return d
    }

    private fun generateCenterText(): SpannableString {
        val s = SpannableString("Leer items")
        s.setSpan(RelativeSizeSpan(2f), 0, 10, 0)
        s.setSpan(ForegroundColorSpan(Color.GRAY), 10, s.length, 0)
        return s
    }

    private fun generateBarData(dataSets: Int, range: Float, count: Int): BarData {

        val tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");

        val sets = ArrayList<IBarDataSet>()

        for (i in 0 until dataSets) {

            val entries = ArrayList<BarEntry>()

            //            entries = FileUtils.loadEntriesFromAssets(getActivity().getAssets(), "stacked_bars.txt");

            for (j in 0 until count) {
                entries.add(BarEntry(j.toFloat(), (Math.random() * range).toFloat() + range / 4))
            }

            val ds = BarDataSet(entries, getLabel(i))
            ds.setColors(*ColorTemplate.VORDIPLOM_COLORS)
            sets.add(ds)
        }

        val d = BarData(sets)
        d.setValueTypeface(tf)
        return d
    }

    private fun getLabel(i: Int): String {
        return mLabels[i]
    }
}