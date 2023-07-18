package com.example.unsplash.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.unsplash.TAG_ERROR_LOAD
import com.example.unsplash.data.network.UnsplashApiRepository
import com.example.unsplash.data.room.UnsplashDaoRepository
import com.example.unsplash.data.room.UnsplashPhotosEntity
import javax.inject.Inject


@OptIn(ExperimentalPagingApi::class)
class UnsplashRemoteMediator @Inject constructor(
    private val unsplashApiRepository: UnsplashApiRepository,
    private val unsplashDaoRepository: UnsplashDaoRepository
) : RemoteMediator<Int, UnsplashPhotosEntity>() {

    private var pageIndex = 0

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UnsplashPhotosEntity>
    ): MediatorResult {
        pageIndex =
            getPageIndex(loadType) ?: return MediatorResult.Success(endOfPaginationReached = true)

        val limit = state.config.pageSize

        return try {
            val photos: List<UnsplashPhotosEntity> =
                unsplashApiRepository.fetchPhotosEntitiesFromApi(pageIndex)

            if (loadType == LoadType.REFRESH)
                unsplashDaoRepository.refresh(photos)
            else {
                Log.d("LOADTYPE", loadType.name)
                unsplashDaoRepository.insert(photos)
            }

            MediatorResult.Success(endOfPaginationReached = photos.size < limit)
        } catch (e: Exception) {
            Log.d(TAG_ERROR_LOAD, e.message ?: "idk")
            MediatorResult.Error(e)
        }
    }

    private fun getPageIndex(loadType: LoadType): Int? {
        pageIndex = when (loadType) {
            LoadType.REFRESH -> {
                Log.d("LOADTYPE", "REFRESH")
                0
            }
            LoadType.PREPEND -> {
                Log.d("LOADTYPE", "PREPEND")
                return null
            }
            LoadType.APPEND -> {
                Log.d("LOADTYPE", "APPEND")
                ++pageIndex
            }
        }
        return pageIndex
    }
}