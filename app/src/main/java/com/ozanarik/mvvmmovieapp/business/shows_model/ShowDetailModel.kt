package com.ozanarik.mvvmmovieapp.business.shows_model


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import java.io.Serializable

data class ShowDetailModel(
    @Expose
    val adult: Boolean = false, // false
    @SerializedName("backdrop_path")
    @Expose
    val backdropPath: String = "", // /wkQRvJEExw8vdwNU0Hbo4rZ62Vp.jpg
    @SerializedName("created_by")
    @Expose
    val createdBy: List<CreatedBy> = listOf(),
    @SerializedName("episode_run_time")
    @Expose
    val episodeRunTime: List<Int> = listOf(),
    @SerializedName("first_air_date")
    @Expose
    val firstAirDate: String = "", // 1996-03-18
    @Expose
    val genres: List<Genre> = listOf(),
    @Expose
    val homepage: String = "", // http://www.bbc.co.uk/programmes/b00pft4t
    @Expose
    val id: Int = 0, // 150
    @SerializedName("in_production")
    @Expose
    val inProduction: Boolean = false, // false
    @Expose
    val languages: List<String> = listOf(),
    @SerializedName("last_air_date")
    @Expose
    val lastAirDate: String = "", // 1997-08-07
    @SerializedName("last_episode_to_air")
    @Expose
    val lastEpisodeToAir: LastEpisodeToAir = LastEpisodeToAir(),
    @Expose
    val name: String = "", // This Life
    @Expose
    val networks: List<Network> = listOf(),
    @SerializedName("next_episode_to_air")
    @Expose
    val nextEpisodeToAir: Any? = null, // null
    @SerializedName("number_of_episodes")
    @Expose
    val numberOfEpisodes: Int = 0, // 32
    @SerializedName("number_of_seasons")
    @Expose
    val numberOfSeasons: Int = 0, // 2
    @SerializedName("origin_country")
    @Expose
    val originCountry: List<String> = listOf(),
    @SerializedName("original_language")
    @Expose
    val originalLanguage: String = "", // en
    @SerializedName("original_name")
    @Expose
    val originalName: String = "", // This Life
    @Expose
    val overview: String = "", // Cult drama series about a group of aspiring young lawyers sharing a shabby house in London, charting their careers and personal lives.
    @Expose
    val popularity: Double = 0.0, // 23.902
    @SerializedName("poster_path")
    @Expose
    val posterPath: String = "", // /f9PiErdWF5IDcvZULIae4oeeb5r.jpg
    @SerializedName("production_companies")
    @Expose
    val productionCompanies: List<ProductionCompany?> = listOf(),
    @SerializedName("production_countries")
    @Expose
    val productionCountries: List<Any?> = listOf(),
    @Expose
    val seasons: List<Season> = listOf(),
    @SerializedName("spoken_languages")
    @Expose
    val spokenLanguages: List<SpokenLanguage> = listOf(),
    @Expose
    val status: String = "", // Ended
    @Expose
    val tagline: String = "", // Living, loving, partygoing 20somethings
    @Expose
    val type: String = "", // Scripted
    @SerializedName("vote_average")
    @Expose
    val voteAverage: Double = 0.0, // 7.789
    @SerializedName("vote_count")
    @Expose
    val voteCount: Int = 0 // 19
):Serializable