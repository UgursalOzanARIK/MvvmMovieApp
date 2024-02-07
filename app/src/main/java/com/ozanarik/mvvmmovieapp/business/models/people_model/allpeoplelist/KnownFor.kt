package com.ozanarik.mvvmmovieapp.business.models.people_model.allpeoplelist


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class KnownFor(
    @Expose
    val adult: Boolean = false, // false
    @SerializedName("backdrop_path")
    @Expose
    val backdropPath: String = "", // /sd4xN5xi8tKRPrJOWwNiZEile7f.jpg
    @SerializedName("first_air_date")
    @Expose
    val firstAirDate: String? = null, // 2019-06-16
    @SerializedName("genre_ids")
    @Expose
    val genreİds: List<Int> = listOf(),
    @Expose
    val id: Int = 0, // 920
    @SerializedName("media_type")
    @Expose
    val mediaType: String = "", // movie
    @Expose
    val name: String? = null, // Euphoria
    @SerializedName("origin_country")
    @Expose
    val originCountry: List<String>? = null,
    @SerializedName("original_language")
    @Expose
    val originalLanguage: String = "", // en
    @SerializedName("original_name")
    @Expose
    val originalName: String? = null, // Euphoria
    @SerializedName("original_title")
    @Expose
    val originalTitle: String? = null, // Cars
    @Expose
    val overview: String = "", // Lightning McQueen, a hotshot rookie race car driven to succeed, discovers that life is about the journey, not the finish line, when he finds himself unexpectedly detoured in the sleepy Route 66 town of Radiator Springs. On route across the country to the big Piston Cup Championship in California to compete against two seasoned pros, McQueen gets to know the town's offbeat characters.
    @Expose
    val popularity: Double = 0.0, // 50.829
    @SerializedName("poster_path")
    @Expose
    val posterPath: String = "", // /abW5AzHDaIK1n9C36VdAeOwORRA.jpg
    @SerializedName("release_date")
    @Expose
    val releaseDate: String? = null, // 2006-06-08
    @Expose
    val title: String? = null, // Cars
    @Expose
    val video: Boolean? = null, // false
    @SerializedName("vote_average")
    @Expose
    val voteAverage: Double = 0.0, // 6.951
    @SerializedName("vote_count")
    @Expose
    val voteCount: Int = 0 // 13226
)