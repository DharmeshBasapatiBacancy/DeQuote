package com.example.dequote.local.entites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remoteKeys")
data class RemoteKeys(
    @PrimaryKey
    val quoteId: String,
    val prevKey: Int?,
    val nextKey: Int?
)
