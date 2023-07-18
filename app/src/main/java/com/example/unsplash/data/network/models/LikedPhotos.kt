package com.example.unsplash.data.network.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LikedPhotos(
    @Json(name = "id")
    val id: String? = null, // qaT_Wrv5p0s
    @Json(name = "slug")
    val slug: String? = null, // qaT_Wrv5p0s
    @Json(name = "created_at")
    val createdAt: String? = null, // 2020-11-23T16:38:38Z
    @Json(name = "updated_at")
    val updatedAt: String? = null, // 2023-05-25T06:22:51Z
    @Json(name = "promoted_at")
    val promotedAt: String? = null, // 2020-11-24T09:23:28Z
    @Json(name = "width")
    val width: Int? = null, // 6600
    @Json(name = "height")
    val height: Int? = null, // 4400
    @Json(name = "color")
    val color: String? = null, // #d9d9d9
    @Json(name = "blur_hash")
    val blurHash: String? = null, // LUKUQM4T%2NGx^j]t7tR%hX9IURj
    @Json(name = "description")
    val description: String? = null, // Flowers in spring
    @Json(name = "alt_description")
    val altDescription: String? = null, // brown and white mountain under white clouds
    @Json(name = "urls")
    val urls: Urls? = null,
    @Json(name = "links")
    val links: Links? = null,
    @Json(name = "likes")
    val likes: Int? = null, // 311
    @Json(name = "liked_by_user")
    val likedByUser: Boolean? = null, // true
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
        val raw: String? = null, // https://images.unsplash.com/photo-1606149407720-523a20a30f35?ixid=M3w0Mzg5MTh8MHwxfGFsbHx8fHx8fHwyfHwxNjg1MDQ1NjA0fA&ixlib=rb-4.0.3
        @Json(name = "full")
        val full: String? = null, // https://images.unsplash.com/photo-1606149407720-523a20a30f35?crop=entropy&cs=srgb&fm=jpg&ixid=M3w0Mzg5MTh8MHwxfGFsbHx8fHx8fHwyfHwxNjg1MDQ1NjA0fA&ixlib=rb-4.0.3&q=85
        @Json(name = "regular")
        val regular: String? = null, // https://images.unsplash.com/photo-1606149407720-523a20a30f35?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w0Mzg5MTh8MHwxfGFsbHx8fHx8fHwyfHwxNjg1MDQ1NjA0fA&ixlib=rb-4.0.3&q=80&w=1080
        @Json(name = "small")
        val small: String? = null, // https://images.unsplash.com/photo-1606149407720-523a20a30f35?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w0Mzg5MTh8MHwxfGFsbHx8fHx8fHwyfHwxNjg1MDQ1NjA0fA&ixlib=rb-4.0.3&q=80&w=400
        @Json(name = "thumb")
        val thumb: String? = null, // https://images.unsplash.com/photo-1606149407720-523a20a30f35?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w0Mzg5MTh8MHwxfGFsbHx8fHx8fHwyfHwxNjg1MDQ1NjA0fA&ixlib=rb-4.0.3&q=80&w=200
        @Json(name = "small_s3")
        val smallS3: String? = null // https://s3.us-west-2.amazonaws.com/images.unsplash.com/small/photo-1606149407720-523a20a30f35
    )

    @JsonClass(generateAdapter = true)
    data class Links(
        @Json(name = "self")
        val self: String? = null, // https://api.unsplash.com/photos/qaT_Wrv5p0s
        @Json(name = "html")
        val html: String? = null, // https://unsplash.com/photos/qaT_Wrv5p0s
        @Json(name = "download")
        val download: String? = null, // https://unsplash.com/photos/qaT_Wrv5p0s/download?ixid=M3w0Mzg5MTh8MHwxfGFsbHx8fHx8fHwyfHwxNjg1MDQ1NjA0fA
        @Json(name = "download_location")
        val downloadLocation: String? = null // https://api.unsplash.com/photos/qaT_Wrv5p0s/download?ixid=M3w0Mzg5MTh8MHwxfGFsbHx8fHx8fHwyfHwxNjg1MDQ1NjA0fA
    )

    @JsonClass(generateAdapter = true)
    data class TopicSubmissions(
        @Json(name = "wallpapers")
        val wallpapers: Wallpapers? = null,
        @Json(name = "nature")
        val nature: Nature? = null,
        @Json(name = "travel")
        val travel: Travel? = null,
        @Json(name = "textures-patterns")
        val texturesPatterns: TexturesPatterns? = null
    ) {
        @JsonClass(generateAdapter = true)
        data class Wallpapers(
            @Json(name = "status")
            val status: String? = null, // approved
            @Json(name = "approved_on")
            val approvedOn: String? = null // 2020-11-24T12:12:45Z
        )

        @JsonClass(generateAdapter = true)
        data class Nature(
            @Json(name = "status")
            val status: String? = null, // approved
            @Json(name = "approved_on")
            val approvedOn: String? = null // 2020-11-24T12:12:43Z
        )

        @JsonClass(generateAdapter = true)
        data class Travel(
            @Json(name = "status")
            val status: String? = null // rejected
        )

        @JsonClass(generateAdapter = true)
        data class TexturesPatterns(
            @Json(name = "status")
            val status: String? = null, // approved
            @Json(name = "approved_on")
            val approvedOn: String? = null // 2020-04-06T14:20:11Z
        )
    }

    @JsonClass(generateAdapter = true)
    data class User(
        @Json(name = "id")
        val id: String? = null, // z1HcFmTr2Rw
        @Json(name = "updated_at")
        val updatedAt: String? = null, // 2023-05-24T01:22:38Z
        @Json(name = "username")
        val username: String? = null, // tobbes_rd
        @Json(name = "name")
        val name: String? = null, // Tobias Rademacher
        @Json(name = "first_name")
        val firstName: String? = null, // Tobias
        @Json(name = "last_name")
        val lastName: String? = null, // Rademacher
        @Json(name = "twitter_username")
        val twitterUsername: String? = null, // neom
        @Json(name = "portfolio_url")
        val portfolioUrl: String? = null, // http://www.maartendeckers.com
        @Json(name = "bio")
        val bio: String? = null, // I'm Toby and a hobby photographer
        @Json(name = "location")
        val location: String? = null, // cologne
        @Json(name = "links")
        val links: Links? = null,
        @Json(name = "profile_image")
        val profileImage: ProfileImage? = null,
        @Json(name = "instagram_username")
        val instagramUsername: String? = null, // tobbes_rd
        @Json(name = "total_collections")
        val totalCollections: Int? = null, // 3
        @Json(name = "total_likes")
        val totalLikes: Int? = null, // 402
        @Json(name = "total_photos")
        val totalPhotos: Int? = null, // 734
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
            val self: String? = null, // https://api.unsplash.com/users/tobbes_rd
            @Json(name = "html")
            val html: String? = null, // https://unsplash.com/@tobbes_rd
            @Json(name = "photos")
            val photos: String? = null, // https://api.unsplash.com/users/tobbes_rd/photos
            @Json(name = "likes")
            val likes: String? = null, // https://api.unsplash.com/users/tobbes_rd/likes
            @Json(name = "portfolio")
            val portfolio: String? = null, // https://api.unsplash.com/users/tobbes_rd/portfolio
            @Json(name = "following")
            val following: String? = null, // https://api.unsplash.com/users/tobbes_rd/following
            @Json(name = "followers")
            val followers: String? = null // https://api.unsplash.com/users/tobbes_rd/followers
        )

        @JsonClass(generateAdapter = true)
        data class ProfileImage(
            @Json(name = "small")
            val small: String? = null, // https://images.unsplash.com/profile-1594563323963-d4411a8e992eimage?ixlib=rb-4.0.3&crop=faces&fit=crop&w=32&h=32
            @Json(name = "medium")
            val medium: String? = null, // https://images.unsplash.com/profile-1594563323963-d4411a8e992eimage?ixlib=rb-4.0.3&crop=faces&fit=crop&w=64&h=64
            @Json(name = "large")
            val large: String? = null // https://images.unsplash.com/profile-1594563323963-d4411a8e992eimage?ixlib=rb-4.0.3&crop=faces&fit=crop&w=128&h=128
        )

        @JsonClass(generateAdapter = true)
        data class Social(
            @Json(name = "instagram_username")
            val instagramUsername: String? = null, // tobbes_rd
            @Json(name = "portfolio_url")
            val portfolioUrl: String? = null, // http://www.maartendeckers.com
            @Json(name = "twitter_username")
            val twitterUsername: String? = null, // neom
            @Json(name = "paypal_email")
            val paypalEmail: Any? = null // null
        )
    }
}