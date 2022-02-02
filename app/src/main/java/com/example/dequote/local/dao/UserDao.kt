package com.example.dequote.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dequote.local.entites.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewUser(user: User)

    @Query("SELECT EXISTS(SELECT * from user where userEmail = :userEmail)")
    suspend fun checkIfUserExists(userEmail: String): Boolean

    @Query("SELECT EXISTS(SELECT * from user where userEmail = :userEmail and userPassword = :userPassword)")
    suspend fun checkValidCredentialsOrNot(userEmail: String, userPassword: String): Boolean

}