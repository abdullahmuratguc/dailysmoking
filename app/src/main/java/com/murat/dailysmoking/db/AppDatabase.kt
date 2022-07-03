package com.murat.dailysmoking.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.murat.dailysmoking.db.dao.SmokeDao
import com.murat.dailysmoking.db.dao.UserDao
import com.murat.dailysmoking.db.entity.Smoke
import com.murat.dailysmoking.db.entity.User
import com.murat.dailysmoking.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Provider

/**
 * Created by Murat
 */
@Database(entities = [User::class, Smoke::class], version = 11)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun smokeDao(): SmokeDao

    class Callback @Inject constructor(
        private val database: Provider<AppDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback()
}