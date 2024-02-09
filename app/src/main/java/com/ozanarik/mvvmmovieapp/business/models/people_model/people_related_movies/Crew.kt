package com.ozanarik.mvvmmovieapp.business.models.people_model.people_related_movies


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Crew(
    @Expose
    val adult: Boolean = false, // false
    @SerializedName("backdrop_path")
    @Expose
    val backdropPath: String? = null, // /sPuANGEwysJe3ey3BmrX1vdvz7G.jpg
    @SerializedName("credit_id")
    @Expose
    val creditİd: String = "", // 600a32dc2d531a0057635070
    @Expose
    val department: String = "", // Production
    @SerializedName("genre_ids")
    @Expose
    val genreİds: List<Int> = listOf(),
    @Expose
    val id: Int = 0, // 675480
    @Expose
    val job: String = "", // Co-Producer
    @SerializedName("media_type")
    @Expose
    val mediaType: String = "", // movie
    @SerializedName("original_language")
    @Expose
    val originalLanguage: String = "", // en
    @SerializedName("original_title")
    @Expose
    val originalTitle: String = "", // Last Call
    @Expose
    val overview: String = "", // A real estate developer returns to his old Philly neighborhood and must decide to raze or resurrect the family bar.
    @Expose
    val popularity: Double = 0.0, // 5.119
    @SerializedName("poster_path")
    @Expose
    val posterPath: String = "", // /bySCgLjkmPFuUd0ExwruObY9VHn.jpg
    @SerializedName("release_date")
    @Expose
    val releaseDate: String = "", // 2021-03-19
    @Expose
    val title: String = "", // Last Call
    @Expose
    val video: Boolean = false, // false
    @SerializedName("vote_average")
    @Expose
    val voteAverage: Double = 0.0, // 5.077
    @SerializedName("vote_count")
    @Expose
    val voteCount: Int = 0 // 13
)