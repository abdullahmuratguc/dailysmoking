package com.murat.dailysmoking.ui.home

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.murat.core.withError
import com.murat.core.withEvent
import com.murat.core.withProgress
import com.murat.core.withUiState
import com.murat.dailysmoking.R
import com.murat.dailysmoking.base.BaseFragment
import com.murat.dailysmoking.base.contentViewBinding
import com.murat.dailysmoking.databinding.FragmentHomeBinding
import com.murat.dailysmoking.utils.Constants.TIMER_FORMAT
import com.murat.dailysmoking.utils.Constants.TIMER_INITIAL
import com.murat.dailysmoking.utils.nextDay
import com.murat.dailysmoking.utils.prevDay
import dagger.hilt.android.AndroidEntryPoint
import timerx.Stopwatch
import timerx.buildStopwatch
import java.util.*
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private val binding by contentViewBinding(FragmentHomeBinding::bind)

    override val layoutId = R.layout.fragment_home

    private val viewModel: HomeViewModel by viewModels()

    private val stopwatch: Stopwatch by lazy {
        buildStopwatch {
            startFormat(TIMER_FORMAT)
            onTick { _, formattedTime ->
                binding.lastSmokeTimerTv.text = formattedTime
            }
        }
    }

    private var selectedDate: Date? = null

    private val dailySmokeAdapter: DailySmokeAdapter by lazy {
        DailySmokeAdapter(
            onClickSmoke = {},
            onClickDelete = { smoke, position ->
                viewModel.deleteSmoke(smoke.id, selectedDate!!, position)
            }
        )
    }

    override fun initViews() {
        initUI()
        collectState()
    }

    private fun initUI() {
        initTimeSelectionView()
        selectedDate = Calendar.getInstance().time
        viewModel.getDailySmoke(selectedDate = selectedDate!!)
        binding.dailySmokeListRv.adapter = dailySmokeAdapter
        binding.addSmoke.setOnClickListener {
            val selDate = selectedDate
            viewModel.addSmoke(selectedDate = selDate!!)
        }
    }

    private fun initTimeSelectionView() {
        binding.dailyDatePicker.setDayText(selectedDate?:Calendar.getInstance().time)

        binding.dailyDatePicker.setDayClickListener {
            val calendar = Calendar.getInstance()
            val startFrom = calendar.timeInMillis
            val constraints = CalendarConstraints.Builder()
                .setEnd(startFrom)
                .build()

            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText(getString(R.string.select_date))
                    .setCalendarConstraints(constraints)
                    .setSelection(selectedDate!!.time)
                    .build()
            datePicker.addOnPositiveButtonClickListener { timestamp ->
                selectedDate = Date(timestamp)
                binding.dailyDatePicker.setDayText(selectedDate!!)
                viewModel.getDailySmoke(selectedDate!!)
            }

            datePicker.show(parentFragmentManager, TAG_DATE_PICKER)
        }

        binding.dailyDatePicker.setPreviousDayClickListener {
            selectedDate = selectedDate!!.prevDay()
            binding.dailyDatePicker.setDayText(selectedDate!!)
            viewModel.getDailySmoke(selectedDate!!)
        }

        binding.dailyDatePicker.setNextDayClickListener {
            selectedDate = selectedDate!!.nextDay()
            binding.dailyDatePicker.setDayText(selectedDate!!)
            viewModel.getDailySmoke(selectedDate!!)
        }
    }

    private fun collectState() = with(viewModel) {
        withUiState(this, ::setData)
        withProgress(this, ::onProgress)
        withError(this, ::onError)
        withEvent(this) { event ->
            when(event) {
                is HomeViewModel.HomeEvent.DeleteSmokeEvent -> {
                    val newSmokeList = dailySmokeAdapter.currentList.toMutableList()
                    newSmokeList.removeAt(event.position)
                    dailySmokeAdapter.submitList(newSmokeList)
                    binding.dailySmokeCountBodyTv.text =
                        binding.dailySmokeCountBodyTv.text.toString()
                            .toInt()
                            .minus(1)
                            .toString()
                }
            }
        }
    }

    private fun setData(state: HomeViewModel.HomeState) {
        dailySmokeAdapter.submitList(state.smokeList) {
            if (state.smokeList.isNotEmpty()) {
                binding.dailySmokeListRv.scrollToPosition(0)
                binding.dailySmokeListRv.isVisible = true
                binding.emptySmokingView.isVisible = false
            } else {
                binding.dailySmokeListRv.isVisible = false
                binding.emptySmokingView.isVisible = true
            }
        }
        state.lastSmokeTime?.let { date ->
            stopwatch.setTime(
                System.currentTimeMillis() - date.time,
                TimeUnit.MILLISECONDS
            )
            stopwatch.start()

            binding.dailySmokeCountBodyTv.text = state.dailyTotalSmokeCount
            binding.dailySmokePriceBodyTv.text = state.dailyTotalSmokePrice
        } ?: run {
            binding.lastSmokeTimerTv.text = TIMER_INITIAL
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stopwatch.stop()
    }

    companion object {
        const val TAG_DATE_PICKER = "Tag_Date_Picker"
    }
}