package com.ozanarik.mvvmmovieapp.business.models.movie_youtube_response


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class ResultX(
    @Expose
    val id: String = "", // 64fb16fbdb4ed610343d72c3
    @SerializedName("iso_3166_1")
    @Expose
    val iso31661: String = "", // US
    @SerializedName("iso_639_1")
    @Expose
    val iso6391: String = "", // en
    @Expose
    val key: String = "", // dfeUzm6KF4g
    @Expose
    val name: String = "", // 20th Anniversary Trailer
    @Expose
    val official: Boolean = false, // true
    @SerializedName("published_at")
    @Expose
    val publishedAt: String = "", // 2019-10-15T18:59:47.000Z
    @Expose
    val site: String = "", // YouTube
    @Expose
    val size: Int = 0, // 1080
    @Expose
    val type: String = "" // Trailer
)