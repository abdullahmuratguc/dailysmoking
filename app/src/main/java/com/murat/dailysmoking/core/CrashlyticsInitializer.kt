package com.murat.dailysmoking.core

import android.app.Application
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.murat.dailysmoking.BuildConfig
import javax.inject.Inject

/**
 * Created by Murat
 */
class CrashlyticsInitializer @Inject constructor() : AppInitializer {

    override fun init(application: Application) {
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(!BuildConfig.DEBUG)
    }
}