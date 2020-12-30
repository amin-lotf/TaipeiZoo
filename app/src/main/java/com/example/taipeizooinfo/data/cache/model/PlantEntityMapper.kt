package com.example.taipeizooinfo.data.cache.model

import com.example.taipeizooinfo.data.remote.model.plant.PlantDTO
import com.example.taipeizooinfo.data.util.EntityDtoMapper
import com.example.taipeizooinfo.repository.util.convertToHttps
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlantEntityMapper @Inject constructor() :EntityDtoMapper<PlantEntity,PlantDTO> {
    override fun mapEntityToDto(entity: PlantEntity): PlantDTO {
        //No usage in this project
        throw NotImplementedError()
    }

    override fun mapDtoToEntity(dto: PlantDTO): PlantEntity {
       return PlantEntity(
           id = dto.id.toLong(),
           nameLatin = dto.f_Name_Latin,
           location = dto.f_Location,
           summary = dto.f_Summary,
           pic02URL = dto.f_Pic02_URL?.convertToHttps(),
           pic01URL = dto.f_Pic01_URL?.convertToHttps(),
           nameCh = dto.F_Name_Ch,
           keywords = dto.f_Keywords,
           pic03URL = dto.f_Pic03_URL?.convertToHttps(),
           alsoKnown = dto.f_AlsoKnown,
           pic04ALT = dto.f_Pic04_ALT,
           nameEn = dto.f_Name_En,
           brief = dto.f_Brief,
           pic04URL = dto.f_Pic04_URL?.convertToHttps(),
           feature = dto.f_Feature,
           pic02ALT = dto.f_Pic02_ALT,
           family = dto.f_Family,
           pic03ALT = dto.f_Pic03_ALT,
           pic01ALT = dto.f_Pic01_ALT,
           genus = dto.f_Genus,
           functionApplication = dto.f_FunctionApplication,
           update = dto.f_Update
       )
    }

}