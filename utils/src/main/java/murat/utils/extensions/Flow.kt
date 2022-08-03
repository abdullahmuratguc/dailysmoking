package murat.utils.extensions

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * Created by Murat
 */

fun <T> Flow<T>.collectIn(
    owner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    action: suspend CoroutineScope.(T) -> Unit = {}
) = owner.lifecycleScope.launch {
    owner.lifecycle.repeatOnLifecycle(minActiveState) {
        collect { action(it) }
    }
}