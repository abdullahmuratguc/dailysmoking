package com.murat.dailysmoking.core

import android.app.Application
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Murat
 */
class TimberInitializer @Inject constructor() : AppInitializer {

    override fun init(application: Application) {
        Timber.plant(Timber.DebugTree())
    }
}