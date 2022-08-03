package murat.utils.extensions

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import timber.log.Timber

/**
 * Created by Murat
 *
 */
const val NIGHT_MODE = 0
const val LIGHT_MODE = 1

fun Context.getPhoneMode(): Int {
    val nightModeFlags: Int = resources.configuration.uiMode and
            Configuration.UI_MODE_NIGHT_MASK
    return when (nightModeFlags) {
        Configuration.UI_MODE_NIGHT_YES -> NIGHT_MODE
        Configuration.UI_MODE_NIGHT_NO -> LIGHT_MODE
        Configuration.UI_MODE_NIGHT_UNDEFINED -> LIGHT_MODE
        else -> LIGHT_MODE
    }
}

fun <T, R> T.tryCatch(priority: Int? = Log.ERROR, block: (T) -> R): R? {
    return try {
        block(this)
    } catch (t: Throwable) {
        priority?.let { Timber.log(it, t) }
        null
    }
}