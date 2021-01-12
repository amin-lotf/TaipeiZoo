package com.example.taipeizooinfo.presentation.ui.sectiondetails

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.taipeizooinfo.presentation.model.Plant
import com.example.taipeizooinfo.presentation.model.Section
import com.example.taipeizooinfo.presentation.ui.common.BaseViewModel
import com.example.taipeizooinfo.repository.ZooRepository
import com.example.taipeizooinfo.repository.util.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch



@OptIn(ExperimentalCoroutinesApi::class)
class SectionViewModel @ViewModelInject constructor(
    private val repository: ZooRepository,
) : BaseViewModel() {

    private val _sectionId = MutableSharedFlow<Long>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    private val _sectionName = MutableSharedFlow<String>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private val _sectionDetails = MutableLiveData<DataState<Section>>()
    private val _plants = MutableLiveData<PagingData<Plant>>()

    val sectionDetails: LiveData<DataState<Section>>
        get() = _sectionDetails

    val plants: LiveData<PagingData<Plant>>
        get() = _plants

    init {

        viewModelScope.launch {
            _sectionId
                .distinctUntilChanged()
                .collect { id ->
                    _sectionDetails.postValue(
                        repository.getSectionDetails(id)
                    )
                }
        }

        viewModelScope.launch {
            _sectionName
                .distinctUntilChanged()
                .flatMapLatest { forSection ->
                    repository
                        .getZooPlants(forSection)
                        .cachedIn(viewModelScope)
                }.onCompletion {exception->
                    exception?.printStackTrace()
                    showErrorMessage(exception!=null)
                }.catch { exception->
                    exception.printStackTrace()
                   showErrorMessage(true)
                }
                .collect { pagingData ->
                    _plants.postValue(pagingData)
                }
        }

    }

    fun getSectionDetails(sectionId: Long, sectionName: String?) {

        if (sectionId != 0L) {
            _sectionId.tryEmit(sectionId)
        }
        sectionName?.let {
            _sectionName.tryEmit(it)
        }
    }
}