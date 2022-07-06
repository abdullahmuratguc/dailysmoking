package com.murat.dailysmoking.core

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

/**
 * Created by Murat
 */
@Module
@InstallIn(SingletonComponent::class)
interface AppInitializerModule {

    @Binds
    @IntoSet
    fun bindTimberInitializer(initializer: TimberInitializer): AppInitializer

    @Binds
    @IntoSet
    fun bindCrashlyticsInitializer(initializer: CrashlyticsInitializer): AppInitializer
}