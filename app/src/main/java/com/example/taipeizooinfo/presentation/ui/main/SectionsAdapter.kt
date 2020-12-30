package com.example.taipeizooinfo.presentation.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.taipeizooinfo.databinding.SectionItemBinding
import com.example.taipeizooinfo.presentation.model.Section
import com.example.taipeizooinfo.presentation.util.GlideManager
import com.example.taipeizooinfo.presentation.util.RecyclerItemListener
import javax.inject.Inject


class SectionsAdapter @Inject constructor(
    private val glideManager: GlideManager
) : PagingDataAdapter<Section, SectionsAdapter.ViewHolder>(diffUtil) {

    private var _listener: RecyclerItemListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SectionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val section = getItem(position)
        section?.let {
            holder.bind(it)
        }
    }

    fun addItemListener(listener: RecyclerItemListener) {
        _listener = listener
    }

    inner class ViewHolder(private val binding: SectionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(section: Section) {
            binding.apply {
                glideManager.setImage(section.picUrl, binding.imgSection)
            }
            binding.txtSectionName.text = section.name
            binding.txtSectionInfo.text = section.info
            binding.txtSectionMemo.text = section.memo
            binding.root.setOnClickListener {
                _listener?.onItemClicked(section.id, section.name)
            }
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<Section>() {
            override fun areItemsTheSame(oldItem: Section, newItem: Section): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Section, newItem: Section): Boolean {
                return oldItem == newItem
            }
        }
    }


}