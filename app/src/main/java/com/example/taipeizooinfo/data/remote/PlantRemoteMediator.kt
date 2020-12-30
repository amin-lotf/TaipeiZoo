package com.example.taipeizooinfo.data.remote

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.taipeizooinfo.data.cache.TaipeiZooDatabase
import com.example.taipeizooinfo.data.cache.model.*
import com.example.taipeizooinfo.data.util.DataConstants.API_STARTING_PAGE_INDEX
import com.example.taipeizooinfo.data.util.DataConstants.PAGE_LIMIT
import com.example.taipeizooinfo.data.util.ZooDataSet
import java.io.InvalidObjectException


@OptIn(ExperimentalPagingApi::class)
class PlantRemoteMediator(
    private val service: ZooApi,
    private val query:String,
    private val database: TaipeiZooDatabase,
    private val dataSet:ZooDataSet,
    private val plantEntityMapper: PlantEntityMapper
) : RemoteMediator<Int, PlantEntity>() {

    private val TAG="aminjoon"

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PlantEntity>
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeyEntity=getRemoteKeyClosestToCurrentPosition(state)
                remoteKeyEntity?.nextKey?.minus(1)?: API_STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeyEntity=getRemoteKeyForFirstItem(state)
                if (remoteKeyEntity==null){
                    throw  InvalidObjectException("RemoteKey should not be null for $loadType")
                }
                val prevKey=remoteKeyEntity.prevKey
                if (prevKey==null){
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeyEntity=getRemoteKeyForLastItem(state)
                if(remoteKeyEntity==null || remoteKeyEntity.nextKey==null){
                    throw  InvalidObjectException("RemoteKey should not be null for $loadType")
                }
                remoteKeyEntity.nextKey
            }
        }

        try {

            val response = service.getPlants(
                dataSetCode = dataSet.dataSetCode,
                query=query,
                limit = PAGE_LIMIT,
                offset = page * PAGE_LIMIT
            )


            if (response.isSuccessful && response.body() != null) {
                val plantResult = response.body()?.plantResultDTO
                val plantEntities = plantResult?.results?.map { plantEntityMapper.mapDtoToEntity(it) } ?: emptyList()

                val endOfPaginationReached=plantEntities.isEmpty()
                database.withTransaction {
                    if (loadType ==LoadType.REFRESH){
                        database.getRemoteKeyDao().clearRemoteKeys(dataSet.id)
                        database.getPlantDao().clearPlants()
                    }
                    val prevKey=if(page== API_STARTING_PAGE_INDEX) null else page-1
                    val nextKey=if(endOfPaginationReached) null else page+1

                    val keys=plantEntities.map {entity->
                        RemoteKeyEntity(entity.id,type = dataSet.id,prevKey=prevKey,nextKey=nextKey)
                    }

                    database.getRemoteKeyDao().insertAll(keys)
                    database.getPlantDao().insertAll(plantEntities)

                }
                return MediatorResult.Success(endOfPaginationReached)

            } else {
                return MediatorResult.Error(
                    Throwable(message = response.message() ?: "Error loading data")
                )
            }

        } catch (throwable: Throwable) {
            Log.d(TAG, "load: Error")
            throwable.printStackTrace()
            return MediatorResult.Error(throwable)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, PlantEntity>):RemoteKeyEntity?{

        return state.pages.lastOrNull(){it.data.isNotEmpty()}?.data?.lastOrNull()?.let {plantEntity ->
            database.getRemoteKeyDao().getRemoteKey(plantEntity.id,dataSet.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, PlantEntity>):RemoteKeyEntity?{
        return state.pages.firstOrNull(){it.data.isNotEmpty()}?.data?.firstOrNull()?.let { plantEntity ->
            database.getRemoteKeyDao().getRemoteKey(plantEntity.id,dataSet.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, PlantEntity>):RemoteKeyEntity?{
        return state.anchorPosition?.let { position->
            state.closestItemToPosition(position)?.id?.let { plantId->
                database.getRemoteKeyDao().getRemoteKey(plantId,dataSet.id)
            }
        }
    }

}