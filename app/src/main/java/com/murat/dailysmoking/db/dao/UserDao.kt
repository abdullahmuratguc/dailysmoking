package com.murat.dailysmoking.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.murat.dailysmoking.db.entity.User

/**
 * Created by Murat
 */
@Dao
interface UserDao {
    @Query("SELECT isPrimeMember FROM user")
    fun isUserPrime(): Boolean

    @Query("SELECT singleCigarettePrice FROM user")
    fun getSingleCigarettePrice(): Double?

    @Query("SELECT currency FROM user")
    fun getCurrency(): String?

    @Query("SELECT * FROM user LIMIT 1")
    fun getUser(): User?

    @Insert
    suspend fun saveUser(user: User)

    @Update
    suspend fun update(user: User)
}