package com.murat.dailysmoking.ui.graph

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieEntry
import com.murat.dailysmoking.db.dao.SmokeDao
import com.murat.dailysmoking.db.dao.UserDao
import com.murat.dailysmoking.db.entity.Smoke
import com.murat.dailysmoking.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

const val DATE_FORMAT = "dd/MMM"

@HiltViewModel
class GraphViewModel @Inject constructor(
    val smokeDao: SmokeDao,
    val userDao: UserDao,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    fun getWeeklySmokeCount() {
        viewModelScope.launch(Dispatchers.IO) {
            var currentWeekStartDate = Calendar.getInstance().time.currentWeekBeginForGraph()
            var currentMonthStartDate = Calendar.getInstance().time.currentMonthBeginForGraph()

            val currentWeekSmokeMapper = mutableMapOf<Int, List<Smoke>>()
            val currentMonthSmokeMapper = mutableMapOf<Int, List<Smoke>>()
            val currentYearSmokeMapper = mutableMapOf<Int, List<Smoke>>()

            val weeklySmokingCountBarEntry: MutableList<BarEntry> = mutableListOf()
            val monthlySmokingCountBarEntry: MutableList<BarEntry> = mutableListOf()
            val yearlyPieChartEntry: MutableList<PieEntry> = mutableListOf()

            for (index in 1..7) {
                val dailyCount = smokeDao.fetchDailySmokeCountAsPrimitive(
                    currentWeekStartDate.startOfDay(),
                    currentWeekStartDate.endOfDay()
                )
                val dailySmokes: MutableList<Smoke> = smokeDao.fetchByDate(
                    currentWeekStartDate.startOfDay(),
                    currentWeekStartDate.endOfDay()
                ).toMutableList()

                if (dailySmokes.isEmpty()) {
                    dailySmokes.add(createDummySmokeItem(currentWeekStartDate))
                }
                currentWeekSmokeMapper[index] = dailySmokes

                weeklySmokingCountBarEntry.add(
                    BarEntry(
                        index.toFloat(),
                        dailyCount?.toFloat().orZero()
                    )
                )
                currentWeekStartDate = currentWeekStartDate.nextDay()
            }

            for (index in 1..currentMonthTotalDay()) {
                val dailyCount = smokeDao.fetchDailySmokeCountAsPrimitive(
                    currentMonthStartDate.startOfDay(),
                    currentMonthStartDate.endOfDay()
                )
                val dailySmokes: MutableList<Smoke> = smokeDao.fetchByDate(
                    currentMonthStartDate.startOfDay(),
                    currentMonthStartDate.endOfDay()
                ).toMutableList()

                if (dailySmokes.isEmpty()) {
                    dailySmokes.add(createDummySmokeItem(currentMonthStartDate))
                }
                currentMonthSmokeMapper[index] = dailySmokes

                monthlySmokingCountBarEntry.add(
                    BarEntry(
                        index.toFloat(),
                        dailyCount?.toFloat().orZero()
                    )
                )
                currentMonthStartDate = currentMonthStartDate.nextDay()
            }

            var monthOfYear = startOfYear()

            for (index in 1..12) {
                val monthlyCount = smokeDao.fetchDailySmokeCountAsPrimitive(
                    monthOfYear,
                    monthOfYear.lastDayOfMonth()
                )

                val monthlySmokes: MutableList<Smoke> = smokeDao.fetchByDate(
                    monthOfYear,
                    monthOfYear.lastDayOfMonth()
                ).toMutableList()

                if (monthlySmokes.isEmpty()) {
                    monthOfYear = monthOfYear.nextMonth()
                    continue
                }
                currentYearSmokeMapper[index] = monthlySmokes

                yearlyPieChartEntry.add(
                    PieEntry(
                        monthlyCount?.toFloat().orZero(),
                        monthOfYear.toFormat("MMM")
                    )
                )
                monthOfYear = monthOfYear.nextMonth()
            }

            _uiState.update { uiState ->
                uiState.copy(
                    weeklySmokingCountBarEntry = weeklySmokingCountBarEntry,
                    weeklyMap = currentWeekSmokeMapper,
                    monthlySmokingCountBarEntry = monthlySmokingCountBarEntry,
                    monthlyMap = currentMonthSmokeMapper,
                    yearlyPieChartEntry =yearlyPieChartEntry,
                    yearlyMap = currentYearSmokeMapper
                )
            }
        }
    }

    private fun createDummySmokeItem(date: Date) = Smoke(
        smokeTime = date,
        singleCigarettePrice = userDao.getSingleCigarettePrice().orZero,
        smokeCurrency = userDao.getCurrency().orEmpty()
    )

    data class UiState(
        var weeklySmokingCountBarEntry: List<BarEntry> = emptyList(),
        var weeklyMap: Map<Int, List<Smoke>> = mapOf(),
        var monthlySmokingCountBarEntry: List<BarEntry> = emptyList(),
        var monthlyMap: Map<Int, List<Smoke>> = mapOf(),
        var yearlyPieChartEntry: List<PieEntry> = emptyList(),
        var yearlyMap: Map<Int, List<Smoke>> = mapOf(),
    )
}