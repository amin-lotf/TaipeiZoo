package com.example.taipeizooinfo.presentation.ui.common


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

import com.example.taipeizooinfo.repository.util.DataState
import com.example.taipeizooinfo.repository.util.Event

abstract class BaseFragment constructor(
    @LayoutRes private val layoutRes: Int
) : Fragment(layoutRes) {

    fun onErrorReceived(errorState: Event<DataState.Failed<*>>){
        errorState.getContentIfNotHandled()?.let {
            Toast.makeText(requireContext(),it.message, Toast.LENGTH_SHORT).show()
        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeErrorObserver()
    }

    abstract fun subscribeErrorObserver()

}