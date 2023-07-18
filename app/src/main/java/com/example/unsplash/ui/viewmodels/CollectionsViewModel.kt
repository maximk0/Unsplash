package com.example.unsplash.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.unsplash.PAGE_SIZE
import com.example.unsplash.data.CollectionsDataSource
import com.example.unsplash.data.network.UnsplashApiRepository
import com.example.unsplash.data.network.models.CollectionsDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CollectionsViewModel @Inject constructor(
    private val unsplashApiRepository: UnsplashApiRepository
) : ViewModel() {

    private var throwable = MutableLiveData<Throwable?>(null)

    var pagedCollections: Flow<PagingData<CollectionsDto>> =
        Pager(config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                CollectionsDataSource(
                    throwable,
                    unsplashApiRepository
                )
            }).flow.cachedIn(viewModelScope)
}