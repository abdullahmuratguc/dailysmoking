package com.murat.dailysmoking.db.dao

import androidx.room.*
import com.murat.dailysmoking.db.entity.Smoke
import com.murat.dailysmoking.db.entity.User
import kotlinx.coroutines.flow.Flow
import java.util.*

/**
 * Created by Murat
 */

@Dao
interface SmokeDao {
    @Query("SELECT * FROM smoke WHERE smokedTime BETWEEN :startDate AND :endDate ORDER BY smokedTime DESC")
    fun fetchByDate(startDate: Date, endDate: Date): List<Smoke>

    @Query("SELECT SUM(singleCigarettePrice) FROM smoke WHERE smokedTime BETWEEN :startDate AND :endDate ORDER BY smokedTime DESC")
    fun fetchDailySmokePrice(startDate: Date, endDate: Date): Double?

    @Query("SELECT COUNT(*) FROM smoke WHERE smokedTime BETWEEN :startDate AND :endDate")
    fun fetchDailySmokeCount(startDate: Date, endDate: Date): Int?

    @Query("SELECT COUNT(*) FROM smoke WHERE smokedTime BETWEEN :startDate AND :endDate")
    fun fetchDailySmokeCountAsPrimitive(startDate: Date, endDate: Date): Int?

    @Query("SELECT * FROM smoke ORDER BY smokedTime DESC LIMIT 1")
    fun getLastSmoke(): Smoke?

    @Insert
    suspend fun addCigarette(smoke: Smoke)

    @Update
    suspend fun updateCigarette(cigarette: Smoke)

    @Query("SELECT smokeCurrency FROM smoke")
    fun getCurrency(): String?

    @Query("DELETE FROM smoke WHERE cigaretteId=:id")
    suspend fun deleteCigarette(id: Long)

    @Query("DELETE FROM smoke")
    fun nukeTable()
}