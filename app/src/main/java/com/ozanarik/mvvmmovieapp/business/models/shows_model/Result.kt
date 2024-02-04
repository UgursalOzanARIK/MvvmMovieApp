package com.ozanarik.mvvmmovieapp.business.models.shows_model


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Result(
    @Expose
    val adult: Boolean = false, // false
    @SerializedName("backdrop_path")
    @Expose
    val backdropPath: String? = null, // /xl1wGwaPZInJo1JAnpKqnFozWBE.jpg
    @SerializedName("first_air_date")
    @Expose
    val firstAirDate: String = "", // 2014-02-17
    @SerializedName("genre_ids")
    @Expose
    val genreİds: List<Int> = listOf(),
    @Expose
    val id: Int = 0, // 59941
    @Expose
    val name: String = "", // The Tonight Show Starring Jimmy Fallon
    @SerializedName("origin_country")
    @Expose
    val originCountry: List<String> = listOf(),
    @SerializedName("original_language")
    @Expose
    val originalLanguage: String = "", // en
    @SerializedName("original_name")
    @Expose
    val originalName: String = "", // The Tonight Show Starring Jimmy Fallon
    @Expose
    val overview: String = "", // After Jay Leno's second retirement from the program, Jimmy Fallon stepped in as his permanent replacement. After 42 years in Los Angeles the program was brought back to New York.
    @Expose
    val popularity: Double = 0.0, // 6915.429
    @SerializedName("poster_path")
    @Expose
    val posterPath: String = "", // /g4amxJvtpnY79J77xeamnAEUO8r.jpg
    @SerializedName("vote_average")
    @Expose
    val voteAverage: Double = 0.0, // 5.97
    @SerializedName("vote_count")
    @Expose
    val voteCount: Int = 0 // 253
)