package com.murat.dailysmoking.ui.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.murat.dailysmoking.databinding.ViewDailyDatePickerBinding
import com.murat.dailysmoking.utils.toFormat
import java.util.*

/**
 * Created by Murat
 */
class DailyDatePickerView  @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    companion object {
        const val DATE_FORMAT = "dd/MM/yyyy"
    }

    private val binding: ViewDailyDatePickerBinding by lazy {
        ViewDailyDatePickerBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun setDayText(selectedDate: Date) {
        binding.selectedDateTv.text =
            selectedDate.toFormat(DATE_FORMAT)
    }

    fun setDayClickListener(action: () -> Unit) {
        binding.selectedDateTv.setOnClickListener {
            action.invoke()
        }
    }

    fun setPreviousDayClickListener(action: () -> Unit) {
        binding.previousDate.setOnClickListener {
            action.invoke()
        }
    }

    fun setNextDayClickListener(action: () -> Unit) {
        binding.nextDate.setOnClickListener {
            action.invoke()
        }
    }

}