package com.ozanarik.mvvmmovieapp.business.models.movie_model


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class AuthorDetails(
    @SerializedName("avatar_path")
    @Expose
    val avatarPath: String? = null, // /4KVM1VkqmXLOuwj1jjaSdxbvBDk.jpg
    @Expose
    val name: String = "",
    @Expose
    val rating: Int? = null, // 9
    @Expose
    val username: String = "" // Goddard
)