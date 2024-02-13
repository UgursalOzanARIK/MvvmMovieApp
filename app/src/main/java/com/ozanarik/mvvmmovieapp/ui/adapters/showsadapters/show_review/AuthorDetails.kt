package com.ozanarik.mvvmmovieapp.ui.adapters.showsadapters.show_review


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class AuthorDetails(
    @SerializedName("avatar_path")
    @Expose
    val avatarPath: String? = null, // /hLLsAvAnVT0cFU7JuuaaItTWXv8.jpg
    @Expose
    val name: String = "",
    @Expose
    val rating: Int = 0, // 10
    @Expose
    val username: String = "" // slyone10001
)