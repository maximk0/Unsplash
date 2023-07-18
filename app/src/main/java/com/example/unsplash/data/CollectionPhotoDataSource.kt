package com.example.unsplash.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.unsplash.TAG_LOAD_PHOTOS
import com.example.unsplash.data.network.UnsplashApiRepository
import com.example.unsplash.data.network.models.CollectionPhotosDto
import kotlinx.coroutines.flow.MutableStateFlow

class CollectionPhotosDataSource(
    private val throwable: MutableStateFlow<Throwable?>,
    private val unsplashApiRepository: UnsplashApiRepository,
    private val collectionId: String
) : PagingSource<Int, CollectionPhotosDto>() {

    private val firstPage = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CollectionPhotosDto> {
        val page = params.key ?: firstPage
        return kotlin.runCatching {
            unsplashApiRepository.getCollectionPhotos(collectionId, page)
        }.fold(
            onSuccess = {
                Log.d(TAG_LOAD_PHOTOS, "CollectionPhotosDataSource: $it")
                LoadResult.Page(
                    data = it,
                    prevKey = null,
                    nextKey = if (it.isEmpty()) null else page + 1
                )
            },
            onFailure = {
                Log.d(TAG_LOAD_PHOTOS, "CollectionPhotosDataSource error: ${it.message}")
                throwable.value = it
                LoadResult.Error(it)
            })
    }

    override fun getRefreshKey(state: PagingState<Int, CollectionPhotosDto>): Int = firstPage

}