package com.example.unsplash.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import com.example.unsplash.BUNDLE_ID_KEY
import com.example.unsplash.PARCELABLE_KEY
import com.example.unsplash.R
import com.example.unsplash.TAG_SEARCH
import com.example.unsplash.data.network.UnsplashPhotoDto
import com.example.unsplash.data.room.UnsplashPhotosEntity
import com.example.unsplash.databinding.FragmentHomeBinding
import com.example.unsplash.ui.adapters.PhotosAdapter
import com.example.unsplash.ui.adapters.SearchPhotosAdapter
import com.example.unsplash.ui.adapters.ReposLoadStateAdapter
import com.example.unsplash.ui.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeFragment : Fragment(), SearchView.OnQueryTextListener  {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val photoViewModel: HomeViewModel by viewModels()

    private val photosAdapter = PhotosAdapter(
        onClickPhoto = { unsplashPhoto -> onItemClicked(unsplashPhoto) },
        onLikeClicked = { photoId -> photoViewModel.onLikeClicked(id = photoId) },
        onDislikeClicked = { photoId -> photoViewModel.onDislikeClicked(id = photoId) }
    )

    private val searchPhotosAdapter = SearchPhotosAdapter(
        onClickPhoto = { searchResponse -> onItemClicked(searchResponse) },
        onLikeClicked = { photoId -> photoViewModel.onLikeClicked(id = photoId) },
        onDislikeClicked = { photoId -> photoViewModel.onDislikeClicked(id = photoId) }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Add search button to toolbar
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.top_app_bar_menu, menu)
                menu.findItem(R.id.action_share).isVisible = false
                menu.findItem(R.id.action_logout).isVisible = false
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                val blackColor = resources.getColor(R.color.black, context?.theme)
                val searchView = menuItem.actionView as SearchView
                val searchEditText =
                    searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
                // change color of search text
                searchEditText.setTextColor(blackColor)
                // change color of close icon
                searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)
                    .setColorFilter(blackColor)
                when (menuItem.itemId) {
                    // вью модель с методом поиска, где аргумент - эдит текст
                    // сабмит дата в адаптер из поиска
                    R.id.action_search -> {
                        searchView.isSubmitButtonEnabled = true
                        searchView.setOnQueryTextListener(this@HomeFragment)
                        return true
                    }
                }
                return false
            }
        }, viewLifecycleOwner)

        setupRecyclerView(photosAdapter)

        photoViewModel.pagedPhotos.onEach {photos ->
            Log.d("RESPONSE_COUNT", "Submit data to adapter: $photos")
            photosAdapter.submitData(photos)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onItemClicked(photo: UnsplashPhotosEntity) {
        val bundle = Bundle()
        bundle.putParcelable(PARCELABLE_KEY, photo)
        findNavController().navigate(R.id.action_navigation_home_to_navigation_details, bundle)
    }

    private fun onItemClicked(searchResponse: UnsplashPhotoDto) {
        val bundle = Bundle()
        bundle.putString(BUNDLE_ID_KEY, searchResponse.id)
        findNavController().navigate(R.id.action_navigation_home_to_navigation_details, bundle)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query != null) {
            searchApiData(query.toString())
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    private fun setupRecyclerView(mAdapter: PagingDataAdapter<*, *>) {
        Log.d(TAG_SEARCH, "setupRecyclerView: $mAdapter")
        binding.recyclerview.adapter = mAdapter
        binding.recyclerview.adapter = mAdapter.withLoadStateFooter(
            footer = ReposLoadStateAdapter { photosAdapter.retry() }
        )

        binding.swipeRefresh.setOnRefreshListener {
            mAdapter.refresh()
        }

        mAdapter.loadStateFlow.onEach { state ->
            binding.swipeRefresh.isRefreshing = state.refresh == LoadState.Loading
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun searchApiData(query: String) {
        photoViewModel.submitQuery(query)
        photoViewModel.pagedSearchPhotos.onEach {
            Log.d(TAG_SEARCH, "Submit data to search adapter: $it")
            setupRecyclerView(searchPhotosAdapter)
            searchPhotosAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
}