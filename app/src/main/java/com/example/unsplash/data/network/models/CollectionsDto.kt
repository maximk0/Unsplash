package com.example.unsplash.data.network.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CollectionsDto(
    @Json(name = "id")
    val id: String? = null, // 72aaifCJ0Sk
    @Json(name = "title")
    val title: String? = null, // On the Court
    @Json(name = "total_photos")
    val totalPhotos: Int? = null, // 43
    @Json(name = "private")
    val `private`: Boolean? = null, // false
    @Json(name = "links")
    val links: Links? = null,
    @Json(name = "user")
    val user: User? = null,
    @Json(name = "cover_photo")
    val coverPhoto: CoverPhoto? = null,
    @Json(name = "preview_photos")
    val previewPhotos: List<PreviewPhoto?>? = null
) {

    @JsonClass(generateAdapter = true)
    data class Links(
        @Json(name = "self")
        val self: String? = null, // https://api.unsplash.com/collections/72aaifCJ0Sk
        @Json(name = "html")
        val html: String? = null, // https://unsplash.com/collections/72aaifCJ0Sk/march-madness
        @Json(name = "photos")
        val photos: String? = null, // https://api.unsplash.com/collections/72aaifCJ0Sk/photos
        @Json(name = "related")
        val related: String? = null // https://api.unsplash.com/collections/72aaifCJ0Sk/related
    )

    @JsonClass(generateAdapter = true)
    data class CoverPhoto(
        @Json(name = "id")
        val id: String? = null, // mzt0A967scs
        @Json(name = "slug")
        val slug: String? = null, // mzt0A967scs
        @Json(name = "created_at")
        val createdAt: String? = null, // 2023-03-10T14:13:07Z
        @Json(name = "updated_at")
        val updatedAt: String? = null, // 2023-04-15T00:20:56Z
        @Json(name = "promoted_at")
        val promotedAt: String? = null, // 2022-12-20T11:44:03Z
        @Json(name = "width")
        val width: Int? = null, // 4672
        @Json(name = "height")
        val height: Int? = null, // 7008
        @Json(name = "color")
        val color: String? = null, // #262626
        @Json(name = "blur_hash")
        val blurHash: String? = null, // LNBp;G?w%2aJRkt7V@WAOuWZWARO
        @Json(name = "description")
        val description: String? = null, // Tek it married
        @Json(name = "alt_description")
        val altDescription: String? = null, // a man holding a basketball standing next to a fence
        @Json(name = "urls")
        val urls: Urls? = null,
        @Json(name = "links")
        val links: Links? = null,
        @Json(name = "likes")
        val likes: Int? = null, // 2
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
        data class Links(
            @Json(name = "self")
            val self: String? = null, // https://api.unsplash.com/photos/mzt0A967scs
            @Json(name = "html")
            val html: String? = null, // https://unsplash.com/photos/mzt0A967scs
            @Json(name = "download")
            val download: String? = null, // https://unsplash.com/photos/mzt0A967scs/download
            @Json(name = "download_location")
            val downloadLocation: String? = null // https://api.unsplash.com/photos/mzt0A967scs/download
        )

        @JsonClass(generateAdapter = true)
        data class TopicSubmissions(
            @Json(name = "arts-culture")
            val artsCulture: ArtsCulture? = null,
            @Json(name = "business-work")
            val businessWork: BusinessWork? = null,
            @Json(name = "architecture-interior")
            val architectureInterior: ArchitectureInterior? = null,
            @Json(name = "interiors")
            val interiors: Interiors? = null
        ) {
            @JsonClass(generateAdapter = true)
            data class ArtsCulture(
                @Json(name = "status")
                val status: String? = null, // approved
                @Json(name = "approved_on")
                val approvedOn: String? = null // 2021-02-05T15:53:47Z
            )

            @JsonClass(generateAdapter = true)
            data class BusinessWork(
                @Json(name = "status")
                val status: String? = null, // approved
                @Json(name = "approved_on")
                val approvedOn: String? = null // 2022-08-05T10:00:38Z
            )

            @JsonClass(generateAdapter = true)
            data class ArchitectureInterior(
                @Json(name = "status")
                val status: String? = null, // approved
                @Json(name = "approved_on")
                val approvedOn: String? = null // 2021-09-28T19:05:43Z
            )

            @JsonClass(generateAdapter = true)
            data class Interiors(
                @Json(name = "status")
                val status: String? = null, // approved
                @Json(name = "approved_on")
                val approvedOn: String? = null // 2021-09-07T13:01:39Z
            )
        }

        @JsonClass(generateAdapter = true)
        data class User(
            @Json(name = "id")
            val id: String? = null, // SF31D1ZrLVk
            @Json(name = "updated_at")
            val updatedAt: String? = null, // 2023-05-24T07:47:51Z
            @Json(name = "username")
            val username: String? = null, // yunustug
            @Json(name = "name")
            val name: String? = null, // Yunus Tuğ
            @Json(name = "first_name")
            val firstName: String? = null, // Yunus
            @Json(name = "last_name")
            val lastName: String? = null, // Tuğ
            @Json(name = "twitter_username")
            val twitterUsername: String? = null, // bumpyshot
            @Json(name = "portfolio_url")
            val portfolioUrl: String? = null, // https://bio.link/yunustug
            @Json(name = "bio")
            val bio: String? = null, // Freelance Photographer
            @Json(name = "location")
            val location: String? = null, // Turkey
            @Json(name = "links")
            val links: Links? = null,
            @Json(name = "profile_image")
            val profileImage: ProfileImage? = null,
            @Json(name = "instagram_username")
            val instagramUsername: String? = null, // yunusstug
            @Json(name = "total_collections")
            val totalCollections: Int? = null, // 24
            @Json(name = "total_likes")
            val totalLikes: Int? = null, // 13
            @Json(name = "total_photos")
            val totalPhotos: Int? = null, // 2767
            @Json(name = "accepted_tos")
            val acceptedTos: Boolean? = null, // true
            @Json(name = "for_hire")
            val forHire: Boolean? = null, // true
            @Json(name = "social")
            val social: Social? = null
        ) {
            @JsonClass(generateAdapter = true)
            data class Links(
                @Json(name = "self")
                val self: String? = null, // https://api.unsplash.com/users/yunustug
                @Json(name = "html")
                val html: String? = null, // https://unsplash.com/@yunustug
                @Json(name = "photos")
                val photos: String? = null, // https://api.unsplash.com/users/yunustug/photos
                @Json(name = "likes")
                val likes: String? = null, // https://api.unsplash.com/users/yunustug/likes
                @Json(name = "portfolio")
                val portfolio: String? = null, // https://api.unsplash.com/users/yunustug/portfolio
                @Json(name = "following")
                val following: String? = null, // https://api.unsplash.com/users/yunustug/following
                @Json(name = "followers")
                val followers: String? = null // https://api.unsplash.com/users/yunustug/followers
            )

            @JsonClass(generateAdapter = true)
            data class ProfileImage(
                @Json(name = "small")
                val small: String? = null, // https://images.unsplash.com/profile-1679003057258-f755aeb489faimage?ixlib=rb-4.0.3&crop=faces&fit=crop&w=32&h=32
                @Json(name = "medium")
                val medium: String? = null, // https://images.unsplash.com/profile-1679003057258-f755aeb489faimage?ixlib=rb-4.0.3&crop=faces&fit=crop&w=64&h=64
                @Json(name = "large")
                val large: String? = null // https://images.unsplash.com/profile-1679003057258-f755aeb489faimage?ixlib=rb-4.0.3&crop=faces&fit=crop&w=128&h=128
            )

            @JsonClass(generateAdapter = true)
            data class Social(
                @Json(name = "instagram_username")
                val instagramUsername: String? = null, // yunusstug
                @Json(name = "portfolio_url")
                val portfolioUrl: String? = null, // https://bio.link/yunustug
                @Json(name = "twitter_username")
                val twitterUsername: String? = null, // bumpyshot
                @Json(name = "paypal_email")
                val paypalEmail: Any? = null // null
            )
        }
    }

    @JsonClass(generateAdapter = true)
    data class PreviewPhoto(
        @Json(name = "id")
        val id: String? = null, // mzt0A967scs
        @Json(name = "slug")
        val slug: String? = null, // mzt0A967scs
        @Json(name = "created_at")
        val createdAt: String? = null, // 2023-03-10T14:13:07Z
        @Json(name = "updated_at")
        val updatedAt: String? = null, // 2023-04-15T00:20:56Z
        @Json(name = "blur_hash")
        val blurHash: String? = null, // LNBp;G?w%2aJRkt7V@WAOuWZWARO
        @Json(name = "urls")
        val urls: Urls? = null
    ) {
        @JsonClass(generateAdapter = true)
        data class Urls(
            @Json(name = "raw")
            val raw: String? = null, // https://plus.unsplash.com/premium_photo-1678401337531-87cede7f059a?ixlib=rb-4.0.3
            @Json(name = "full")
            val full: String? = null, // https://plus.unsplash.com/premium_photo-1678401337531-87cede7f059a?ixlib=rb-4.0.3&q=85&fm=jpg&crop=entropy&cs=srgb
            @Json(name = "regular")
            val regular: String? = null, // https://plus.unsplash.com/premium_photo-1678401337531-87cede7f059a?ixlib=rb-4.0.3&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max
            @Json(name = "small")
            val small: String? = null, // https://plus.unsplash.com/premium_photo-1678401337531-87cede7f059a?ixlib=rb-4.0.3&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max
            @Json(name = "thumb")
            val thumb: String? = null, // https://plus.unsplash.com/premium_photo-1678401337531-87cede7f059a?ixlib=rb-4.0.3&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=200&fit=max
            @Json(name = "small_s3")
            val smallS3: String? = null // https://s3.us-west-2.amazonaws.com/images.unsplash.com/small/unsplash-premium-photos-production/premium_photo-1678401337531-87cede7f059a
        )
    }
}