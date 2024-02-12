package com.ozanarik.mvvmmovieapp.business.models.shows_model.shows_credits_cast_model


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Cast(
    @Expose
    val adult: Boolean = false, // false
    @Expose
    val character: String = "", // Walter White
    @SerializedName("credit_id")
    @Expose
    val creditİd: String = "", // 52542282760ee313280017f9
    @Expose
    val gender: Int = 0, // 2
    @Expose
    val id: Int = 0, // 17419
    @SerializedName("known_for_department")
    @Expose
    val knownForDepartment: String = "", // Acting
    @Expose
    val name: String = "", // Bryan Cranston
    @Expose
    val order: Int = 0, // 0
    @SerializedName("original_name")
    @Expose
    val originalName: String = "", // Bryan Cranston
    @Expose
    val popularity: Double = 0.0, // 31.024
    @SerializedName("profile_path")
    @Expose
    val profilePath: String = "" // /7Jahy5LZX2Fo8fGJltMreAI49hC.jpg
)