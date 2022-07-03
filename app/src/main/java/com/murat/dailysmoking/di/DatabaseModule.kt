package com.murat.dailysmoking.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.murat.dailysmoking.db.AppDatabase
import com.murat.dailysmoking.db.dao.SmokeDao
import com.murat.dailysmoking.db.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Murat
 */

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application, callback: AppDatabase.Callback): AppDatabase{
        return Room.databaseBuilder(application, AppDatabase::class.java, "daily_smoking_db")
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()
    }

    @Provides
    @Singleton
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }

    @Provides
    @Singleton
    fun provideSmokeDao(appDatabase: AppDatabase): SmokeDao {
        return appDatabase.smokeDao()
    }
}