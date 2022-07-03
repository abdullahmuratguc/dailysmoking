package murat.utils.extensions

import kotlinx.serialization.SerializationException

/**
 * Created by Murat
 */

fun Throwable.isServiceError(): Boolean = when (this) {
    is SerializationException -> true
    else -> false
}

fun Throwable.throwIfNotServiceError() {
    if (isServiceError().not()) {
        throw this
    }
}