package com.murat.dailysmoking.ui.graph

import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.murat.core.withError
import com.murat.core.withProgress
import com.murat.core.withUiState
import com.murat.dailysmoking.R
import com.murat.dailysmoking.base.BaseFragment
import com.murat.dailysmoking.base.contentViewBinding
import com.murat.dailysmoking.databinding.FragmentGraphBinding
import com.murat.dailysmoking.ui.graph.util.ChartFeed
import com.murat.dailysmoking.utils.EMPTY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GraphFragment : BaseFragment() {

    private val binding by contentViewBinding(FragmentGraphBinding::bind)

    override val layoutId = R.layout.fragment_graph

    private val viewModel: GraphViewModel by viewModels()

    override fun initViews() {
        initBarChart(binding.weeklySmokeCountBarChart)
        initBarChart(binding.monthlySmokeCountBarChart)
        initPieChart(binding.yearlySpendPieChart)

        collectState()
        viewModel.getWeeklySmokeCount()
    }

    private fun collectState() = with(viewModel) {
        withUiState(this, ::setUI)
        withProgress(this, ::onProgress)
        withError(this, ::onError)
    }

    private fun setUI(uiState: GraphViewModel.GraphState) = with(binding) {
        ChartFeed.setBarChartData(
            chart = weeklySmokeCountBarChart,
            barEntries = uiState.weeklySmokingCountBarEntry,
            context = requireContext(),
            smokeMap = uiState.weeklyMap
        )
        ChartFeed.setBarChartData(
            chart = monthlySmokeCountBarChart,
            barEntries = uiState.monthlySmokingCountBarEntry,
            context = requireContext(),
            smokeMap = uiState.monthlyMap
        )
        ChartFeed.setPieChartData(
            chart = yearlySpendPieChart,
            pieEntries = uiState.yearlyPieChartEntry
        )
    }

    private fun initBarChart(barChart: BarChart) {
        barChart.legend.isEnabled = false
        barChart.xAxis.position = XAxisPosition.BOTTOM
        barChart.xAxis.setDrawGridLines(false)
        barChart.axisLeft.setDrawGridLines(false)
        barChart.axisRight.setDrawGridLines(false)
        barChart.axisLeft.axisMinimum = 0f
        barChart.axisRight.axisMinimum = 0f

        barChart.axisLeft.spaceTop = 15F
        barChart.axisRight.spaceTop = 15F

        barChart.isDoubleTapToZoomEnabled = false
        barChart.setPinchZoom(false)
        barChart.isAutoScaleMinMaxEnabled = true
        barChart.animateY(1500)
        barChart.description = Description().apply {
            text = String.EMPTY
        }
    }

    private fun initPieChart(chart: PieChart) {
        chart.setUsePercentValues(true)
        chart.description.isEnabled = false
        chart.setExtraOffsets(5f, 10f, 5f, 5f)

        chart.dragDecelerationFrictionCoef = 0.95f

        chart.centerText = getString(R.string.yearly_pie_chart_center_text)

        chart.isDrawHoleEnabled = true
        chart.setHoleColor(Color.WHITE)

        chart.setTransparentCircleColor(Color.WHITE)
        chart.setTransparentCircleAlpha(110)

        chart.holeRadius = 58f
        chart.transparentCircleRadius = 60f

        chart.setDrawCenterText(true)

        chart.rotationAngle = 0f
        chart.isRotationEnabled = true
        chart.isHighlightPerTapEnabled = true
        chart.setUsePercentValues(true)


        chart.animateY(1400, Easing.EaseInOutQuad)

        val l: Legend = chart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.xEntrySpace = 7f
        l.yEntrySpace = 0f
        l.yOffset = 0f

        chart.setEntryLabelColor(Color.WHITE)
        chart.setEntryLabelTextSize(12f)
    }
}