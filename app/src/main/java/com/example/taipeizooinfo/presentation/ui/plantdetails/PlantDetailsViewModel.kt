package com.example.taipeizooinfo.presentation.ui.plantdetails

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.taipeizooinfo.presentation.model.Plant
import com.example.taipeizooinfo.presentation.ui.common.BaseViewModel
import com.example.taipeizooinfo.repository.ZooRepository
import com.example.taipeizooinfo.repository.util.DataState
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PlantDetailsViewModel @ViewModelInject constructor(
    private val repository: ZooRepository
) : BaseViewModel() {


    private val _plantId = MutableSharedFlow<Long>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private val _plantDetails = MutableLiveData<DataState<Plant>>()

    val plantDetails: LiveData<DataState<Plant>>
        get() = _plantDetails

    init {
        viewModelScope.launch {
            _plantId
                .collect {
                    val plantDetails = repository.getPlantDetails(it)
                    _plantDetails.postValue(plantDetails)
                }
        }
    }


    fun getPlantDetails(plantId:Long){
        if (plantId!=0L){
            _plantId.tryEmit(plantId)
        }
    }


}