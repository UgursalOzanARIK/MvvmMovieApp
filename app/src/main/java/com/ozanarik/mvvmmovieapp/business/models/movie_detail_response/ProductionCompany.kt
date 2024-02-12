package com.ozanarik.mvvmmovieapp.business.models.movie_detail_response


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class ProductionCompany(
    @Expose
    val id: Int = 0, // 491
    @SerializedName("logo_path")
    @Expose
    val logoPath: String? = null, // /5LvDUt3KmvRnXQ4NrdWJYHeuA8J.png
    @Expose
    val name: String = "", // Summit Entertainment
    @SerializedName("origin_country")
    @Expose
    val originCountry: String = "" // US
)