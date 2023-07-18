package com.example.unsplash.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.unsplash.PAGE_SIZE
import com.example.unsplash.data.CollectionPhotosDataSource
import com.example.unsplash.data.network.UnsplashApiRepository
import com.example.unsplash.data.network.models.CollectionPhotosDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class CollectionPhotosViewModel @Inject constructor(
    private val apiRepository: UnsplashApiRepository
) : ViewModel() {

    private var throwable = MutableStateFlow<Throwable?>(null)
    private val _collectionId = MutableStateFlow("")

    val pagedCollectionPhotos: Flow<PagingData<CollectionPhotosDto>> =
        Pager(config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                CollectionPhotosDataSource(
                    throwable,
                    apiRepository,
                    _collectionId.value
                )
            }).flow.cachedIn(viewModelScope)

    fun submitId(collectionId: String) {
        _collectionId.value = collectionId
    }
}