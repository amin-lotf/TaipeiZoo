package com.example.taipeizooinfo.data.cache

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.taipeizooinfo.data.cache.model.PlantEntity
import com.example.taipeizooinfo.data.cache.model.SectionEntity

@Dao
interface PlantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(plants:List<PlantEntity>)

    @Query("select * from zoo_plants where location like :location ")
    fun getPlants(location:String): PagingSource<Int,PlantEntity>

    @Query("delete from zoo_plants  ")
    suspend fun clearPlants()

    @Query("select * from zoo_plants where id=:id")
    suspend fun selectPlant(id:Long):PlantEntity

}