package com.ozanarik.mvvmmovieapp.business.models.movie_model


import com.google.gson.annotations.Expose

data class Dates(
    @Expose
    val maximum: String = "", // 2024-01-24
    @Expose
    val minimum: String = "" // 2023-12-13
)