package com.example.taipeizooinfo.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.taipeizooinfo.data.cache.model.RemoteKeyEntity

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys:List<RemoteKeyEntity>)


    @Query("select * from remote_keys where id=:itemId and type=:type")
    suspend fun getRemoteKey(itemId:Long,type:Int):RemoteKeyEntity?


    @Query("delete from remote_keys where type=:type")
    suspend fun clearRemoteKeys(type:Int)

}