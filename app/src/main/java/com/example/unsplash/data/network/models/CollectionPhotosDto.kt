package com.example.unsplash.data.network.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class CollectionPhotosDto(
    @Json(name = "id")
    val id: String? = null, // xCmvrpzctaQ
    @Json(name = "slug")
    val slug: String? = null, // xCmvrpzctaQ
    @Json(name = "created_at")
    val createdAt: String? = null, // 2016-01-10T20:30:11Z
    @Json(name = "updated_at")
    val updatedAt: String? = null, // 2023-05-24T07:00:27Z
    @Json(name = "promoted_at")
    val promotedAt: String? = null, // 2016-01-10T20:30:11Z
    @Json(name = "width")
    val width: Int? = null, // 7360
    @Json(name = "height")
    val height: Int? = null, // 4912
    @Json(name = "color")
    val color: String? = null, // #d9d9d9
    @Json(name = "blur_hash")
    val blurHash: String? = null, // LSKw|N~D0MxpNsaf%NR%RPROivt7
    @Json(name = "description")
    val description: String? = null, // Couple working at home
    @Json(name = "alt_description")
    val altDescription: String? = null, // man and woman holding hands together in walkway during daytime
    @Json(name = "urls")
    val urls: Urls? = null,
    @Json(name = "links")
    val links: Links? = null,
    @Json(name = "likes")
    val likes: Int? = null, // 810
    @Json(name = "liked_by_user")
    val likedByUser: Boolean? = null, // false
    @Json(name = "current_user_collections")
    val currentUserCollections: List<Any?>? = null,
    @Json(name = "sponsorship")
    val sponsorship: Any? = null, // null
    @Json(name = "topic_submissions")
    val topicSubmissions: TopicSubmissions? = null,
    @Json(name = "user")
    val user: User? = null
) {
    @JsonClass(generateAdapter = true)
    data class Urls(
        @Json(name = "raw")
        val raw: String? = null, // https://images.unsplash.com/photo-1452457807411-4979b707c5be?ixid=M3w0Mzg5MTh8MHwxfGNvbGxlY3Rpb258MXwyMDZ8fHx8fDJ8fDE2ODQ5NjU2MzV8&ixlib=rb-4.0.3
        @Json(name = "full")
        val full: String? = null, // https://images.unsplash.com/photo-1452457807411-4979b707c5be?crop=entropy&cs=srgb&fm=jpg&ixid=M3w0Mzg5MTh8MHwxfGNvbGxlY3Rpb258MXwyMDZ8fHx8fDJ8fDE2ODQ5NjU2MzV8&ixlib=rb-4.0.3&q=85
        @Json(name = "regular")
        val regular: String? = null, // https://images.unsplash.com/photo-1452457807411-4979b707c5be?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w0Mzg5MTh8MHwxfGNvbGxlY3Rpb258MXwyMDZ8fHx8fDJ8fDE2ODQ5NjU2MzV8&ixlib=rb-4.0.3&q=80&w=1080
        @Json(name = "small")
        val small: String? = null, // https://images.unsplash.com/photo-1452457807411-4979b707c5be?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w0Mzg5MTh8MHwxfGNvbGxlY3Rpb258MXwyMDZ8fHx8fDJ8fDE2ODQ5NjU2MzV8&ixlib=rb-4.0.3&q=80&w=400
        @Json(name = "thumb")
        val thumb: String? = null, // https://images.unsplash.com/photo-1452457807411-4979b707c5be?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w0Mzg5MTh8MHwxfGNvbGxlY3Rpb258MXwyMDZ8fHx8fDJ8fDE2ODQ5NjU2MzV8&ixlib=rb-4.0.3&q=80&w=200
        @Json(name = "small_s3")
        val smallS3: String? = null // https://s3.us-west-2.amazonaws.com/images.unsplash.com/small/photo-1452457807411-4979b707c5be
    )

    @JsonClass(generateAdapter = true)
    data class Links(
        @Json(name = "self")
        val self: String? = null, // https://api.unsplash.com/photos/xCmvrpzctaQ
        @Json(name = "html")
        val html: String? = null, // https://unsplash.com/photos/xCmvrpzctaQ
        @Json(name = "download")
        val download: String? = null, // https://unsplash.com/photos/xCmvrpzctaQ/download?ixid=M3w0Mzg5MTh8MHwxfGNvbGxlY3Rpb258MXwyMDZ8fHx8fDJ8fDE2ODQ5NjU2MzV8
        @Json(name = "download_location")
        val downloadLocation: String? = null // https://api.unsplash.com/photos/xCmvrpzctaQ/download?ixid=M3w0Mzg5MTh8MHwxfGNvbGxlY3Rpb258MXwyMDZ8fHx8fDJ8fDE2ODQ5NjU2MzV8
    )

    @JsonClass(generateAdapter = true)
    data class TopicSubmissions(
        @Json(name = "work-from-home")
        val workFromHome: WorkFromHome? = null,
        @Json(name = "arts-culture")
        val artsCulture: ArtsCulture? = null
    ) {
        @JsonClass(generateAdapter = true)
        data class WorkFromHome(
            @Json(name = "status")
            val status: String? = null, // approved
            @Json(name = "approved_on")
            val approvedOn: String? = null // 2020-04-30T14:30:16Z
        )

        @JsonClass(generateAdapter = true)
        data class ArtsCulture(
            @Json(name = "status")
            val status: String? = null, // approved
            @Json(name = "approved_on")
            val approvedOn: String? = null // 2020-04-06T14:20:25Z
        )
    }

    @JsonClass(generateAdapter = true)
    data class User(
        @Json(name = "id")
        val id: String? = null, // eUO1o53muso
        @Json(name = "updated_at")
        val updatedAt: String? = null, // 2023-05-10T21:53:05Z
        @Json(name = "username")
        val username: String? = null, // crew
        @Json(name = "name")
        val name: String? = null, // Crew
        @Json(name = "first_name")
        val firstName: String? = null, // Crew
        @Json(name = "last_name")
        val lastName: String? = null, // Kraft
        @Json(name = "twitter_username")
        val twitterUsername: String? = null, // crewlabs
        @Json(name = "portfolio_url")
        val portfolioUrl: String? = null, // https://crew.co
        @Json(name = "bio")
        val bio: String? = null, // Founder and CEO @bandandroll - finest goods for human and hound. For all unsplashers a warm welcome with 15% off everything with the code: BRUNSPLASH.
        @Json(name = "location")
        val location: String? = null, // Montreal
        @Json(name = "links")
        val links: Links? = null,
        @Json(name = "profile_image")
        val profileImage: ProfileImage? = null,
        @Json(name = "instagram_username")
        val instagramUsername: String? = null, // crewlabs
        @Json(name = "total_collections")
        val totalCollections: Int? = null, // 52
        @Json(name = "total_likes")
        val totalLikes: Int? = null, // 20
        @Json(name = "total_photos")
        val totalPhotos: Int? = null, // 59
        @Json(name = "accepted_tos")
        val acceptedTos: Boolean? = null, // false
        @Json(name = "for_hire")
        val forHire: Boolean? = null, // false
        @Json(name = "social")
        val social: Social? = null
    ) {
        @JsonClass(generateAdapter = true)
        data class Links(
            @Json(name = "self")
            val self: String? = null, // https://api.unsplash.com/users/crew
            @Json(name = "html")
            val html: String? = null, // https://unsplash.com/@crew
            @Json(name = "photos")
            val photos: String? = null, // https://api.unsplash.com/users/crew/photos
            @Json(name = "likes")
            val likes: String? = null, // https://api.unsplash.com/users/crew/likes
            @Json(name = "portfolio")
            val portfolio: String? = null, // https://api.unsplash.com/users/crew/portfolio
            @Json(name = "following")
            val following: String? = null, // https://api.unsplash.com/users/crew/following
            @Json(name = "followers")
            val followers: String? = null // https://api.unsplash.com/users/crew/followers
        )

        @JsonClass(generateAdapter = true)
        data class ProfileImage(
            @Json(name = "small")
            val small: String? = null, // https://images.unsplash.com/profile-1472177817227-a0ca6d7cbea6?ixlib=rb-4.0.3&crop=faces&fit=crop&w=32&h=32
            @Json(name = "medium")
            val medium: String? = null, // https://images.unsplash.com/profile-1472177817227-a0ca6d7cbea6?ixlib=rb-4.0.3&crop=faces&fit=crop&w=64&h=64
            @Json(name = "large")
            val large: String? = null // https://images.unsplash.com/profile-1472177817227-a0ca6d7cbea6?ixlib=rb-4.0.3&crop=faces&fit=crop&w=128&h=128
        )

        @JsonClass(generateAdapter = true)
        data class Social(
            @Json(name = "instagram_username")
            val instagramUsername: String? = null, // crewlabs
            @Json(name = "portfolio_url")
            val portfolioUrl: String? = null, // https://crew.co
            @Json(name = "twitter_username")
            val twitterUsername: String? = null, // crewlabs
            @Json(name = "paypal_email")
            val paypalEmail: Any? = null // null
        )
    }
}