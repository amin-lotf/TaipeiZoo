package com.example.taipeizooinfo.data.remote

import com.example.taipeizooinfo.data.remote.model.plant.PlantResponse
import com.example.taipeizooinfo.data.remote.model.section.SectionResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ZooApi {

    @GET("{dataSetCode}")
    suspend fun getSections(
        @Path("dataSetCode") dataSetCode:String,
        @Query("scope") scope:String="resourceAquire",
        @Query("offset") offset:Int,
        @Query("limit") limit:Int
    ):Response<SectionResponse>

    @GET("{dataSetCode}")
    suspend fun getPlants(
        @Path("dataSetCode") dataSetCode:String,
        @Query("q") query:String,
        @Query("scope") scope:String="resourceAquire",
        @Query("offset") offset:Int,
        @Query("limit") limit:Int
    ):Response<PlantResponse>

}