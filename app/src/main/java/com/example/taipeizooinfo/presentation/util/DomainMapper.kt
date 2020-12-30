package com.example.taipeizooinfo.presentation.util

interface DomainMapper<DomainModel,Entity> {
    fun mapToDomainModel(entity: Entity):DomainModel
    fun mapFromDomainModel(domainModel: DomainModel):Entity
}