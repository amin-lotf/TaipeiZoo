package com.example.taipeizooinfo.data.cache.model

import com.example.taipeizooinfo.data.remote.model.section.SectionDto
import com.example.taipeizooinfo.data.util.EntityDtoMapper
import com.example.taipeizooinfo.repository.util.convertToHttps
import java.lang.NullPointerException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SectionEntityMapper @Inject constructor():EntityDtoMapper<SectionEntity,SectionDto> {

    override fun mapEntityToDto(entity: SectionEntity): SectionDto {
        //No usage in this project
        throw NotImplementedError()
    }

    override fun mapDtoToEntity(dto: SectionDto): SectionEntity {
        return SectionEntity(
            id = dto.id?.toLong()?:throw NullPointerException("Id cannot be null"),
            picUrl = dto.picUrl?.convertToHttps(),
            info = dto.info,
            number = dto.number,
            category = dto.category,
            name = dto.name,
            memo = dto.memo,
            sectionLink = dto.sectionLink?.convertToHttps()
        )
    }
}