package murat.utils.extensions

import kotlin.LazyThreadSafetyMode.NONE

/**
 * Created by Murat
 */

fun <T> unsafeLazy(initializer: () -> T): Lazy<T> = lazy(NONE, initializer)
