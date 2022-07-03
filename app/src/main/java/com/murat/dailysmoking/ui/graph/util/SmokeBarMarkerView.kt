package com.murat.dailysmoking.ui.graph.util

import android.annotation.SuppressLint
import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.murat.dailysmoking.R
import com.murat.dailysmoking.data.ui.SmokeUiModel
import com.murat.dailysmoking.utils.orZero

/**
 * Created by Murat
 */
@SuppressLint("ViewConstructor")
class SmokeBarMarkerView(context: Context) :
    MarkerView(context, R.layout.view_smoke_bar_markerview) {

    private val tvMarkerTitle = findViewById<TextView>(R.id.tvMarkerTitle)

    override fun refreshContent(e: Entry?, highlight: Highlight?) {

        tvMarkerTitle.text = context.getString(
            R.string.weekly_bar_chart_marker_text,
            e?.y?.toInt().orZero.toString()
        )
        super.refreshContent(e, highlight)

    }

    override fun getOffset(): MPPointF {
        return MPPointF((-(width / 2)).toFloat(), (-height).toFloat())
    }

}