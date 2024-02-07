package com.ozanarik.mvvmmovieapp.business.models.movie_model


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class MovieReviewModel(
    @Expose
    val id: Int = 0, // 550
    @Expose
    val page: Int = 0, // 1
    @Expose
    val results: List<ResultXX> = listOf(),
    @SerializedName("total_pages")
    @Expose
    val totalPages: Int = 0, // 1
    @SerializedName("total_results")
    @Expose
    val totalResults: Int = 0 // 9
)