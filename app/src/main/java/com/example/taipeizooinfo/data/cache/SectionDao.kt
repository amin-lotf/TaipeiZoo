package com.example.taipeizooinfo.data.cache

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.taipeizooinfo.data.cache.model.SectionEntity

@Dao
interface SectionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(sections:List<SectionEntity>)

    @Query("select * from zoo_sections order by id")
    fun getSections(): PagingSource<Int,SectionEntity>

    @Query("delete from zoo_sections")
    suspend fun clearSections()

    @Query("select * from zoo_sections where id=:id")
    suspend fun selectSection(id:Long):SectionEntity

}