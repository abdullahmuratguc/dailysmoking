package com.murat.dailysmoking.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.murat.dailysmoking.data.ui.SmokeUiModel
import com.murat.dailysmoking.db.dao.SmokeDao
import com.murat.dailysmoking.db.dao.UserDao
import com.murat.dailysmoking.db.entity.Smoke
import com.murat.dailysmoking.utils.endOfDay
import com.murat.dailysmoking.utils.orZero
import com.murat.dailysmoking.utils.startOfDay
import com.murat.dailysmoking.utils.toFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
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
) : ViewModel() {

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    private fun sendEvent(event: Event) {
        viewModelScope.launch {
            eventChannel.send(event)
        }
    }

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

    fun deleteSmoke(id: Long, selectedDate: Date) {
        viewModelScope.launch(Dispatchers.IO) {
            smokeDao.deleteCigarette(id)
            getDailySmoke(selectedDate)
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
            sendEvent(Event.SmokeList(smokeUiModelList))
            val headerInfo = Event.HeaderInfo(
                dailyTotalSmokeCount = smokeDao.fetchDailySmokeCount(
                    startDate = startDate,
                    endDate = endDate
                ).toString(),
                dailyTotalSmokePrice = roundOffDecimal(
                    smokeDao.fetchDailySmokePrice(
                        startDate = startDate,
                        endDate = endDate
                    ) ?: 0.0
                ).orZero.toString() + " " + smokeDao.getCurrency().orEmpty(),
                lastSmokeTime = smokeDao.getLastSmoke()?.smokeTime
            )
            sendEvent(headerInfo)
        }
    }

    fun roundOffDecimal(number: Double): Double {
        val df = DecimalFormat("##.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(number).replace(",", ".").toDouble()
    }

    sealed class Event {
        data class SmokeList(val smokeList: List<SmokeUiModel>) : Event()
        data class HeaderInfo(
            val dailyTotalSmokeCount: String,
            val dailyTotalSmokePrice: String,
            val lastSmokeTime: Date?
        ) : Event()
    }
}