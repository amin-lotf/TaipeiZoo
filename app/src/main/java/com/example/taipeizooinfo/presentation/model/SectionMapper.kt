package com.example.taipeizooinfo.presentation.model

import com.example.taipeizooinfo.data.cache.model.SectionEntity
import com.example.taipeizooinfo.presentation.util.DomainMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SectionMapper @Inject constructor():DomainMapper<Section,SectionEntity> {

    override fun mapToDomainModel(entity: SectionEntity): Section {
        return Section(
            id=entity.id,
            picUrl = entity.picUrl,
            info = entity.info,
            number = entity.number,
            category = entity.category,
            name = entity.name,
            memo = entity.memo,
            sectionLink = entity.sectionLink
        )
    }

    override fun mapFromDomainModel(domainModel: Section): SectionEntity {
        // No usage in this project
        throw NotImplementedError("Not Implemented")
    }
}