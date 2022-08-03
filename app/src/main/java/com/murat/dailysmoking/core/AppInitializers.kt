package com.murat.dailysmoking.core

import android.app.Application
import javax.inject.Inject

/**
 * Created by Murat
 */
class AppInitializers @Inject constructor(
    private val initializers: Set<@JvmSuppressWildcards AppInitializer>
) {

    fun init(application: Application) {
        initializers.forEach { it.init(application) }
    }
}