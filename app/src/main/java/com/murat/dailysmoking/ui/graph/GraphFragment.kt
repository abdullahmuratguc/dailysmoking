package com.murat.dailysmoking.ui.graph

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.murat.dailysmoking.R
import com.murat.dailysmoking.databinding.FragmentGraphBinding
import com.murat.dailysmoking.ui.graph.util.ChartFeed
import com.murat.dailysmoking.utils.EMPTY
import com.murat.dailysmoking.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GraphFragment : Fragment(R.layout.fragment_graph) {

    private val binding by viewBinding(FragmentGraphBinding::bind)

    private val viewModel: GraphViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observe()
        viewModel.getWeeklySmokeCount()
    }

    private fun initView() {
        initBarChart(binding.weeklySmokeCountBarChart)
        initBarChart(binding.monthlySmokeCountBarChart)
        initPieChart(binding.yearlySpendPieChart)
    }

    private fun observe() {
        lifecycleScope.launchWhenCreated {
            viewModel.uiState.collect(::setUI)
        }
    }

    private fun setUI(uiState: GraphViewModel.UiState) = with(binding) {
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
            pieEntries = uiState.yearlyPieChartEntry,
            context = requireContext(),
            smokeMap = uiState.yearlyMap
        )
    }

    private fun initBarChart(barChart: BarChart) {
        barChart.axisLeft.textColor = ContextCompat.getColor(barChart.context, R.color.white)
        barChart.axisRight.textColor = ContextCompat.getColor(barChart.context, R.color.white)
        barChart.legend.isEnabled = false
        barChart.xAxis.position = XAxisPosition.BOTTOM
        barChart.xAxis.setDrawGridLines(false)
        barChart.axisLeft.setDrawGridLines(false)
        barChart.axisRight.setDrawGridLines(false)
        barChart.axisLeft.axisMinimum = 0f
        barChart.axisRight.axisMinimum = 0f
        barChart.xAxis.textColor = ContextCompat.getColor(requireContext(), R.color.white)

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
        // enable rotation of the chart by touch
        // enable rotation of the chart by touch
        chart.isRotationEnabled = true
        chart.isHighlightPerTapEnabled = true
        chart.setUsePercentValues(true)

        // chart.setUnit(" €");
        // chart.setDrawUnitsInChart(true);

        // add a selection listener

        // chart.setUnit(" €");
        // chart.setDrawUnitsInChart(true);


        chart.animateY(1400, Easing.EaseInOutQuad)
        // chart.spin(2000, 0, 360);

        // chart.spin(2000, 0, 360);
        val l: Legend = chart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.xEntrySpace = 7f
        l.yEntrySpace = 0f
        l.yOffset = 0f

        // entry label styling

        // entry label styling
        chart.setEntryLabelColor(Color.WHITE)
        chart.setEntryLabelTextSize(12f)
    }
}