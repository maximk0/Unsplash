package com.example.unsplash.data.network.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
//        @Json(name = "id")
//        val id: String, // dG6lZyj-wvM
//        @Json(name = "updated_at")
//        val updatedAt: String, // 2023-04-29T15:33:33Z
    @Json(name = "username")
    val username: String? = null, // nate_dumlao
    @Json(name = "name")
    val name: String? = null, // Nathan Dumlao
//    @Json(name = "first_name")
//    val firstName: String, // Nathan
//    @Json(name = "last_name")
//    val lastName: String? = null, // Dumlao
//        @Json(name = "twitter_username")
//        val twitterUsername: String, // Nate_Dumlao
//        @Json(name = "portfolio_url")
//        val portfolioUrl: String, // https://www.instagram.com/kristapsungurs/
        @Json(name = "bio")
        val bio: String? = null, // I've learned and developed my talents due to the generosity of the people who came before me. Hopefully some of my content will help you do the same!  Feel free to drop a donation if you find my photos inspiring or useful!
//        @Json(name = "location")
//        val location: String, // Los Angeles
//        @Json(name = "links")
//        val links: Links,
    @Json(name = "profile_image")
    val profileImage: ProfileImage? = null,
//        @Json(name = "instagram_username")
//        val instagramUsername: String, // nate_dumlao
//        @Json(name = "total_collections")
//        val totalCollections: Int, // 23
//        @Json(name = "total_likes")
//        val totalLikes: Int, // 1237
//        @Json(name = "total_photos")
//        val totalPhotos: Int, // 4276
//        @Json(name = "accepted_tos")
//        val acceptedTos: Boolean, // true
//        @Json(name = "for_hire")
//        val forHire: Boolean, // true
//        @Json(name = "social")
//        val social: Social
)

@JsonClass(generateAdapter = true)
data class ProfileImage(
    @Json(name = "medium")
    val medium: String, // https://images.unsplash.com/profile-1495427732560-fe5248ad6638?ixlib=rb-4.0.3&crop=faces&fit=crop&w=64&h=64
//    @Json(name = "large")
//    val large: String // https://images.unsplash.com/profile-1495427732560-fe5248ad6638?ixlib=rb-4.0.3&crop=faces&fit=crop&w=128&h=128
)
