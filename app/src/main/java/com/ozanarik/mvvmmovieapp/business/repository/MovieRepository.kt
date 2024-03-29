package com.ozanarik.mvvmmovieapp.business.repository

import com.ozanarik.mvvmmovieapp.business.remote.MovieApi
import com.ozanarik.mvvmmovieapp.utils.CONSTANTS.Companion.API_KEY
import javax.inject.Inject

class MovieRepository @Inject constructor(private val movieApi: MovieApi) {



    suspend fun getMoviesNowPlaying()=movieApi.getNowPlayingMovies()

    suspend fun getPopularMovies()=movieApi.getPopularMovies()

    suspend fun getTopRatedMovies()=movieApi.getTopRatedMovies()

    suspend fun getUpComingMovies()=movieApi.getUpComingMovies()

    suspend fun getMovieDetail(movieId:Int)=movieApi.getMovieDetail(movieId)

    suspend fun getAllMovies()=movieApi.getAllMovies()

    suspend fun getMovieCastAndCredits(movieId: Int)=movieApi.getMovieCastAndCredits(movieId)

    suspend fun getMovieTrailer(movieId: Int)=movieApi.getMovieTrailer(movieId)

    suspend fun getMovieReviews(movieId: Int)=movieApi.getMovieReviews(movieId)

    suspend fun getSimilarMovies(movieId: Int)=movieApi.getSimilarMovies(movieId)

    suspend fun searchMovieOrShow(query:String)=movieApi.searchMovie(query)



}