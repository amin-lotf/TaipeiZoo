package com.example.taipeizooinfo.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.taipeizooinfo.presentation.model.Plant
import com.example.taipeizooinfo.presentation.model.Section
import com.example.taipeizooinfo.repository.util.DataState
import kotlinx.coroutines.flow.Flow

interface ZooRepository {

    fun getZooSections():Flow<PagingData<Section>>

    fun getZooPlants(forSection:String):Flow<PagingData<Plant>>

    suspend fun getSectionDetails(id:Long):DataState<Section>

   suspend fun getPlantDetails(id:Long):DataState<Plant>

}