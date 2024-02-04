package com.ozanarik.mvvmmovieapp.business.models.movie_model


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class ProductionCountry(
    @SerializedName("iso_3166_1")
    @Expose
    val iso31661: String = "", // GB
    @Expose
    val name: String = "" // United Kingdom
)