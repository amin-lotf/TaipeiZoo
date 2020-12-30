package com.example.taipeizooinfo.data.util

interface EntityDtoMapper<Entity,Dto> {
    fun mapEntityToDto(entity: Entity):Dto
    fun mapDtoToEntity(dto: Dto):Entity
}