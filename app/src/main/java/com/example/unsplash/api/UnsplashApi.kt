package com.example.unsplash.api

import com.example.unsplash.PAGE_SIZE
import com.example.unsplash.data.network.models.DetailPhotoDto
import com.example.unsplash.data.network.UnsplashPhotoDto
import com.example.unsplash.data.network.models.*
import retrofit2.http.*

const val BASE_URL = "https://api.unsplash.com/"

interface UnsplashApi {
    @GET("photos?")
    suspend fun getPhotos(
//        @Query("client_id") clientId: String = ACCESS_KEY,
//        @Header("Authorization") authHeader: String,
        @Query("page") page: Int,
        @Query("per_page") perPages: Int = PAGE_SIZE,
        @Query("order_by") orderBy: String = "popular"
    ): List<UnsplashPhotoDto>

    @POST("photos/{id}/like")
    suspend fun likePhoto(
//        @Header("Authorization") authHeader: String,
        @Path("id") photoId: String
    )

    @DELETE("photos/{id}/like")
    suspend fun unlikePhoto(
//        @Header("Authorization") authHeader: String,
        @Path("id") photoId: String
    )

    @GET("/photos/{id}")
    suspend fun getPhotoDetails(
        @Path("id") id: String
    ): DetailPhotoDto

    @GET("/search/photos")
    suspend fun searchPhotos(
//        @Header("Authorization") authHeader: String,
        @Query("page") page: Int,
        @Query("query") query: String,
        @Query("per_page") perPages: Int = PAGE_SIZE,
    ) : SearchResponse

    @GET("/collections")
    suspend fun getCollections(
        @Query("page") page: Int,
        @Query("per_page") perPages: Int = PAGE_SIZE,
    ): List<CollectionsDto>

    @GET("/collections/{id}/photos")
    suspend fun getCollectionPhotos(
        @Path("id") collectionId: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = PAGE_SIZE,
    ): List<CollectionPhotosDto>

    @GET("/me")
    suspend fun getCurrentUser(): UserDto

    @GET("/users/{username}/likes")
    suspend fun getUserLikedPhotos(
        @Path("username") username: String
    ): List<UnsplashPhotoDto>
}
