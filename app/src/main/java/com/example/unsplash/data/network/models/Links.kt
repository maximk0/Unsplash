package com.example.unsplash.data.network.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Links(
    @Json(name = "self")
    val self: String? = null, // https://api.unsplash.com/photos/ZefdTSLstl0
    @Json(name = "html")
    val html: String? = null, // https://unsplash.com/photos/ZefdTSLstl0
    @Json(name = "download")
    val download: String? = null, // https://unsplash.com/photos/ZefdTSLstl0/download?ixid=M3w0Mzg5MTh8MHwxfGFsbHx8fHx8fHx8fDE2ODQ0MTI1MTl8
    @Json(name = "download_location")
    val downloadLocation: String? = null // https://api.unsplash.com/photos/ZefdTSLstl0/download?ixid=M3w0Mzg5MTh8MHwxfGFsbHx8fHx8fHx8fDE2ODQ0MTI1MTl8
)