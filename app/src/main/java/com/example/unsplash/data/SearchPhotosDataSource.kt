package com.example.unsplash.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.unsplash.TAG_SEARCH
import com.example.unsplash.data.network.UnsplashApiRepository
import com.example.unsplash.data.network.UnsplashPhotoDto

class SearchPhotosDataSource(
    private var throwable: Throwable?,
    private val unsplashApiRepository: UnsplashApiRepository,
    private val query: String,
) : PagingSource<Int, UnsplashPhotoDto>() {

    init {
        Log.d(TAG_SEARCH, "SearchPhotosDataSource init")
    }

    private val firstPage = 1

    override fun getRefreshKey(state: PagingState<Int, UnsplashPhotoDto>): Int = firstPage

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhotoDto> {
        val page = params.key ?: firstPage
        return kotlin.runCatching {
            Log.d(TAG_SEARCH, "SearchPhotosDataSource load - query: $query, page: $page")
            unsplashApiRepository.searchPhotos(query = query, page = page)
        }.fold(
            onSuccess = {
                Log.d(TAG_SEARCH, "SearchPhotosDataSource success: $it")
                LoadResult.Page(
                    data = it,
                    prevKey = null,
                    nextKey = if (it.isEmpty()) null else page + 1
                )
            },
            onFailure = {
                Log.d(TAG_SEARCH, "SearchPhotosDataSource error: ${it.message}")
                throwable = it
                LoadResult.Error(it)
            })
    }
}
