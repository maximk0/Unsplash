package com.example.unsplash.data.network


import com.example.unsplash.data.network.models.Links
import com.example.unsplash.data.network.models.Urls
import com.example.unsplash.data.network.models.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UnsplashPhotoDto(
    @Json(name = "id")
    val id: String? = null, // anKB1EqgfRE
    @Json(name = "urls")
    val urls: Urls? = null,
    @Json(name = "links")
    val links: Links? = null,
    @Json(name = "likes")
    val likes: Int? = null, // 24
    @Json(name = "liked_by_user")
    val likedByUser: Boolean? = null, // false
    @Json(name = "current_user_collections")
    val currentUserCollections: List<Any>? = null,
    @Json(name = "user")
    val user: User? = null
)