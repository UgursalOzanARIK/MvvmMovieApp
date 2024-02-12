package com.ozanarik.mvvmmovieapp.business.models.movie_model.movie_credits_response


import com.google.gson.annotations.Expose

data class Genre(
    @Expose
    val id: Int = 0, // 35
    @Expose
    val name: String = "" // Comedy
)