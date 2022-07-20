package com.murat.dailysmoking.ui.home

import androidx.lifecycle.viewModelScope
import com.murat.core.BaseViewModel
import com.murat.core.Event
import com.murat.core.NoEvent
import com.murat.core.UiState
import com.murat.dailysmoking.data.ui.SmokeUiModel
import com.murat.dailysmoking.db.dao.SmokeDao
import com.murat.dailysmoking.db.dao.UserDao
import com.murat.dailysmoking.db.entity.Smoke
import com.murat.dailysmoking.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*
import javax.inject.Inject

const val DATE_FORMAT = "dd MMM yyyy HH:mm:ss"

@HiltViewModel
class HomeViewModel @Inject constructor(
    private var userDao: UserDao,
    private var smokeDao: SmokeDao
) : BaseViewModel<HomeViewModel.HomeState, HomeViewModel.HomeEvent>(HomeState()) {

    fun addSmoke(selectedDate: Date) {
        val today = Calendar.getInstance().time
        viewModelScope.launch(Dispatchers.IO) {
            smokeDao.addCigarette(
                Smoke(
                    smokeTime = today,
                    singleCigarettePrice = userDao.getSingleCigarettePrice() ?: 00.00,
                    smokeCurrency = userDao.getCurrency().orEmpty()
                )
            )
            if (today.startOfDay() < selectedDate && selectedDate < today.endOfDay()) {
                getDailySmoke(selectedDate)
            }
        }
    }

    fun deleteSmoke(id: Long, selectedDate: Date, position: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            smokeDao.deleteCigarette(id)
            if (position == Int.ZERO) {
                getDailySmoke(selectedDate)
            } else {
                pushEvent(HomeEvent.DeleteSmokeEvent(position = position))
            }
        }
    }

    fun getDailySmoke(selectedDate: Date) {
        viewModelScope.launch(Dispatchers.IO) {
            val startDate = selectedDate.startOfDay()
            val endDate = selectedDate.endOfDay()

            val dailySmokeList = smokeDao.fetchByDate(
                startDate = startDate,
                endDate = endDate
            )
            val smokeUiModelList = dailySmokeList.map { smoke ->
                SmokeUiModel(
                    id = smoke.cigaretteId,
                    date = smoke.smokeTime?.toFormat(DATE_FORMAT).orEmpty(),
                    price = roundOffDecimal(smoke.singleCigarettePrice).toString() + " " + smoke.smokeCurrency.orEmpty()
                )
            }
            val homeState = HomeState(
                smokeList = smokeUiModelList,
                dailyTotalSmokeCount = smokeDao.fetchDailySmokeCount(
                    startDate = startDate,
                    endDate = endDate
                ).toString(),
                dailyTotalSmokePrice = roundOffDecimal(
                    number = smokeDao.fetchDailySmokePrice(
                        startDate = startDate,
                        endDate = endDate
                    ) ?: 0.0
                ).orZero.toString() + " " + userDao.getCurrency().orEmpty(),
                lastSmokeTime = smokeDao.getLastSmoke()?.smokeTime
            )
            setState {
                homeState
            }
        }
    }

    fun roundOffDecimal(number: Double): Double {
        val df = DecimalFormat("##.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(number).replace(",", ".").toDouble()
    }

    data class HomeState(
        val smokeList: List<SmokeUiModel> = emptyList(),
        val dailyTotalSmokeCount: String = String.EMPTY,
        val dailyTotalSmokePrice: String = String.EMPTY,
        val lastSmokeTime: Date? = null
    ) : UiState

    sealed class HomeEvent : Event {
        data class DeleteSmokeEvent(val position: Int) : HomeEvent()
    }
}