package com.murat.dailysmoking.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Murat
 */

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true) val userId: Long = 0,
    @ColumnInfo(name = "isPrimeMember") var isPrimeMember: Boolean = false,
    @ColumnInfo(name = "userName") var userName: String?,
    @ColumnInfo(name = "singleCigarettePrice")  var singleCigarettePrice: Double? = 00.00,
    @ColumnInfo(name = "currency")  var currency: String? = "$",
    @ColumnInfo(name = "packagePrice")  var packagePrice: Double = 00.00,
    @ColumnInfo(name = "packageContent")  var packageContent: Int = 20
)