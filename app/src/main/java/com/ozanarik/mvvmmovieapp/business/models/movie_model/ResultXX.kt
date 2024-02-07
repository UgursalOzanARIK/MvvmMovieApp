package com.ozanarik.mvvmmovieapp.business.models.movie_model


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class ResultXX(
    @Expose
    val author: String = "", // Goddard
    @SerializedName("author_details")
    @Expose
    val authorDetails: AuthorDetails = AuthorDetails(),
    @Expose
    val content: String = "", // Pretty awesome movie.  It shows what one crazy person can convince other crazy people to do.  Everyone needs something to believe in.  I recommend Jesus Christ, but they want Tyler Durden.
    @SerializedName("created_at")
    @Expose
    val createdAt: String = "", // 2018-06-09T17:51:53.359Z
    @Expose
    val id: String = "", // 5b1c13b9c3a36848f2026384
    @SerializedName("updated_at")
    @Expose
    val updatedAt: String = "", // 2021-06-23T15:58:09.421Z
    @Expose
    val url: String = "" // https://www.themoviedb.org/review/5b1c13b9c3a36848f2026384
)