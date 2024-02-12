package com.ozanarik.mvvmmovieapp.business.models.shows_model.shows_credits_cast_model


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Crew(
    @Expose
    val adult: Boolean = false, // false
    @SerializedName("credit_id")
    @Expose
    val creditİd: String = "", // 64189e3f0d5d85009ba2c1d8
    @Expose
    val department: String = "", // Production
    @Expose
    val gender: Int = 0, // 2
    @Expose
    val id: Int = 0, // 24951
    @Expose
    val job: String = "", // Co-Executive Producer
    @SerializedName("known_for_department")
    @Expose
    val knownForDepartment: String = "", // Production
    @Expose
    val name: String = "", // Peter Gould
    @SerializedName("original_name")
    @Expose
    val originalName: String = "", // Peter Gould
    @Expose
    val popularity: Double = 0.0, // 5.555
    @SerializedName("profile_path")
    @Expose
    val profilePath: String? = null // /a2dJSpUiXQ2NAxqSzztr6WsnhOJ.jpg
)