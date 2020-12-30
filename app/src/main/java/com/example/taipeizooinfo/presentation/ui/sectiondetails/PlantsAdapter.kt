package com.example.taipeizooinfo.presentation.ui.sectiondetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.taipeizooinfo.databinding.PlantItemBinding
import com.example.taipeizooinfo.presentation.model.Plant
import com.example.taipeizooinfo.presentation.util.GlideManager
import com.example.taipeizooinfo.presentation.util.RecyclerItemListener
import javax.inject.Inject


class PlantsAdapter @Inject constructor(
    private val glideManager: GlideManager
) : PagingDataAdapter<Plant, PlantsAdapter.ViewHolder>(diffUtil) {

    private var _listener: RecyclerItemListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PlantItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val plant = getItem(position)
        plant?.let {
            holder.bind(it)
        }
    }

    fun addItemListener(listener: RecyclerItemListener) {
        _listener = listener
    }


    inner class ViewHolder(private val binding: PlantItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(plant: Plant) {
            binding.apply {
                glideManager.setImage(plant.pic01URL, binding.imgPlant)
            }

            binding.txtChName.text = plant.nameCh
            binding.txtKnown.text = plant.alsoKnown

            binding.root.setOnClickListener {
                _listener?.onItemClicked(plant.id, plant.nameCh)
            }
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<Plant>() {
            override fun areItemsTheSame(oldItem: Plant, newItem: Plant): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Plant, newItem: Plant): Boolean {
                return oldItem == newItem
            }
        }
    }


}