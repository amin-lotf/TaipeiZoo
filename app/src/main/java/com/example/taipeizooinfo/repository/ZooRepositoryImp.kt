package com.example.taipeizooinfo.repository

import androidx.paging.*
import com.example.taipeizooinfo.data.cache.TaipeiZooDatabase
import com.example.taipeizooinfo.data.remote.PlantRemoteMediator
import com.example.taipeizooinfo.data.remote.SectionRemoteMediator
import com.example.taipeizooinfo.data.remote.ZooApi
import com.example.taipeizooinfo.data.util.DataConstants.PAGE_LIMIT
import com.example.taipeizooinfo.data.util.ZooDataSet
import com.example.taipeizooinfo.presentation.model.Plant
import com.example.taipeizooinfo.presentation.model.Section
import com.example.taipeizooinfo.repository.util.DataState
import com.example.taipeizooinfo.repository.util.Mappers
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ZooRepositoryImp @Inject constructor(
    private val mappers: Mappers,
    private val database: TaipeiZooDatabase,
    private val zooApi: ZooApi
) : ZooRepository {

    private val TAG = "aminjoon"


    override fun getZooSections() = Pager(
        config = PagingConfig(
            pageSize = PAGE_LIMIT,
            enablePlaceholders = false
        ),
        remoteMediator = SectionRemoteMediator(
            service = zooApi,
            database = database,
            dataSet = ZooDataSet.SectionDataSet,
            sectionEntityMapper = mappers.sectionEntityMapper
        ),
        pagingSourceFactory = { database.getSectionDao().getSections() }
    ).flow.map { pagingData ->
        pagingData.mapSync { sectionEntity ->
            mappers.sectionMapper.mapToDomainModel(
                sectionEntity
            )
        }
    }

    override fun getZooPlants(forSection: String) = Pager(
        config = PagingConfig(
            pageSize = PAGE_LIMIT,
            enablePlaceholders = false
        ),
        remoteMediator = PlantRemoteMediator(
            service = zooApi,
            query = forSection,
            database = database,
            dataSet = ZooDataSet.PlantDataSet,
            plantEntityMapper = mappers.plantEntityMapper
        ),
        pagingSourceFactory = {
            database.getPlantDao().getPlants("%$forSection%")
        }
    ).flow.map { pagingData ->
        pagingData.mapSync { sectionEntity ->
            mappers.plantMapper.mapToDomainModel(
                sectionEntity
            )
        }
    }

    override suspend fun getSectionDetails(id: Long): DataState<Section> {
        val sectionEntity = database.getSectionDao().selectSection(id)
        return DataState.success(
            mappers.sectionMapper
                .mapToDomainModel(
                    sectionEntity
                )
        )
    }


    override suspend fun getPlantDetails(id: Long): DataState<Plant> {
        val plantEntity = database.getPlantDao().selectPlant(id)
        return DataState.success(
            mappers.plantMapper
                .mapToDomainModel(
                    plantEntity
                )
        )
    }
}