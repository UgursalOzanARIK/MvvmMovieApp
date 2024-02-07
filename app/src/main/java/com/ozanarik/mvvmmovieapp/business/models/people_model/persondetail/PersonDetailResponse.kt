package com.ozanarik.mvvmmovieapp.business.models.people_model.persondetail


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class PersonDetailResponse(
    @Expose
    val adult: Boolean = false, // false
    @SerializedName("also_known_as")
    @Expose
    val alsoKnownAs: List<Any> = listOf(),
    @Expose
    val biography: String = "", // Jeremy Samuel Piven (born July 26, 1965) is an American film producer and actor best known for his role as Ari Gold in the television series Entourage for which he has won three Primetime Emmy Awards as well as several other nominations for Best Supporting Actor.
    @Expose
    val birthday: String = "", // 1965-07-26
    @Expose
    val deathday: Any? = null, // null
    @Expose
    val gender: Int = 0, // 2
    @Expose
    val homepage: String = "", // https://jeremy-piven.com/
    @Expose
    val id: Int = 0, // 12799
    @SerializedName("imdb_id")
    @Expose
    val imdbİd: String = "", // nm0005315
    @SerializedName("known_for_department")
    @Expose
    val knownForDepartment: String = "", // Acting
    @Expose
    val name: String = "", // Jeremy Piven
    @SerializedName("place_of_birth")
    @Expose
    val placeOfBirth: String = "", // New York City, New York, USA
    @Expose
    val popularity: Double = 0.0, // 355.007
    @SerializedName("profile_path")
    @Expose
    val profilePath: String = "" // /pqdR8zqAWF87chGYlbdYr0YfC7g.jpg
)