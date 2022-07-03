package com.murat.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * Created by Murat
 */
abstract class BaseViewModel<S : UiState, E : Event> constructor(initialState: S) : ViewModel() {

    val stateMutex = Mutex()

    private val _stateFlow = MutableStateFlow(State(initialState))
    val stateFlow: StateFlow<State<S>>
        get() = _stateFlow

    val currentUiState: S
        get() = _stateFlow.value.uiState

    /**
     * Sets the current ui state with [_stateFlow] of this [BaseViewModel].
     * E.g.:
     *  setState {
     *      copy(stateField = newValue)
     *  }
     */
    protected fun setState(reducer: S.() -> S) {
        pushState {
            val newState = _stateFlow.value.copy(uiState = reducer(currentUiState))
            _stateFlow.value = newState
        }
    }

    fun setProgress(showProgress: Boolean) {
        pushState {
            _stateFlow.value = _stateFlow.value.copy(progress = showProgress)
        }
    }

    fun setError(exception: Exception) {
        pushState {
            _stateFlow.value = _stateFlow.value.copy(error = exception)
        }.invokeOnCompletion {
            // Setting error as null is required when we do not want to show multiple error dialogs
            pushState {
                _stateFlow.value = _stateFlow.value.copy(error = null)
            }
        }
    }

    val eventMutex = Mutex()

    private val _eventFlow = MutableSharedFlow<E>()
    val eventFlow: Flow<E>
        get() = _eventFlow

    /**
     * Pushes a new event with [_eventFlow] from this [BaseViewModel].
     */
    protected fun pushEvent(event: E) = viewModelScope.launch {
        eventMutex.withLock {
            _eventFlow.emit(event)
        }
    }

    private fun pushState(action: () -> Unit) = viewModelScope.launch {
        stateMutex.withLock {
            action.invoke()
        }
    }
}

data class State<T : UiState>(
    val uiState: T,
    val progress: Boolean = false,
    val error: Exception? = null
)

/**
 * Marks a class/object as a UiState
 */
interface UiState

/**
 * Marks a class/object as an Event
 */
interface Event

/**
 * Can be used for a [BaseViewModel] with no events.
 */
object NoEvent : Event