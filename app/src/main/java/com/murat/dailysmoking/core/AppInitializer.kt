package com.murat.dailysmoking.core

import android.app.Application

/**
 * Created by Murat
 */

interface AppInitializer {
    fun init(application: Application)
}