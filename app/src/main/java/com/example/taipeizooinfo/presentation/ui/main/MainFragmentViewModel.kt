package com.example.taipeizooinfo.presentation.ui.main

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.taipeizooinfo.presentation.model.Section
import com.example.taipeizooinfo.presentation.ui.common.BaseViewModel
import com.example.taipeizooinfo.repository.ZooRepository
import com.example.taipeizooinfo.repository.util.DataState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


@OptIn(FlowPreview::class)
class MainFragmentViewModel @ViewModelInject constructor(
    private val repository: ZooRepository,
) : BaseViewModel() {

    private val _sections = MutableLiveData<PagingData<Section>>()

    val sections: LiveData<PagingData<Section>>
        get() = _sections

    init {
        viewModelScope.launch {
            repository.getZooSections()
                .cachedIn(viewModelScope)
                .debounce(1000) //To avoid multiple refresh requests in short time
                .onCompletion { exception ->
                    exception?.printStackTrace()
                    showErrorMessage(exception != null)
                }.catch { exception ->
                    exception.printStackTrace()
                    showErrorMessage(true)
                }
                .collectLatest {
                    _sections.postValue(it)
                }
        }
    }

}