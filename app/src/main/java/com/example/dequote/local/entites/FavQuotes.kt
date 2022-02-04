package com.example.dequote.local.entites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favQuotes")
data class FavQuotes(
    @PrimaryKey val favQuotesId: String,
    val author: String,
    val content: String,
)
