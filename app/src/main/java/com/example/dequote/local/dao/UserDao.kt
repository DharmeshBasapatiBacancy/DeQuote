package com.example.dequote.local.dao

import androidx.room.*
import com.example.dequote.local.entites.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewUser(user: User)

    @Query("SELECT EXISTS(SELECT * from user where userEmail = :userEmail)")
    suspend fun checkIfUserExists(userEmail: String): Boolean

    @Query("SELECT EXISTS(SELECT * from user where userEmail = :userEmail and userPassword = :userPassword)")
    suspend fun checkValidCredentialsOrNot(userEmail: String, userPassword: String): Boolean

    @Query("SELECT * FROM user where userEmail = :userEmail")
    suspend fun getUserDetails(userEmail: String): User

    @Query("UPDATE user SET userName = :userName, userPassword = :userPassword  where userEmail = :userEmail")
    suspend fun updateUserDetails(userName: String, userEmail: String, userPassword: String)


}