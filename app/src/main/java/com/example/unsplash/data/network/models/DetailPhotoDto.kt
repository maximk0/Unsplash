package com.example.unsplash.data.network.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DetailPhotoDto(
    @Json(name = "id")
    val id: String? = null, // ZefdTSLstl0
    @Json(name = "width")
    val width: Int? = null, // 8640
    @Json(name = "height")
    val height: Int? = null, // 5760
    @Json(name = "urls")
    val urlPhoto: Urls? = null,
    @Json(name = "links")
    val links: Links? = null,
    @Json(name = "likes")
    val likes: Int? = null, // 33
    @Json(name = "liked_by_user")
    val likedByUser: Boolean? = null, // false
    @Json(name = "user")
    val user: User? = null,
    @Json(name = "exif")
    val exif: Exif? = null,
    @Json(name = "location")
    val location: Location? = null,
    @Json(name = "tags")
    val tags: List<Tag?>? = null,
    @Json(name = "downloads")
    val downloads: Long? = null, // 2744
)

@JsonClass(generateAdapter = true)
data class Exif(
    @Json(name = "exposure_time")
    val exposureTime: String? = null,
    @Json(name = "aperture")
    val aperture: String? = null,
    @Json(name = "focal_length")
    val focalLength: String? = null,
    @Json(name = "iso")
    val iso: Int? = null,
    @Json(name = "name")
    val name: String? = null,
    @Json(name = "model")
    val model: String? = null,
    @Json(name = "make")
    val make: String? = null
)

@JsonClass(generateAdapter = true)
data class Location(
    @Json(name = "city")
    val city: String? = null, // null
    @Json(name = "country")
    val country: String? = null, // null
    @Json(name = "position")
    val position: Position? = null
)

@JsonClass(generateAdapter = true)
data class Position(
    @Json(name = "latitude")
    val latitude: Double? = null, // 0.0
    @Json(name = "longitude")
    val longitude: Double? = null // 0.0

)

@JsonClass(generateAdapter = true)
data class Tag(
//    @Json(name = "type")
//    val type: String? = null, // landing_page
    @Json(name = "title")
    val title: String? = null, // nature
)