package com.ozanarik.mvvmmovieapp.business.models.shows_model


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class SpokenLanguage(
    @SerializedName("english_name")
    @Expose
    val englishName: String = "", // English
    @SerializedName("iso_639_1")
    @Expose
    val iso6391: String = "", // en
    @Expose
    val name: String = "" // English
)