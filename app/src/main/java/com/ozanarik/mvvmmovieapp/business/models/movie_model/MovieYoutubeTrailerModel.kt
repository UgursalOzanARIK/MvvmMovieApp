package com.ozanarik.mvvmmovieapp.business.models.movie_model


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class MovieYoutubeTrailerModel(
    @Expose
    val id: Int = 0, // 550
    @Expose
    val results: List<ResultX> = listOf()
)