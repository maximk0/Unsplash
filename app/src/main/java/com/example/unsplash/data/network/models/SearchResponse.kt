package com.example.unsplash.data.network.models


import com.example.unsplash.data.network.UnsplashPhotoDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResponse(
 @Json(name = "results")
val results: List<UnsplashPhotoDto> = listOf()
)