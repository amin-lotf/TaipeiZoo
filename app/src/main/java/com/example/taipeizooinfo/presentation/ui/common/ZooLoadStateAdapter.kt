package com.example.taipeizooinfo.presentation.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taipeizooinfo.databinding.LoadStateViewItemBinding

class ZooLoadStateAdapter(private val retry:()->Unit) : LoadStateAdapter<ZooLoadStateAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        val binding =
            LoadStateViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
       holder.bind(loadState)
    }


    inner class ViewHolder(val binding: LoadStateViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState) {
            binding.imgRetry.isVisible=loadState !is LoadState.Loading
            binding.progressBar.isVisible=loadState is LoadState.Loading

            binding.imgRetry.setOnClickListener{
                retry()
            }
        }
    }


}