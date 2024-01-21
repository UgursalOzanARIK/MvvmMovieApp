package com.ozanarik.mvvmmovieapp.business.model


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Result(
    @Expose
    val adult: Boolean = false, // false
    @SerializedName("backdrop_path")
    @Expose
    val backdropPath: String = "", // /rz8GGX5Id2hCW1KzAIY4xwbQw1w.jpg
    @SerializedName("genre_ids")
    @Expose
    val genreIds: List<Int> = listOf(),
    @Expose
    val id: Int = 0, // 955916
    @SerializedName("original_language")
    @Expose
    val originalLanguage: String = "", // en
    @SerializedName("original_title")
    @Expose
    val originalTitle: String = "", // Lift
    @Expose
    val overview: String = "", // An international heist crew, led by Cyrus Whitaker, race to lift $500 million in gold from a passenger plane at 40,000 feet.
    @Expose
    val popularity: Double = 0.0, // 2025.03
    @SerializedName("poster_path")
    @Expose
    val posterPath: String = "", // /gma8o1jWa6m0K1iJ9TzHIiFyTtI.jpg
    @SerializedName("release_date")
    @Expose
    val releaseDate: String = "", // 2024-01-10
    @Expose
    val title: String = "", // Lift
    @Expose
    val video: Boolean = false, // false
    @SerializedName("vote_average")
    @Expose
    val voteAverage: Double = 0.0, // 6.323
    @SerializedName("vote_count")
    @Expose
    val voteCount: Int = 0 // 364
)