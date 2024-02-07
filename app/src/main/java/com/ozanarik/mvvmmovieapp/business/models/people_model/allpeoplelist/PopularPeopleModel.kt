package com.ozanarik.mvvmmovieapp.business.models.people_model.allpeoplelist


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class PopularPeopleModel(
    @Expose
    val page: Int = 0, // 1
    @Expose
    val results: List<Result> = listOf(),
    @SerializedName("total_pages")
    @Expose
    val totalPages: Int = 0, // 162243
    @SerializedName("total_results")
    @Expose
    val totalResults: Int = 0 // 3244859
)