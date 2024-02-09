package com.ozanarik.mvvmmovieapp.business.models.people_model.people_related_movies


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Cast(
    @Expose
    val adult: Boolean = false, // false
    @SerializedName("backdrop_path")
    @Expose
    val backdropPath: String? = null, // /4CxQXuitlZFNndvis2uv2YUzAQ7.jpg
    @Expose
    val character: String = "", // Droz
    @SerializedName("credit_id")
    @Expose
    val creditİd: String = "", // 52fe45f29251416c75067e05
    @SerializedName("episode_count")
    @Expose
    val episodeCount: Int? = null, // 1
    @SerializedName("first_air_date")
    @Expose
    val firstAirDate: String? = null, // 2003-01-26
    @SerializedName("genre_ids")
    @Expose
    val genreİds: List<Int> = listOf(),
    @Expose
    val id: Int = 0, // 14425
    @SerializedName("media_type")
    @Expose
    val mediaType: String = "", // movie
    @Expose
    val name: String? = null, // Jimmy Kimmel Live!
    @Expose
    val order: Int? = null, // 0
    @SerializedName("origin_country")
    @Expose
    val originCountry: List<String>? = null,
    @SerializedName("original_language")
    @Expose
    val originalLanguage: String = "", // en
    @SerializedName("original_name")
    @Expose
    val originalName: String? = null, // Jimmy Kimmel Live!
    @SerializedName("original_title")
    @Expose
    val originalTitle: String? = null, // PCU
    @Expose
    val overview: String = "", // Nervous high school senior Tom Lawrence visits Port Chester University, where he gets a taste of politically correct college life when he's guided by fraternity wild man Droz and his housemates at The Pit. But Droz and his pals have rivals in nasty preppy Rand McPherson and the school's steely president. With their house threatened with expulsion, Droz and company decide to throw a raging party where the various factions will collide.
    @Expose
    val popularity: Double = 0.0, // 10.762
    @SerializedName("poster_path")
    @Expose
    val posterPath: String? = null, // /xTToUzy9Im9nu2W8fz7TPWoDWN5.jpg
    @SerializedName("release_date")
    @Expose
    val releaseDate: String? = null, // 1994-04-29
    @Expose
    val title: String? = null, // PCU
    @Expose
    val video: Boolean? = null, // false
    @SerializedName("vote_average")
    @Expose
    val voteAverage: Double = 0.0, // 5.93
    @SerializedName("vote_count")
    @Expose
    val voteCount: Int = 0 // 115
)