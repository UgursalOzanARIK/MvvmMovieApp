package com.ozanarik.mvvmmovieapp.business.shows_model


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class CreatedBy(
    @SerializedName("credit_id")
    @Expose
    val creditİd: String = "", // 52533ef619c295794008a9e2
    @Expose
    val gender: Int = 0, // 1
    @Expose
    val id: Int = 0, // 1186907
    @Expose
    val name: String = "", // Amy Jenkins
    @SerializedName("profile_path")
    @Expose
    val profilePath: Any? = null // null
)