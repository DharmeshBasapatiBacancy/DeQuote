package com.example.dequote.local.entites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true) val userId: Int = 0,
    val userName: String,
    val userEmail: String,
    val userPassword: String
)
