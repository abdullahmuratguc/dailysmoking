package com.murat.dailysmoking.ui.onboarding

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.murat.core.BaseViewModel
import com.murat.core.Event
import com.murat.core.UiState
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
) : BaseViewModel<UiState, OnBoardingViewModel.OnBoardingEvent>(EmptyState()) {

    fun continueClick(packagePrice: String?, currency: String?, packageContent: String?) {
        if (packagePrice.isNullOrBlank()) {
            pushEvent(OnBoardingEvent.Error(message = R.string.on_boarding_error_package_price))
        } else if (currency.isNullOrBlank()) {
            pushEvent(OnBoardingEvent.Error(message = R.string.on_boarding_error_currency))
        } else if (packageContent.isNullOrBlank()) {
            pushEvent(OnBoardingEvent.Error(message = R.string.on_boarding_error_package_count))
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                val user = userDao.getUser()
                user?.let { safeUser ->
                    try {
                        val packageFinalPrice = packagePrice.toDouble()

                        if (packageFinalPrice == 0.0) {
                            pushEvent(OnBoardingEvent.Error(message = R.string.on_boarding_error_package_price))
                        } else {
                            val packageFinalContent = packageContent.toInt()

                            safeUser.singleCigarettePrice = packageFinalPrice / packageFinalContent
                            safeUser.packageContent = packageFinalContent
                            safeUser.packagePrice = packageFinalPrice
                            safeUser.currency = currency

                            userDao.update(safeUser)
                            pushEvent(OnBoardingEvent.NavigateToHome)
                        }
                    } catch (e: Exception) {
                        pushEvent(OnBoardingEvent.Error(message = R.string.on_boarding_error_package_price))
                    }
                }
            }
        }
    }

    class EmptyState: UiState

    sealed class OnBoardingEvent: Event {
        object NavigateToHome : OnBoardingEvent()
        data class Error(@StringRes var message: Int) : OnBoardingEvent()
    }
}