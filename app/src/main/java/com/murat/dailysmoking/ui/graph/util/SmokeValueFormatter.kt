package com.murat.dailysmoking.ui.graph.util

import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.murat.dailysmoking.data.ui.SmokeUiModel
import com.murat.dailysmoking.utils.orZero
import com.murat.dailysmoking.utils.toFormat
import java.util.*

/**
 * Created by Murat
 */

class SmokeValueFormatter : ValueFormatter() {
    override fun getBarLabel(barEntry: BarEntry?): String {
        return barEntry?.y?.toInt().orZero.toString()
    }
}