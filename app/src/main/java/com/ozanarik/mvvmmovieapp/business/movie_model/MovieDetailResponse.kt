package com.ozanarik.mvvmmovieapp.business.movie_model


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class MovieDetailResponse(
    @Expose
    val adult: Boolean = false, // false
    @SerializedName("backdrop_path")
    @Expose
    val backdropPath: String = "", // /cXQH2u7wUIX1eoIdEj51kHXoWhX.jpg
    @SerializedName("belongs_to_collection")
    @Expose
    val belongsToCollection: Any? = null, // null
    @Expose
    val budget: Int = 0, // 1350000
    @Expose
    val genres: List<Genre> = listOf(),
    @Expose
    val homepage: String = "", // http://www.universalstudiosentertainment.com/lock-stock-and-two-smoking-barrels/
    @Expose
    val id: Int = 0, // 100
    @SerializedName("imdb_id")
    @Expose
    val imdbİd: String = "", // tt0120735
    @SerializedName("original_language")
    @Expose
    val originalLanguage: String = "", // en
    @SerializedName("original_title")
    @Expose
    val originalTitle: String = "", // Lock, Stock and Two Smoking Barrels
    @Expose
    val overview: String = "", // A card shark and his unwillingly-enlisted friends need to make a lot of cash quick after losing a sketchy poker match. To do this they decide to pull a heist on a small-time gang who happen to be operating out of the flat next door.
    @Expose
    val popularity: Double = 0.0, // 17.769
    @SerializedName("poster_path")
    @Expose
    val posterPath: String = "", // /wt2TRBmFmBn5M5MBcPTwovlREaB.jpg
    @SerializedName("production_companies")
    @Expose
    val productionCompanies: List<ProductionCompany> = listOf(),
    @SerializedName("production_countries")
    @Expose
    val productionCountries: List<ProductionCountry> = listOf(),
    @SerializedName("release_date")
    @Expose
    val releaseDate: String = "", // 1998-08-28
    @Expose
    val revenue: Int = 0, // 28356188
    @Expose
    val runtime: Int = 0, // 105
    @SerializedName("spoken_languages")
    @Expose
    val spokenLanguages: List<SpokenLanguage> = listOf(),
    @Expose
    val status: String = "", // Released
    @Expose
    val tagline: String = "", // A Disgrace to Criminals Everywhere.
    @Expose
    val title: String = "", // Lock, Stock and Two Smoking Barrels
    @Expose
    val video: Boolean = false, // false
    @SerializedName("vote_average")
    @Expose
    val voteAverage: Double = 0.0, // 8.124
    @SerializedName("vote_count")
    @Expose
    val voteCount: Int = 0 // 6222
)