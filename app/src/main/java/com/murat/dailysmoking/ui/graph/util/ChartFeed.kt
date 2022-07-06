package com.murat.dailysmoking.ui.graph.util

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.MPPointF
import com.murat.dailysmoking.R
import com.murat.dailysmoking.db.entity.Smoke
import com.murat.dailysmoking.utils.EMPTY
import murat.utils.extensions.getPhoneMode

/**
 * Created by Murat
 */
object ChartFeed {
    const val NIGHT_MODE = 0
    const val LIGHT_MODE = 1

    fun setBarChartData(
        chart: BarChart,
        barEntries: List<BarEntry>,
        context: Context,
        smokeMap: Map<Int, List<Smoke>>
    ) {

        val set1 = BarDataSet(barEntries, String.EMPTY)
        set1.valueFormatter = SmokeValueFormatter()
        val xAxis = chart.xAxis
        xAxis.granularity = 1f
        xAxis.valueFormatter = XAxisValueDateFormatter(smokeMap)
        val dataSets: java.util.ArrayList<IBarDataSet> = ArrayList()
        dataSets.add(set1)
        val data = BarData(dataSets)
        chart.data = data
        //Set marker view
        val markerView = SmokeBarMarkerView(context)
        markerView.chartView = chart
        chart.marker = markerView

        val color = if (context.getPhoneMode() == NIGHT_MODE) {
            ContextCompat.getColor(context, R.color.white)
        } else {
            ContextCompat.getColor(context, R.color.black)
        }

        chart.axisLeft.textColor = color
        chart.axisRight.textColor = color
        chart.xAxis.textColor = color
        xAxis.textColor = color
        set1.valueTextColor = color

        chart.invalidate()
    }

    fun setPieChartData(
        chart: PieChart,
        pieEntries: List<PieEntry>
    ) {

        val dataSet = PieDataSet(pieEntries, String.EMPTY)
        dataSet.setDrawIcons(false)

        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f

        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter(chart))
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.WHITE)
        chart.data = data

        chart.highlightValues(null)

        chart.invalidate()
    }
}