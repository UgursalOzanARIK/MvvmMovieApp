package com.ozanarik.mvvmmovieapp.business.remote

import com.ozanarik.mvvmmovieapp.business.models.shows_model.ShowDetailModel
import com.ozanarik.mvvmmovieapp.business.models.shows_model.ShowsResponse
import com.ozanarik.mvvmmovieapp.business.models.shows_model.show_youtube_trailer_response.ShowsYoutubeTrailerModel
import com.ozanarik.mvvmmovieapp.business.models.shows_model.shows_credits_cast_model.ShowCreditResponse
import com.ozanarik.mvvmmovieapp.ui.adapters.showsadapters.show_review.ShowReviewResponse
import com.ozanarik.mvvmmovieapp.utils.CONSTANTS.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ShowApi {


    @GET("/3/tv/airing_today")
    suspend fun getAiringTodayShows(
        @Query("api_key")api_key:String = API_KEY
    ):Response<ShowsResponse>

    @GET("/3/tv/on_the_air")
    suspend fun getOnTheAirShows(
        @Query("api_key")api_key: String= API_KEY
    ):Response<ShowsResponse>

    @GET("3/tv/popular")
    suspend fun getPopularShows(
        @Query("api_key")api_key:String = API_KEY
    ):Response<ShowsResponse>

    @GET("3/tv/top_rated")
    suspend fun getTopRatedShows(
        @Query("api_key")api_key: String = API_KEY
    ):Response<ShowsResponse>

    @GET("3/tv/{series_id}")
    suspend fun getShowDetail(
        @Path("series_id")series_id:Int,
        @Query("api_key")api_key:String=API_KEY
    ):Response<ShowDetailModel>

    @GET("/3/tv/{series_id}/credits")
    suspend fun getShowCredits(
        @Path("series_id")series_id:Int,
        @Query("api_key")api_key:String=API_KEY
    ):Response<ShowCreditResponse>

    @GET("/3/search/tv")
    suspend fun searchShow(
        @Query("query")query:String,
        @Query("api_key")api_key: String= API_KEY
    ):Response<ShowsResponse>

    @GET("/3/tv/{series_id}/videos")
    suspend fun getShowTrailer(
        @Path("series_id")series_id:Int,
        @Query("api_key")api_key: String = API_KEY
    ):Response<ShowsYoutubeTrailerModel>

    @GET("3/tv/{series_id}/similar")
    suspend fun getSimilarShows(
        @Path("series_id")series_id: Int,
        @Query("api_key")api_key: String = API_KEY
    ):Response<ShowsResponse>

    @GET("/3/tv/{series_id}/reviews")
    suspend fun getShowReviews(
        @Path("series_id")series_id:Int,
        @Query("api_key")api_key:String= API_KEY
    ):Response<ShowReviewResponse>


}