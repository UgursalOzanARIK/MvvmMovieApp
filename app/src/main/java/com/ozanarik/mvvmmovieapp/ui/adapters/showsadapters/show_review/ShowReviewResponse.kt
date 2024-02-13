package com.ozanarik.mvvmmovieapp.ui.adapters.showsadapters.show_review


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class ShowReviewResponse(
    @Expose
    val id: Int = 0, // 1396
    @Expose
    val page: Int = 0, // 1
    @Expose
    val results: List<Result> = listOf(),
    @SerializedName("total_pages")
    @Expose
    val totalPages: Int = 0, // 1
    @SerializedName("total_results")
    @Expose
    val totalResults: Int = 0 // 4
)