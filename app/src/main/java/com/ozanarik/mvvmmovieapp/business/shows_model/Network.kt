package com.ozanarik.mvvmmovieapp.business.shows_model


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Network(
    @Expose
    val id: Int = 0, // 332
    @SerializedName("logo_path")
    @Expose
    val logoPath: String = "", // /6kl5tMuct7u3ej5myL4c9QQVSW1.png
    @Expose
    val name: String = "", // BBC Two
    @SerializedName("origin_country")
    @Expose
    val originCountry: String = "" // GB
)