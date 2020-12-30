package com.example.taipeizooinfo.presentation.model

import com.example.taipeizooinfo.data.cache.model.PlantEntity
import com.example.taipeizooinfo.presentation.util.DomainMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlantMapper @Inject constructor() :DomainMapper<Plant,PlantEntity> {
    override fun mapToDomainModel(entity: PlantEntity): Plant {
        return Plant(
            id = entity.id,
            nameLatin = entity.nameLatin,
            location = entity.location,
            summary = entity.summary,
            pic02URL = entity.pic02URL,
            pic01URL = entity.pic01URL,
            nameCh = entity.nameCh,
            keywords = entity.keywords,
            pic03URL = entity.pic03URL,
            alsoKnown =entity.alsoKnown,
            pic04ALT = entity.pic04ALT,
            nameEn = entity.nameEn,
            brief = entity.brief,
            pic04URL = entity.pic04URL,
            feature = entity.feature,
            pic02ALT = entity.pic02ALT,
            family = entity.family,
            pic03ALT = entity.pic03ALT,
            pic01ALT = entity.pic01ALT,
            genus = entity.genus,
            functionApplication = entity.functionApplication,
            update = entity.update
        )
    }

    override fun mapFromDomainModel(domainModel: Plant): PlantEntity {
        // No usage in this project
        throw NotImplementedError("Not Implemented")
    }

}