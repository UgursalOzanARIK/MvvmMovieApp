package com.ozanarik.mvvmmovieapp.business.models.movie_model.movie_credits_response


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Crew(
    @Expose
    val adult: Boolean = false, // false
    @SerializedName("credit_id")
    @Expose
    val creditİd: String = "", // 55731b8192514111610027d7
    @Expose
    val department: String = "", // Production
    @Expose
    val gender: Int = 0, // 2
    @Expose
    val id: Int = 0, // 376
    @Expose
    val job: String = "", // Executive Producer
    @SerializedName("known_for_department")
    @Expose
    val knownForDepartment: String = "", // Production
    @Expose
    val name: String = "", // Arnon Milchan
    @SerializedName("original_name")
    @Expose
    val originalName: String = "", // Arnon Milchan
    @Expose
    val popularity: Double = 0.0, // 1.732
    @SerializedName("profile_path")
    @Expose
    val profilePath: String? = null // /b2hBExX4NnczNAnLuTBF4kmNhZm.jpg
)