package com.ozanarik.mvvmmovieapp.business.shows_model


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Genre(
    @Expose
    val id: Int = 0, // 18
    @Expose
    val name: String = "" // Drama
)