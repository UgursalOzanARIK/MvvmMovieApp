package com.ozanarik.mvvmmovieapp.business.movie_model


import com.google.gson.annotations.Expose

data class Genre(
    @Expose
    val id: Int = 0, // 35
    @Expose
    val name: String = "" // Comedy
)