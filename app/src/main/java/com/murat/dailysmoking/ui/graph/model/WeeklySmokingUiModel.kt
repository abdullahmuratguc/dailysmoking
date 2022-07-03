package com.murat.dailysmoking.ui.graph.model

import com.github.mikephil.charting.data.BarEntry

/**
 * Created by Murat
 */
data class WeeklySmokingUiModel(
    var barEntries: List<BarEntry> = emptyList()
)
