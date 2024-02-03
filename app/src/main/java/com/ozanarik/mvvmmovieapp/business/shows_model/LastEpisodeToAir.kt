package com.ozanarik.mvvmmovieapp.business.shows_model


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class LastEpisodeToAir(
    @SerializedName("air_date")
    @Expose
    val airDate: String = "", // 1997-08-07
    @SerializedName("episode_number")
    @Expose
    val episodeNumber: Int = 0, // 21
    @SerializedName("episode_type")
    @Expose
    val episodeType: String = "", // finale
    @Expose
    val id: Int = 0, // 7979
    @Expose
    val name: String = "", // Apocalypse Wow!
    @Expose
    val overview: String = "", // Can Miles go through with the wedding? Will Anna be able to deal with it if he does? Will Egg and Milly's new-found happiness last? Will Ferdy finally come to terms with his sexuality?
    @SerializedName("production_code")
    @Expose
    val productionCode: String = "",
    @Expose
    val runtime: Int = 0, // 40
    @SerializedName("season_number")
    @Expose
    val seasonNumber: Int = 0, // 2
    @SerializedName("show_id")
    @Expose
    val showİd: Int = 0, // 150
    @SerializedName("still_path")
    @Expose
    val stillPath: Any? = null, // null
    @SerializedName("vote_average")
    @Expose
    val voteAverage: Double = 0.0, // 0
    @SerializedName("vote_count")
    @Expose
    val voteCount: Int = 0 // 0
)