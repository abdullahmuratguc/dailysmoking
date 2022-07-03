package com.murat.dailysmoking

import android.app.Application
import com.murat.dailysmoking.core.AppInitializers
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * Created by Murat
 */

@HiltAndroidApp
class DailySmokingApp: Application() {
    @Inject
    lateinit var appInitializers: AppInitializers

    override fun onCreate() {
        super.onCreate()
        appInitializers.init(this)
    }
}