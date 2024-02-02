package com.ozanarik.mvvmmovieapp.business.repository

import com.ozanarik.mvvmmovieapp.business.remote.MovieApi
import javax.inject.Inject

class MovieRepository @Inject constructor(private val movieApi: MovieApi) {



    suspend fun getMoviesNowPlaying()=movieApi.getNowPlayingMovies()

    suspend fun getPopularMovies()=movieApi.getPopularMovies()

    suspend fun getTopRatedMovies()=movieApi.getTopRatedMovies()

    suspend fun getUpComingMovies()=movieApi.getUpComingMovies()

    suspend fun getMovieDetail(movieId:Int)=movieApi.getMovieDetail(movieId)


}