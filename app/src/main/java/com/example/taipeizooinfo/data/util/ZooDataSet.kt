package com.example.taipeizooinfo.data.util



sealed class ZooDataSet(val id:Int, val dataSetCode:String){
    object SectionDataSet:ZooDataSet(id = 0,dataSetCode = "5a0e5fbb-72f8-41c6-908e-2fb25eff9b8a")
    object PlantDataSet:ZooDataSet(id = 1,dataSetCode = "f18de02f-b6c9-47c0-8cda-50efad621c14")
}