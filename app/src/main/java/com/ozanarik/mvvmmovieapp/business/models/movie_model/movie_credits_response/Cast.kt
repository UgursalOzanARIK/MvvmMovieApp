package com.ozanarik.mvvmmovieapp.business.models.movie_model.movie_credits_response


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Cast(
    @Expose
    val adult: Boolean = false, // false
    @SerializedName("cast_id")
    @Expose
    val castİd: Int = 0, // 4
    @Expose
    val character: String = "", // Narrator
    @SerializedName("credit_id")
    @Expose
    val creditİd: String = "", // 52fe4250c3a36847f80149f3
    @Expose
    val gender: Int = 0, // 2
    @Expose
    val id: Int = 0, // 819
    @SerializedName("known_for_department")
    @Expose
    val knownForDepartment: String = "", // Acting
    @Expose
    val name: String = "", // Edward Norton
    @Expose
    val order: Int = 0, // 0
    @SerializedName("original_name")
    @Expose
    val originalName: String = "", // Edward Norton
    @Expose
    val popularity: Double = 0.0, // 22.571
    @SerializedName("profile_path")
    @Expose
    val profilePath: String? = null // /5XBzD5WuTyVQZeS4VI25z2moMeY.jpg
)