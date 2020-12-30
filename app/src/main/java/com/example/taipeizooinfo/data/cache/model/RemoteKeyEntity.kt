package com.example.taipeizooinfo.data.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="remote_keys",primaryKeys = ["id","type"])
data class RemoteKeyEntity(
    @ColumnInfo(name = "id",index = true)
    val itemId:Long,
    @ColumnInfo(name = "type",index = true)
    val type:Int, //  0:sections , 1: plants
    val prevKey:Int?,
    val nextKey:Int?
)