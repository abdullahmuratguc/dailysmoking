package com.murat.core.domain

import androidx.lifecycle.viewModelScope
import com.murat.core.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import murat.utils.extensions.throwIfNotServiceError
import timber.log.Timber

/**
 * Created by Murat
 */

fun BaseViewModel<*, *>.launch(
    notifyProgress: Boolean = true,
    notifyError: Boolean = true,
    onError: (exception: Exception) -> Unit = {},
    onCompleted: () -> Unit = {},
    block: suspend CoroutineScope.() -> Unit
) = viewModelScope.launch {
    if (notifyProgress) {
        setProgress(showProgress = true)
    }

    try {
        block()
    } catch (exception: Exception) {
        exception.throwIfNotServiceError()

        onError(exception)
        if (notifyError) {
            setError(exception = exception)
        } else {
            Timber.e(exception)
        }
    } finally {
        onCompleted()
        if (notifyProgress) {
            setProgress(showProgress = false)
        }
    }
}