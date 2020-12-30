package com.example.taipeizooinfo.presentation.ui.common


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taipeizooinfo.repository.util.DataState
import com.example.taipeizooinfo.repository.util.Event
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch


@OptIn(FlowPreview::class)
abstract class BaseViewModel:ViewModel() {

    private val _errorState=MutableLiveData<Event<DataState.Failed<*>>>()


    private val _showErrorMessage = MutableStateFlow(false)

    init {
        viewModelScope.launch {
            _showErrorMessage
                .debounce(20)
                .distinctUntilChangedBy { it }
                .collectLatest { shouldCheck ->
                    if (shouldCheck){
                        _errorState.postValue(
                            Event(
                                DataState.failed<Nothing>("Error getting data")
                            )
                        )
                    }
                }
        }
    }

    val errorState:LiveData<Event<DataState.Failed<*>>>
    get() = _errorState

    fun showErrorMessage(showError:Boolean) {

        if (showError!=_showErrorMessage.value){
            _showErrorMessage.value= showError
        }
    }




}