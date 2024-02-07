package com.ozanarik.mvvmmovieapp.business.models.people_model.allpeoplelist


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Result(
    @Expose
    val adult: Boolean = false, // false
    @Expose
    val gender: Int = 0, // 2
    @Expose
    val id: Int = 0, // 12799
    @SerializedName("known_for")
    @Expose
    val knownFor: List<KnownFor> = listOf(),
    @SerializedName("known_for_department")
    @Expose
    val knownForDepartment: String = "", // Acting
    @Expose
    val name: String = "", // Jeremy Piven
    @SerializedName("original_name")
    @Expose
    val originalName: String = "", // Jeremy Piven
    @Expose
    val popularity: Double = 0.0, // 355.007
    @SerializedName("profile_path")
    @Expose
    val profilePath: String = "" // /pqdR8zqAWF87chGYlbdYr0YfC7g.jpg
)