package com.example.unsplash.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.unsplash.TAG_LOAD_PHOTOS
import com.example.unsplash.data.network.UnsplashApiRepository

import com.example.unsplash.data.network.models.CollectionsDto

class CollectionsDataSource(
    private val throwable: MutableLiveData<Throwable?>,
    private val unsplashApiRepository: UnsplashApiRepository,
) : PagingSource<Int, CollectionsDto>() {

    private val firstPage = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CollectionsDto> {
        val page = params.key ?: firstPage
        return kotlin.runCatching {
            unsplashApiRepository.getCollections(page)
        }.fold(
            onSuccess = {
                Log.d(TAG_LOAD_PHOTOS, "CollectionsDataSource: $it")
                LoadResult.Page(
                    data = it,
                    prevKey = null,
                    nextKey = if (it.isEmpty()) null else page + 1
                )
            },
            onFailure = {
                Log.d(TAG_LOAD_PHOTOS, "CollectionsDataSource error: ${it.message}")
                throwable.value = it
                LoadResult.Error(it)
            })
    }

    override fun getRefreshKey(state: PagingState<Int, CollectionsDto>): Int = firstPage

}