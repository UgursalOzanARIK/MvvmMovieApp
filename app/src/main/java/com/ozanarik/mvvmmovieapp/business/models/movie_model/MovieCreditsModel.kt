package com.ozanarik.mvvmmovieapp.business.models.movie_model


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class MovieCreditsModel(
    @Expose
    val cast: List<Cast> = listOf(),
    @Expose
    val crew: List<Crew> = listOf(),
    @Expose
    val id: Int = 0 // 550
)