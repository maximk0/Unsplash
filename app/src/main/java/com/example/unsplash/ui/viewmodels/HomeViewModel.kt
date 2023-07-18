package com.example.unsplash.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.unsplash.PAGE_SIZE
import com.example.unsplash.TAG_SEARCH
import com.example.unsplash.data.SearchPhotosDataSource
import com.example.unsplash.data.UnsplashRemoteMediator
import com.example.unsplash.data.network.UnsplashApiRepository
import com.example.unsplash.data.network.UnsplashPhotoDto
import com.example.unsplash.data.room.UnsplashDaoRepository
import com.example.unsplash.data.room.UnsplashPhotosEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val unsplashApiRepository: UnsplashApiRepository,
    private val unsplashDaoRepository: UnsplashDaoRepository,
    unsplashRemoteMediator: UnsplashRemoteMediator
) : ViewModel() {

    private val tag = "HomeViewModel"

    @OptIn(ExperimentalPagingApi::class)
    val pagedPhotos: Flow<PagingData<UnsplashPhotosEntity>> = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE),
        remoteMediator = unsplashRemoteMediator,
        pagingSourceFactory = { unsplashDaoRepository.getPagingSource() }
    ).flow.cachedIn(viewModelScope)

    private var throwable = MutableStateFlow<Throwable?>(null)
    private val queryForPagingData = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val pagedSearchPhotos: Flow<PagingData<UnsplashPhotoDto>> = queryForPagingData.flatMapLatest { query ->
        Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                SearchPhotosDataSource(
                    throwable.value,
                    unsplashApiRepository,
                    query
                )
            }
        ).flow.cachedIn(viewModelScope)
    }

    fun submitQuery(query: String) {
        queryForPagingData.value = query
        Log.d(TAG_SEARCH, "submitQuery: $query")
    }

    fun onLikeClicked(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                unsplashApiRepository.likePhoto(id)
            } catch (e: Exception) {
                Log.d(tag, e.message ?: "idk")
            }
        }
    }

    fun onDislikeClicked(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                unsplashApiRepository.unlikePhoto(id)
            } catch (e: Exception) {
                Log.d(tag, e.message ?: "idk")
            }
        }
    }

}