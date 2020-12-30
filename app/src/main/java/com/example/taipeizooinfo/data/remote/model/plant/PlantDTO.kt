package com.example.taipeizooinfo.data.remote.model.plant

import com.google.gson.annotations.SerializedName

/*
Copyright (c) 2020 Kotlin Data Classes Generated from JSON powered by http://www.json2kotlin.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

For support, please feel free to contact me at https://www.linkedin.com/in/syedabsar */


data class PlantDTO (
	@SerializedName("_id") val id : Int,
	@SerializedName("F_Name_Latin") val f_Name_Latin : String?,
	@SerializedName("F_Location") val f_Location : String?,
	@SerializedName("F_Summary") val f_Summary : String?,
	@SerializedName("F_Pic02_URL") val f_Pic02_URL : String?,
	@SerializedName("F_Pic01_URL") val f_Pic01_URL : String?,
	@SerializedName("﻿F_Name_Ch") val F_Name_Ch : String?,
	@SerializedName("F_Keywords") val f_Keywords : String?,
	@SerializedName("F_Pic03_URL") val f_Pic03_URL : String?,
	@SerializedName("F_AlsoKnown") val f_AlsoKnown : String?,
	@SerializedName("F_Pic04_ALT") val f_Pic04_ALT : String?,
	@SerializedName("F_Name_En") val f_Name_En : String?,
	@SerializedName("F_Brief") val f_Brief : String?,
	@SerializedName("F_Pic04_URL") val f_Pic04_URL : String?,
	@SerializedName("F_Feature") val f_Feature : String?,
	@SerializedName("F_Pic02_ALT") val f_Pic02_ALT : String?,
	@SerializedName("F_Family") val f_Family : String?,
	@SerializedName("F_Pic03_ALT") val f_Pic03_ALT : String?,
	@SerializedName("F_Pic01_ALT") val f_Pic01_ALT : String?,
	@SerializedName("F_Genus") val f_Genus : String?,
	@SerializedName("F_Function＆Application") val f_FunctionApplication : String?,
	@SerializedName("F_Update") val f_Update : String?,

)