package com.murat.dailysmoking.ui.profile

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.murat.core.BaseViewModel
import com.murat.core.Event
import com.murat.core.UiState
import com.murat.core.domain.launch
import com.murat.dailysmoking.R
import com.murat.dailysmoking.db.dao.UserDao
import com.murat.dailysmoking.db.entity.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userDao: UserDao
) : BaseViewModel<ProfileViewModel.ProfileUiState, ProfileViewModel.ProfileEvent>(ProfileUiState()) {

    init {
        getUserInfo()
    }

    private fun getUserInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            val user = userDao.getUser()
            setState { ProfileUiState(user) }
        }
    }

    fun updateClick(packagePrice: String?, currency: String?, packageContent: String?) {
        if (packagePrice.isNullOrBlank()) {
            pushEvent(ProfileEvent.Error(message = R.string.on_boarding_error_package_price))
        } else if (currency.isNullOrBlank()) {
            pushEvent(ProfileEvent.Error(message = R.string.on_boarding_error_currency))
        } else if (packageContent.isNullOrBlank()) {
            pushEvent(ProfileEvent.Error(message = R.string.on_boarding_error_package_count))
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                val user = userDao.getUser()
                user?.let { safeUser ->
                    try {
                        val packageFinalPrice = packagePrice.toDouble()

                        if (packageFinalPrice == 0.0) {
                            pushEvent(ProfileEvent.Error(message = R.string.on_boarding_error_package_price))
                        } else {
                            val packageFinalContent = packageContent.toInt()

                            safeUser.singleCigarettePrice = packageFinalPrice / packageFinalContent
                            safeUser.packageContent = packageFinalContent
                            safeUser.packagePrice = packageFinalPrice
                            safeUser.currency = currency

                            userDao.update(safeUser)
                            pushEvent(ProfileEvent.UpdateUser)
                        }
                    } catch (e: Exception) {
                        pushEvent(ProfileEvent.Error(message = R.string.on_boarding_error_package_price))
                    }
                }
            }
        }
    }

    data class ProfileUiState(
        val user: User? = null
    ) : UiState

    sealed class ProfileEvent : Event {
        object UpdateUser : ProfileEvent()
        data class Error(@StringRes var message: Int) : ProfileEvent()
    }
}