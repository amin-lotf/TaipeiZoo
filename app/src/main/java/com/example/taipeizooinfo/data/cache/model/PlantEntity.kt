package com.example.taipeizooinfo.data.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "zoo_plants")
data class PlantEntity(
    @PrimaryKey
    val id: Long,
    val nameLatin: String?,
    val location: String?,
    val summary: String?,
    val pic02URL: String?,
    val pic01URL: String?,
    val nameCh: String?,
    val keywords: String?,
    val pic03URL: String?,
    val alsoKnown: String?,
    val pic04ALT: String?,
    val nameEn: String?,
    val brief: String?,
    val pic04URL: String?,
    val feature: String?,
    val pic02ALT: String?,
    val family: String?,
    val pic03ALT: String?,
    val pic01ALT: String?,
    val genus: String?,
    val functionApplication: String?,
    val update: String?,
    )