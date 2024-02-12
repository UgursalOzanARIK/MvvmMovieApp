package com.ozanarik.mvvmmovieapp.business.models.movie_youtube_response


import com.google.gson.annotations.Expose

data class MovieYoutubeTrailerModel(
    @Expose
    val id: Int = 0, // 550
    @Expose
    val results: List<ResultX> = listOf()
)