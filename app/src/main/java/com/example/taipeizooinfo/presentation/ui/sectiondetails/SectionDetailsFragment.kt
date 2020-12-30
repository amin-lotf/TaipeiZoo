package com.example.taipeizooinfo.presentation.ui.sectiondetails

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taipeizooinfo.R
import com.example.taipeizooinfo.databinding.FragmentSectionDetailsBinding
import com.example.taipeizooinfo.presentation.model.Section
import com.example.taipeizooinfo.presentation.ui.common.BaseFragment
import com.example.taipeizooinfo.presentation.ui.common.ZooLoadStateAdapter
import com.example.taipeizooinfo.repository.util.DataState
import com.example.taipeizooinfo.presentation.util.GlideManager
import com.example.taipeizooinfo.presentation.util.RecyclerItemListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SectionDetailsFragment : BaseFragment(R.layout.fragment_section_details) {

    @Inject
    lateinit var plantsAdapter: PlantsAdapter

    @Inject
    lateinit var glideManager: GlideManager

    private val viewModel by viewModels<SectionViewModel>()
    private val args: SectionDetailsFragmentArgs by navArgs()
    private var _binding: FragmentSectionDetailsBinding? = null
    private val binding: FragmentSectionDetailsBinding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSectionDetailsBinding.bind(view)

        ViewCompat.setTransitionName(binding.imgAppbar, "detail_view")

        val sectionId = args.sectionId
        val sectionName = args.sectionName
        setupToolbar(sectionName)

        viewModel.getSectionDetails(sectionId, sectionName)
        subscribeObservers()
        setupPlantsAdapter()

        binding.layoutSwipe.setOnRefreshListener {
            viewModel.getSectionDetails(sectionId, sectionName)
            binding.layoutSwipe.isRefreshing = false
        }
    }

    private fun subscribeObservers() {
        viewModel.sectionDetails.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    val details = dataState.data
                    showSectionDetails(details)
                }
                is DataState.Failed -> {
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun setupToolbar(sectionName: String?) {
        val title = sectionName ?: getString(R.string.app_name)
        binding.collapsingToolbar.title = title
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }


    private fun setupPlantsAdapter() {
        plantsAdapter.addItemListener(object : RecyclerItemListener {
            override fun onItemClicked(itemId: Long, itemName: String?) {
                val action = SectionDetailsFragmentDirections
                    .actionSectionDetailsFragmentToPlantDetailsFragment(
                        plantId = itemId,
                        plantName = itemName
                    )
                findNavController().navigate(action)
            }
        })

        binding.recyclerPlants.apply {
            isNestedScrollingEnabled = true
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            this.adapter = plantsAdapter.withLoadStateHeaderAndFooter(
                header = ZooLoadStateAdapter { plantsAdapter.retry() },
                footer = ZooLoadStateAdapter { plantsAdapter.retry() }
            )
        }

        plantsAdapter.addLoadStateListener { loadState ->
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
                ?: loadState.refresh as? LoadState.Error
                ?: loadState.source.refresh as? LoadState.Error

            viewModel.showErrorMessage(errorState != null)
        }

        viewModel.plants.observe(viewLifecycleOwner) {
            plantsAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

    }


    private fun showSectionDetails(section: Section) {
        binding.apply {
            glideManager.setImage(section.picUrl, imgAppbar)
            binding.txtSectionInfo.text = section.info
            binding.txtSectionMemo.text = section.memo
        }

        section.sectionLink?.let { link ->
            binding.txtSectionLink.setOnClickListener {
                val action =
                    SectionDetailsFragmentDirections.actionSectionDetailsFragmentToWebFragment(link)
                findNavController().navigate(action)
            }
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