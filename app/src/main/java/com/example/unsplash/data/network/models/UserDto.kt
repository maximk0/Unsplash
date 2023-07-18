package com.example.unsplash.data.network.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserDto(
 @Json(name = "id")
 val id: String? = null, // rzXqwFk0MFM
 @Json(name = "updated_at")
 val updatedAt: String? = null, // 2023-05-25T16:56:59Z
 @Json(name = "username")
 val username: String? = null, // vfzr
 @Json(name = "name")
 val name: String? = null, // anonim anonim
 @Json(name = "first_name")
 val firstName: String? = null, // anonim
 @Json(name = "last_name")
 val lastName: String? = null, // anonim
 @Json(name = "twitter_username")
 val twitterUsername: Any? = null, // null
 @Json(name = "portfolio_url")
 val portfolioUrl: Any? = null, // null
 @Json(name = "bio")
 val bio: String? = null, // null
 @Json(name = "location")
 val location: String? = null, // null
 @Json(name = "links")
 val links: Links? = null,
 @Json(name = "profile_image")
 val profileImage: ProfileImage? = null,
 @Json(name = "instagram_username")
 val instagramUsername: Any? = null, // null
 @Json(name = "total_collections")
 val totalCollections: Int? = null, // 1
 @Json(name = "total_likes")
 val totalLikes: Int? = null, // 17
 @Json(name = "total_photos")
 val totalPhotos: Int? = null, // 0
 @Json(name = "accepted_tos")
 val acceptedTos: Boolean? = null, // false
 @Json(name = "for_hire")
 val forHire: Boolean? = null, // false
 @Json(name = "social")
 val social: Social? = null,
 @Json(name = "followed_by_user")
 val followedByUser: Boolean? = null, // false
 @Json(name = "photos")
 val photos: List<Any?>? = null,
 @Json(name = "badge")
 val badge: Any? = null, // null
 @Json(name = "tags")
 val tags: Tags? = null,
 @Json(name = "followers_count")
 val followersCount: Int? = null, // 0
 @Json(name = "following_count")
 val followingCount: Int? = null, // 0
 @Json(name = "allow_messages")
 val allowMessages: Boolean? = null, // true
 @Json(name = "numeric_id")
 val numericId: Int? = null, // 12475211
 @Json(name = "downloads")
 val downloads: Int? = null, // 0
 @Json(name = "meta")
 val meta: Meta? = null,
 @Json(name = "uid")
 val uid: String? = null, // rzXqwFk0MFM
 @Json(name = "confirmed")
 val confirmed: Boolean? = null, // true
 @Json(name = "uploads_remaining")
 val uploadsRemaining: Int? = null, // 10
 @Json(name = "unlimited_uploads")
 val unlimitedUploads: Boolean? = null, // false
 @Json(name = "email")
 val email: String? = null, // vfzr@bk.ru
 @Json(name = "dmca_verification")
 val dmcaVerification: String? = null, // unverified
 @Json(name = "unread_in_app_notifications")
 val unreadInAppNotifications: Boolean? = null, // false
 @Json(name = "unread_highlight_notifications")
 val unreadHighlightNotifications: Boolean? = null // false
) {
 @JsonClass(generateAdapter = true)
 data class Links(
  @Json(name = "self")
  val self: String? = null, // https://api.unsplash.com/users/vfzr
  @Json(name = "html")
  val html: String? = null, // https://unsplash.com/@vfzr
  @Json(name = "photos")
  val photos: String? = null, // https://api.unsplash.com/users/vfzr/photos
  @Json(name = "likes")
  val likes: String? = null, // https://api.unsplash.com/users/vfzr/likes
  @Json(name = "portfolio")
  val portfolio: String? = null, // https://api.unsplash.com/users/vfzr/portfolio
  @Json(name = "following")
  val following: String? = null, // https://api.unsplash.com/users/vfzr/following
  @Json(name = "followers")
  val followers: String? = null // https://api.unsplash.com/users/vfzr/followers
 )

 @JsonClass(generateAdapter = true)
 data class ProfileImage(
  @Json(name = "small")
  val small: String? = null, // https://images.unsplash.com/placeholder-avatars/extra-large.jpg?ixlib=rb-4.0.3&crop=faces&fit=crop&w=32&h=32
  @Json(name = "medium")
  val medium: String? = null, // https://images.unsplash.com/placeholder-avatars/extra-large.jpg?ixlib=rb-4.0.3&crop=faces&fit=crop&w=64&h=64
  @Json(name = "large")
  val large: String? = null // https://images.unsplash.com/placeholder-avatars/extra-large.jpg?ixlib=rb-4.0.3&crop=faces&fit=crop&w=128&h=128
 )

 @JsonClass(generateAdapter = true)
 data class Social(
  @Json(name = "instagram_username")
  val instagramUsername: Any? = null, // null
  @Json(name = "portfolio_url")
  val portfolioUrl: Any? = null, // null
  @Json(name = "twitter_username")
  val twitterUsername: Any? = null, // null
  @Json(name = "paypal_email")
  val paypalEmail: Any? = null // null
 )

 @JsonClass(generateAdapter = true)
 data class Tags(
  @Json(name = "custom")
  val custom: List<Any?>? = null,
  @Json(name = "aggregated")
  val aggregated: List<Any?>? = null
 )

 @JsonClass(generateAdapter = true)
 data class Meta(
  @Json(name = "index")
  val index: Boolean? = null // false
 )
}