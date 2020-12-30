package com.example.taipeizooinfo.presentation.ui.main

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taipeizooinfo.R
import com.example.taipeizooinfo.databinding.FragmentMainBinding
import com.example.taipeizooinfo.presentation.ui.common.BaseFragment
import com.example.taipeizooinfo.presentation.ui.common.ZooLoadStateAdapter
import com.example.taipeizooinfo.presentation.util.RecyclerItemListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainFragment : BaseFragment(R.layout.fragment_main) {

    private val TAG = "Aminjoon"

    val viewModel by viewModels<MainFragmentViewModel>()

    @Inject
    lateinit var sectionsAdapter: SectionsAdapter



    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)
        binding.collapsingToolbar.title=getString(R.string.app_name)

        setupAdapter()
        setupObservers()

        binding.layoutRefresh.setOnRefreshListener {
            sectionsAdapter.refresh()
            binding.layoutRefresh.isRefreshing=false
        }

    }

    override fun subscribeErrorObserver() {
        viewModel.errorState.observe(viewLifecycleOwner) {
            onErrorReceived(it)
        }
    }

    private fun setupObservers() {
        viewModel.sections.observe(viewLifecycleOwner) { pagingData ->
            sectionsAdapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
        }
    }

    private fun setupAdapter() {
        sectionsAdapter.addItemListener(object:RecyclerItemListener{
            override fun onItemClicked(itemId: Long, itemName: String?) {
                val action = MainFragmentDirections
                    .actionMainFragmentToSectionDetailsFragment(
                        sectionId = itemId,
                        sectionName = itemName
                    )
                findNavController().navigate(action)
            }
        })



        sectionsAdapter.let { adapter ->
            binding.recyclerSections.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                this.adapter = adapter.withLoadStateHeaderAndFooter(
                    header = ZooLoadStateAdapter { adapter.retry() },
                    footer = ZooLoadStateAdapter { adapter.retry() }
                )

                setHasFixedSize(true)
            }
        }

        sectionsAdapter.addLoadStateListener { loadState ->

            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading


            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
                ?: loadState.refresh as? LoadState.Error
                ?: loadState.source.refresh as? LoadState.Error


            viewModel.showErrorMessage(errorState!=null)

        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}