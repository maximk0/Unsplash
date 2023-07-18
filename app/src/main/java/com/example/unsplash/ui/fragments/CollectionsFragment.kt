package com.example.unsplash.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.unsplash.BUNDLE_ID_KEY
import com.example.unsplash.R
import com.example.unsplash.data.network.models.CollectionsDto
import com.example.unsplash.databinding.FragmentCollectionsBinding
import com.example.unsplash.ui.adapters.CollectionsAdapter
import com.example.unsplash.ui.adapters.ReposLoadStateAdapter
import com.example.unsplash.ui.viewmodels.CollectionsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class CollectionsFragment : Fragment() {

    private var _binding: FragmentCollectionsBinding? = null
    private val binding get() = _binding!!

    private val collectionsViewModel: CollectionsViewModel by viewModels()

    private val collectionsAdapter: CollectionsAdapter = CollectionsAdapter(
        onClickPhoto = { collection -> onItemClicked(collection) },
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCollectionsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerview.adapter = collectionsAdapter.withLoadStateFooter(
            footer = ReposLoadStateAdapter { collectionsAdapter.retry() }
        )

        binding.swipeRefresh.setOnRefreshListener {
            collectionsAdapter.refresh()
        }

        collectionsAdapter.loadStateFlow.onEach { state ->
            binding.swipeRefresh.isRefreshing = state.refresh == LoadState.Loading
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        collectionsViewModel.pagedCollections.onEach {
            collectionsAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onItemClicked(collection: CollectionsDto) {
        val bundle = Bundle()
        bundle.putString(BUNDLE_ID_KEY, collection.id)
        findNavController().navigate(R.id.action_CollectionsFragment_to_CollectionPhotosFragment, bundle)
    }
}