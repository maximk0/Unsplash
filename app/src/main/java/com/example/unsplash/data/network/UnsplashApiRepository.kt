package com.example.unsplash.data.network

import android.util.Log
import com.example.unsplash.TAG_SEARCH
import com.example.unsplash.api.UnsplashApi
import com.example.unsplash.data.network.models.*
import com.example.unsplash.data.room.UnsplashPhotosEntity

import javax.inject.Inject

class UnsplashApiRepository @Inject constructor(
    private val unsplashApi: UnsplashApi
) {

    var count = 0

    private suspend fun getPhotos(page: Int): List<UnsplashPhotoDto> {
        Log.d("GET_PHOTOS", "getPhotos count: $count")
        count++
        return unsplashApi.getPhotos(page = page)
    }

    suspend fun likePhoto(photoId: String) =
        unsplashApi.likePhoto(photoId = photoId)

    suspend fun unlikePhoto(photoId: String) =
        unsplashApi.unlikePhoto(photoId = photoId)

    suspend fun getPhotoDetails(photoId: String): DetailPhotoDto {
        val response = unsplashApi.getPhotoDetails(id = photoId)
        Log.d("MarsPhotosViewModel", "Get photo details response: $response")
        return response
    }

    /* Gets entity from api response */
    suspend fun fetchPhotosEntitiesFromApi(page: Int): List<UnsplashPhotosEntity> =
        getPhotos(page)
            .map { photoDto ->
                UnsplashPhotosEntity(
                    id = photoDto.id ?: "",
                    likes = photoDto.likes ?: 0,
                    likedByUser = photoDto.likedByUser ?: false,
                    urlPhoto = photoDto.urls?.small ?: "",
                    userName = photoDto.user?.name ?: "",
                    userNickname = photoDto.user?.username ?: "",
                    userPhoto = photoDto.user?.profileImage?.medium ?: "",
                    shareLink = photoDto.links?.html ?: "",
                )
            }

    suspend fun getCollections(page: Int): List<CollectionsDto> {
        val response = unsplashApi.getCollections(page = page)
        Log.d("RESPONSE_COUNT", "Get collections response $response")
        return response
    }

    suspend fun getCollectionPhotos(id: String, page: Int): List<CollectionPhotosDto> {
        val response = unsplashApi.getCollectionPhotos(collectionId = id, page = page)
        Log.d("RESPONSE_COUNT", "Get collection photos response $response")
        return response
    }

    suspend fun getCurrentUser() = unsplashApi.getCurrentUser()

    suspend fun getUserLikedPhotos(username: String): List<UnsplashPhotoDto> {
        val response = unsplashApi.getUserLikedPhotos(username = username)
        Log.d("RESPONSE_COUNT", "Get user liked photos response $response")
        return response
    }

    suspend fun searchPhotos(query: String, page: Int): List<UnsplashPhotoDto> {
        Log.d(TAG_SEARCH, "Search photos query $query")
        val response = unsplashApi.searchPhotos(query = query, page = page).results
        Log.d(TAG_SEARCH, "Search photos response $response")
        return response
    }

}