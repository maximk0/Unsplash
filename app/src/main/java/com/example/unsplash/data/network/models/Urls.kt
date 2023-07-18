package com.example.unsplash.data.network.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Urls(
    @Json(name = "raw")
    val raw: String? = null, // https://images.unsplash.com/photo-1682205595406-0978ff7b7709?ixid=Mnw0Mzg5MTh8MHwxfGFsbHwyMXx8fHx8fDJ8fDE2ODI4NTM4NDQ&ixlib=rb-4.0.3
    @Json(name = "full")
    val full: String, // https://images.unsplash.com/photo-1682205595406-0978ff7b7709?crop=entropy&cs=srgb&fm=jpg&ixid=Mnw0Mzg5MTh8MHwxfGFsbHwyMXx8fHx8fDJ8fDE2ODI4NTM4NDQ&ixlib=rb-4.0.3&q=85
    @Json(name = "regular")
    val regular: String, // https://images.unsplash.com/photo-1682205595406-0978ff7b7709?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=Mnw0Mzg5MTh8MHwxfGFsbHwyMXx8fHx8fDJ8fDE2ODI4NTM4NDQ&ixlib=rb-4.0.3&q=80&w=1080
    @Json(name = "small")
    val small: String, // https://images.unsplash.com/photo-1682205595406-0978ff7b7709?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=Mnw0Mzg5MTh8MHwxfGFsbHwyMXx8fHx8fDJ8fDE2ODI4NTM4NDQ&ixlib=rb-4.0.3&q=80&w=400
//    @Json(name = "thumb")
//    val thumb: String, // https://images.unsplash.com/photo-1682205595406-0978ff7b7709?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=Mnw0Mzg5MTh8MHwxfGFsbHwyMXx8fHx8fDJ8fDE2ODI4NTM4NDQ&ixlib=rb-4.0.3&q=80&w=200
//    @Json(name = "small_s3")
//    val smallS3: String // https://s3.us-west-2.amazonaws.com/images.unsplash.com/small/photo-1682205595406-0978ff7b7709
)