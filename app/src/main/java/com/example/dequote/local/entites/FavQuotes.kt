package com.example.dequote.local.entites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favQuotes")
data class FavQuotes(
    @PrimaryKey(autoGenerate = true) val favQuotesId: Int = 0,
    val author: String,
    val content: String,
)
