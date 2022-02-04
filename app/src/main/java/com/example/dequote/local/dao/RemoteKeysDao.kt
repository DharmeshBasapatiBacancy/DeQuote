package com.example.dequote.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dequote.local.entites.RemoteKeys

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRemote(list: List<RemoteKeys>)

    @Query("SELECT * FROM remoteKeys WHERE quoteId = :id")
    fun getRemoteKeys(id:String) : RemoteKeys

    @Query("DELETE FROM remoteKeys")
    fun clearAll()

}