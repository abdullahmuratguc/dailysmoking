package com.murat.dailysmoking.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * Created by Murat
 */
@Entity(tableName = "smoke")
data class Smoke(
    @PrimaryKey(autoGenerate = true) val cigaretteId: Long = 0,
    @ColumnInfo(name = "smokedTime") val smokeTime: Date?,
    @ColumnInfo(name = "singleCigarettePrice")  val singleCigarettePrice: Double,
    @ColumnInfo(name = "smokeCurrency")  val smokeCurrency: String?
)