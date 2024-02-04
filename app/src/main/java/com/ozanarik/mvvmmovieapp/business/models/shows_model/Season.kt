package com.ozanarik.mvvmmovieapp.business.models.shows_model


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Season(
    @SerializedName("air_date")
    @Expose
    val airDate: String = "", // 2007-01-02
    @SerializedName("episode_count")
    @Expose
    var episodeCount: Int = 0, // 2
    @Expose
    val id: Int = 0, // 439
    @Expose
    val name: String = "", // Specials
    @Expose
    val overview: String = "",
    @SerializedName("poster_path")
    @Expose
    val posterPath: String? = null, // /mscDDn8beq1WkLXB4TTNl7tBzeY.jpg
    @SerializedName("season_number")
    @Expose
    val seasonNumber: Int = 0, // 0
    @SerializedName("vote_average")
    @Expose
    val voteAverage: Double = 0.0 // 0
)