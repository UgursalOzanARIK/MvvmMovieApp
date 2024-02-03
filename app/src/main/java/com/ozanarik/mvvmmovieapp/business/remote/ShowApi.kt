package com.ozanarik.mvvmmovieapp.business.remote

import com.ozanarik.mvvmmovieapp.business.shows_model.ShowDetailModel
import com.ozanarik.mvvmmovieapp.business.shows_model.ShowsResponse
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

}