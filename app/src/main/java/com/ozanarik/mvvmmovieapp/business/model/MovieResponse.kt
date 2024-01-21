package com.ozanarik.mvvmmovieapp.business.model


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class MovieResponse(
    @Expose
    val dates: Dates = Dates(),
    @Expose
    val page: Int = 0, // 1
    @Expose
    val results: List<Result> = listOf(),
    @SerializedName("total_pages")
    @Expose
    val totalPages: Int = 0, // 152
    @SerializedName("total_results")
    @Expose
    val totalResults: Int = 0 // 3022
)