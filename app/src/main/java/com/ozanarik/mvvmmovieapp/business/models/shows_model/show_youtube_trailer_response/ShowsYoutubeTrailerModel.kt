package com.ozanarik.mvvmmovieapp.business.models.shows_model.show_youtube_trailer_response


import com.google.gson.annotations.Expose

data class ShowsYoutubeTrailerModel(
    @Expose
    val id: Int = 0, // 1396
    @Expose
    val results: List<Result> = listOf()
)