package com.murat.dailysmoking.ui.graph.util

import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.murat.dailysmoking.db.entity.Smoke
import com.murat.dailysmoking.utils.EMPTY
import com.murat.dailysmoking.utils.toFormat

/**
 * Created by Murat
 */
private const val DATE_FORMATTER = "MMM"
class PieChartFormatter(val smokeMap: Map<Int, List<Smoke>>): ValueFormatter() {

    override fun getPieLabel(value: Float, pieEntry: PieEntry?): String {
        return try {
            smokeMap[value.toInt()]?.firstOrNull()?.smokeTime?.toFormat(DATE_FORMATTER) ?: String.EMPTY
        } catch (e: Exception) {
            String.EMPTY
        }
    }
}