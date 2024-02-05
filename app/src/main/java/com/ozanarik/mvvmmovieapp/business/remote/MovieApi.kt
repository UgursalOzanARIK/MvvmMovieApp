package com.ozanarik.mvvmmovieapp.business.remote

import com.ozanarik.mvvmmovieapp.business.models.movie_model.MovieCreditsModel
import com.ozanarik.mvvmmovieapp.business.models.movie_model.MovieDetailResponse
import com.ozanarik.mvvmmovieapp.business.models.movie_model.MovieResponse
import com.ozanarik.mvvmmovieapp.business.models.movie_model.MovieYoutubeTrailerModel
import com.ozanarik.mvvmmovieapp.business.models.movie_model.ResultX
import com.ozanarik.mvvmmovieapp.utils.CONSTANTS.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("3/movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("api_key")api_key:String=API_KEY):Response<MovieResponse>

    @GET("3/movie/popular")
    suspend fun getPopularMovies(@Query("api_key")api_key:String = API_KEY):Response<MovieResponse>

    @GET("3/movie/top_rated")
    suspend fun getTopRatedMovies(@Query("api_key")api_key:String = API_KEY):Response<MovieResponse>

    @GET("3/movie/upcoming")
    suspend fun getUpComingMovies(@Query("api_key")api_key:String = API_KEY):Response<MovieResponse>

    @GET("3/movie/{movie_id}")
    suspend fun getMovieDetail(@Path("movie_id")movie_id:Int,@Query("api_key")api_key: String= API_KEY):Response<MovieDetailResponse>

    @GET("3/movie/{movie_id}/credits")
    suspend fun getMovieCastAndCredits(@Path("movie_id")movie_id:Int,@Query("api_key")api_key: String= API_KEY):Response<MovieCreditsModel>

    @GET("/3/discover/movie")
    suspend fun getAllMovies(
        @Query("api_key")api_key:String= API_KEY
    ):Response<MovieResponse>

    @GET("/3/movie/{movie_id}/videos")
    suspend fun getMovieTrailer(
        @Path("movie_id")movie_id:Int,
        @Query("api_key")api_key:String = API_KEY
    ):Response<MovieYoutubeTrailerModel>




}