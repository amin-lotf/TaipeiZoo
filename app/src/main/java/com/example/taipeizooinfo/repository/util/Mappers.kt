package com.example.taipeizooinfo.repository.util

import com.example.taipeizooinfo.data.cache.model.PlantEntityMapper
import com.example.taipeizooinfo.data.cache.model.SectionEntityMapper
import com.example.taipeizooinfo.presentation.model.PlantMapper
import com.example.taipeizooinfo.presentation.model.SectionMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Mappers @Inject constructor(
    val sectionEntityMapper: SectionEntityMapper,
    val sectionMapper: SectionMapper,
    val plantEntityMapper: PlantEntityMapper,
    val plantMapper: PlantMapper
)