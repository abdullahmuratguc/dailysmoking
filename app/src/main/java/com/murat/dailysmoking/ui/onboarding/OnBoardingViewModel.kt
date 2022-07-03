package com.murat.dailysmoking.ui.onboarding

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.murat.dailysmoking.R
import com.murat.dailysmoking.db.dao.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Murat
 */

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val userDao: UserDao
) : ViewModel() {

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    private fun sendEvent(event: Event) {
        viewModelScope.launch {
            eventChannel.send(event)
        }
    }

    fun continueClick(packagePrice: String?, currency: String?, packageContent: String?) {
        if (packagePrice.isNullOrBlank()) {
            sendEvent(Event.Error(message = R.string.on_boarding_error_package_price))
        } else if (currency.isNullOrBlank()) {
            sendEvent(Event.Error(message = R.string.on_boarding_error_currency))
        } else if (packageContent.isNullOrBlank()) {
            sendEvent(Event.Error(message = R.string.on_boarding_error_package_count))
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                val user = userDao.getUser()
                user?.let { safeUser ->
                    try {
                        val packageFinalPrice = packagePrice.toDouble()

                        if (packageFinalPrice == 0.0) {
                            sendEvent(Event.Error(message = R.string.on_boarding_error_package_price))
                        } else {
                            val packageFinalContent = packageContent.toInt()

                            safeUser.singleCigarettePrice = packageFinalPrice / packageFinalContent
                            safeUser.packageContent = packageFinalContent
                            safeUser.packagePrice = packageFinalPrice
                            safeUser.currency = currency

                            userDao.update(safeUser)
                            sendEvent(Event.NavigateToHome)
                        }
                    } catch (e: Exception) {
                        sendEvent(Event.Error(message = R.string.on_boarding_error_package_price))
                    }
                }
            }
        }
    }

    sealed class Event {
        object NavigateToHome : Event()
        data class Error(@StringRes var message: Int) : Event()
    }
}