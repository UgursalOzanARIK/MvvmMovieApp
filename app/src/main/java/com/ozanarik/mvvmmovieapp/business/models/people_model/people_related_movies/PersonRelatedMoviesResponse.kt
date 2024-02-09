package com.ozanarik.mvvmmovieapp.business.models.people_model.people_related_movies


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class PersonRelatedMoviesResponse(
    @Expose
    val cast: List<Cast> = listOf(),
    @Expose
    val crew: List<Crew> = listOf(),
    @Expose
    val id: Int = 0 // 12799
)