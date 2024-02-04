package com.ozanarik.mvvmmovieapp.business.models.shows_model


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class ProductionCompany(
    @Expose
    val id: Int = 0, // 10532
    @SerializedName("logo_path")
    @Expose
    val logoPath: String? = "", // /fgnJcdnipMiexhxaWC3w14b5Nbg.png
    @Expose
    val name: String? = "", // World Productions
    @SerializedName("origin_country")
    @Expose
    val originCountry: String? = "" // GB


)