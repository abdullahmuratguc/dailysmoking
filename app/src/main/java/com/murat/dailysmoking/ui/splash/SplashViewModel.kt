package com.murat.dailysmoking.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.murat.dailysmoking.db.dao.UserDao
import com.murat.dailysmoking.db.entity.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Murat
 */

@HiltViewModel
class SplashViewModel @Inject constructor(
    private var userDao: UserDao
): ViewModel() {

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    fun startSplash() = viewModelScope.launch {
        delay(1000)
        viewModelScope.launch(Dispatchers.IO) {
            val currentUser = userDao.getUser()
            if (currentUser == null) {
                userDao.saveUser(
                    User(
                        isPrimeMember = false,
                        userName = "",
                        singleCigarettePrice = 00.00
                    )
                )
            }

            if (userDao.getUser()?.singleCigarettePrice == 00.00) {
                eventChannel.send(Event.NavigateToOnBoardingScreen)
            } else {
                eventChannel.send(Event.NavigateToHome)
            }
        }
    }

    sealed class Event {
        object NavigateToHome : Event()
        object NavigateToOnBoardingScreen : Event()
    }
}