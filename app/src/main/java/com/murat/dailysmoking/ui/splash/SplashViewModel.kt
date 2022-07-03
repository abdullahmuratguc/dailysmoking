package com.murat.dailysmoking.ui.splash

import androidx.lifecycle.viewModelScope
import com.murat.core.BaseViewModel
import com.murat.core.Event
import com.murat.core.UiState
import com.murat.dailysmoking.db.dao.UserDao
import com.murat.dailysmoking.db.entity.User
import com.murat.dailysmoking.utils.EMPTY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Murat
 */

@HiltViewModel
class SplashViewModel @Inject constructor(
    private var userDao: UserDao
): BaseViewModel<UiState, SplashViewModel.SplashEvent>(EmptyState()) {

    fun startSplash() = viewModelScope.launch {
        delay(1000)
        viewModelScope.launch(Dispatchers.IO) {
            val currentUser = userDao.getUser()
            if (currentUser == null) {
                userDao.saveUser(
                    User(
                        isPrimeMember = false,
                        userName = String.EMPTY,
                        singleCigarettePrice = 00.00
                    )
                )
            }

            if (userDao.getUser()?.singleCigarettePrice == 00.00) {
                pushEvent(SplashEvent.NavigateToOnBoardingScreen)
            } else {
                pushEvent(SplashEvent.NavigateToHome)
            }
        }
    }

    class EmptyState: UiState

    sealed class SplashEvent: Event {
        object NavigateToHome : SplashEvent()
        object NavigateToOnBoardingScreen : SplashEvent()
    }
}