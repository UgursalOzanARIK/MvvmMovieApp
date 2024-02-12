package com.ozanarik.mvvmmovieapp.business.models.shows_model.show_youtube_trailer_response


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Result(
    @Expose
    val id: String = "", // 5759db2fc3a3683e7c003df7
    @SerializedName("iso_3166_1")
    @Expose
    val iso31661: String = "", // US
    @SerializedName("iso_639_1")
    @Expose
    val iso6391: String = "", // en
    @Expose
    val key: String = "", // XZ8daibM3AE
    @Expose
    val name: String = "", // Series Trailer
    @Expose
    val official: Boolean = false, // true
    @SerializedName("published_at")
    @Expose
    val publishedAt: String = "", // 2013-03-25T16:28:54.000Z
    @Expose
    val site: String = "", // YouTube
    @Expose
    val size: Int = 0, // 480
    @Expose
    val type: String = "" // Trailer
)