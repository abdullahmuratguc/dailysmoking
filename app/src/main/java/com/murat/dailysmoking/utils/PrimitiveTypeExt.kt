package com.murat.dailysmoking.utils

import android.content.Context
import android.util.DisplayMetrics

/**
 * Created by Murat
 */
fun Float?.orZero() = this ?: 0.0f

val Int?.orZero: Int get() = this ?: 0

val Double?.orZero: Double get() = this ?: 0.0

val Int.Companion.NOT_FOUND_INDEX: Int get() = -1

val Int.Companion.ZERO: Int get() = 0

val String.Companion.EMPTY: String get() = ""


fun Int.dpToPx(context: Context): Int {
    val displayMetrics = context.resources.displayMetrics
    return this * (displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
}

fun Float.format(digits: Int) = "%.${digits}f".format(this)