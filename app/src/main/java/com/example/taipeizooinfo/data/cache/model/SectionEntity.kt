package com.example.taipeizooinfo.data.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "zoo_sections")
data class SectionEntity(
    @PrimaryKey
    val id : Long,
    val picUrl : String?,
    val info : String?,
    val number : Int?,
    val category : String?,
    val name : String?,
    val memo : String?,
    val sectionLink : String?
)