package com.example.unsplash.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.unsplash.BUNDLE_ID_KEY
import com.example.unsplash.databinding.FragmentCollectionsBinding
import com.example.unsplash.ui.adapters.CollectionPhotosAdapter
import com.example.unsplash.ui.adapters.ReposLoadStateAdapter
import com.example.unsplash.ui.viewmodels.CollectionPhotosViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class CollectionPhotosFragment : Fragment() {

    private var _binding: FragmentCollectionsBinding? = null
    private val binding get() = _binding!!

    private val collectionPhotosViewModel: CollectionPhotosViewModel by viewModels()

    private var collectionId: String? = null

    private val collectionPhotosAdapter: CollectionPhotosAdapter = CollectionPhotosAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCollectionsBinding.inflate(inflater, container, false)

        arguments?.let { bundle ->
            collectionId = bundle.getString(BUNDLE_ID_KEY)
            collectionId?.let { collectionPhotosViewModel.submitId(collectionId = it) }
            Log.d("CollectionPhotosFragment", "collectionId: $collectionId")
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerview.adapter = collectionPhotosAdapter.withLoadStateFooter(
            footer = ReposLoadStateAdapter { collectionPhotosAdapter.retry() }
        )

        binding.swipeRefresh.setOnRefreshListener {
            collectionPhotosAdapter.refresh()
        }

        collectionPhotosAdapter.loadStateFlow.onEach { state ->
            binding.swipeRefresh.isRefreshing = state.refresh == LoadState.Loading
        }.launchIn(viewLifecycleOwner.lifecycleScope)



        collectionId?.let {id ->
            Log.d("CollectionPhotosFragment", "id: $id")
            collectionPhotosViewModel.pagedCollectionPhotos.onEach { collectionPhoto ->
                collectionPhotosAdapter.submitData(collectionPhoto)
                Log.d("CollectionPhotosFragment", "collectionPhoto: $collectionPhoto")
            }.launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}