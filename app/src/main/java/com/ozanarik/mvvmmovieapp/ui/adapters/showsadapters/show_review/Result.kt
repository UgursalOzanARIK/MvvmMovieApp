package com.ozanarik.mvvmmovieapp.ui.adapters.showsadapters.show_review


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Result(
    @Expose
    val author: String = "", // slyone10001
    @SerializedName("author_details")
    @Expose
    val authorDetails: AuthorDetails = AuthorDetails(),
    @Expose
    val content: String = "", // Wow....where to start. Not really into "DRUG" inspired shows. But this one had me from the start. The only bad about this show was the split seasons when it was a first run show. But now you can go right through to the next episode with out having to wait.....MUST WATCH ! !
    @SerializedName("created_at")
    @Expose
    val createdAt: String = "", // 2018-04-10T15:44:38.134Z
    @Expose
    val id: String = "", // 5accdbe6c3a3687e2702d058
    @SerializedName("updated_at")
    @Expose
    val updatedAt: String = "", // 2021-06-23T15:58:07.601Z
    @Expose
    val url: String = "" // https://www.themoviedb.org/review/5accdbe6c3a3687e2702d058
)