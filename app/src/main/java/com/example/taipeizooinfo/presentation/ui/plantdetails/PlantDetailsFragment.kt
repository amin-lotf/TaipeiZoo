package com.example.taipeizooinfo.presentation.ui.plantdetails

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.taipeizooinfo.R
import com.example.taipeizooinfo.databinding.FragmentPlantDetailsBinding
import com.example.taipeizooinfo.presentation.model.Plant
import com.example.taipeizooinfo.presentation.ui.common.BaseFragment
import com.example.taipeizooinfo.repository.util.DataState
import com.example.taipeizooinfo.presentation.util.GlideManager
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class PlantDetailsFragment : BaseFragment(R.layout.fragment_plant_details) {

    private var _binding: FragmentPlantDetailsBinding? = null
    private val viewModel by viewModels<PlantDetailsViewModel>()
    private val args: PlantDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var glideManager: GlideManager

    private val binding: FragmentPlantDetailsBinding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPlantDetailsBinding.bind(view)
        viewModel.getPlantDetails(plantId = args.plantId)

        val plantName = args.plantName
        plantName?.let {
            showTitle(it)
        }
        setupToolbar()
        subscribeObservers()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun showTitle(plantName: String) {
        var isShow = true
        var scrollRange = -1

        binding.appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (scrollRange == -1) {
                scrollRange = appBarLayout.totalScrollRange
            }
            if (scrollRange + verticalOffset == 0) {
                binding.collapsingToolbar.title = plantName
                isShow = true
            } else if (isShow) {
                binding.collapsingToolbar.title = " "
                isShow = false
            }


        })

    }

    private fun subscribeObservers() {
        viewModel.plantDetails.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    showPlantDetails(
                        plant = dataState.data
                    )
                }
                is DataState.Failed -> viewModel.showErrorMessage(true)
            }
        }
    }

    private fun showPlantDetails(plant: Plant) {
        binding.layoutConstraint.isVisible = true
        binding.apply {
            glideManager.setImage(plant.pic01URL ?: plant.pic02URL ?: plant.pic03URL, imgAppbar)
            txtNameCh.text = plant.nameCh
            txtNameEn.text = plant.nameLatin
            txtAlias.text = plant.alsoKnown
            txtIntro.text = plant.brief
            txtIdentification.text = plant.feature
            txtFunc.text = plant.functionApplication
            txtUpdate.text = plant.update
        }
    }

    override fun subscribeErrorObserver() {
        viewModel.errorState.observe(viewLifecycleOwner) {
            onErrorReceived(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}