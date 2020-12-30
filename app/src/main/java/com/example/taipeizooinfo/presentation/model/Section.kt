package com.example.taipeizooinfo.presentation.model

import com.google.gson.annotations.SerializedName

data class Section(
     val id : Long,
     val picUrl : String?,
     val info : String?,
     val number : Int?,
     val category : String?,
     val name : String?,
     val memo : String?,
     val sectionLink : String?
)