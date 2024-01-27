package com.ozanarik.mvvmmovieapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ozanarik.mvvmmovieapp.business.model.MovieResponse
import com.ozanarik.mvvmmovieapp.business.repository.MovieRepository
import com.ozanarik.mvvmmovieapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class MovieViewModel @Inject constructor(private val movieRepository: MovieRepository):ViewModel() {

    private val _nowPlayingMovies:MutableStateFlow<Resource<MovieResponse>> = MutableStateFlow(Resource.Loading())
    val nowPlayingMovies:StateFlow<Resource<MovieResponse>> = _nowPlayingMovies

    private val _popularMovies:MutableStateFlow<Resource<MovieResponse>> = MutableStateFlow(Resource.Loading())
    val popularMovies:StateFlow<Resource<MovieResponse>> = _popularMovies

    private val _topRatedMovies:MutableStateFlow<Resource<MovieResponse>> = MutableStateFlow(Resource.Loading())
    val topRatedMovies:StateFlow<Resource<MovieResponse>> = _topRatedMovies

    private val _upcomingMovies:MutableStateFlow<Resource<MovieResponse>> = MutableStateFlow(Resource.Loading())
    val upcomingMovies:StateFlow<Resource<MovieResponse>> = _upcomingMovies

    fun getTopRatedMovies()=viewModelScope.launch {

        _topRatedMovies.value = Resource.Loading()

        try {
            val topRatedMoviesResponse = withContext(Dispatchers.IO){
                movieRepository.getTopRatedMovies()
            }

            if (topRatedMoviesResponse.isSuccessful){
                _topRatedMovies.value = Resource.Success(topRatedMoviesResponse.body()!!)
            }

        }catch (e:Exception){
            _topRatedMovies.value = Resource.Error(e.message?:e.localizedMessage)
        }catch (e:IOException){
            _topRatedMovies.value = Resource.Error(e.message?:e.localizedMessage)
        }
    }

    fun getPopularMovies()=viewModelScope.launch {

        _popularMovies.value = Resource.Loading()

        try {

            val popularMoviesResponse = withContext(Dispatchers.IO){
                movieRepository.getPopularMovies()
            }

            if (popularMoviesResponse.isSuccessful){
                _popularMovies.value = Resource.Success(popularMoviesResponse.body()!!)
            }

        }catch (e:Exception){
            _popularMovies.value = Resource.Error(e.localizedMessage?:e.message!!)
        }catch (e:IOException){
            _popularMovies.value = Resource.Error(e.localizedMessage?:e.message!!)
        }
    }

    fun getNowPlayingMovies()=viewModelScope.launch {
        _nowPlayingMovies.value = Resource.Loading()
        try {

            val nowPlayingMoviesResponse = withContext(Dispatchers.IO){
                movieRepository.getMoviesNowPlaying()
            }

            if (nowPlayingMoviesResponse.isSuccessful){
                _nowPlayingMovies.value = Resource.Success(nowPlayingMoviesResponse.body()!!)
            }

        }catch (e:Exception){
            _nowPlayingMovies.value = Resource.Error(e.message?:e.localizedMessage)
        }catch (e:IOException){
            _nowPlayingMovies.value = Resource.Error(e.message?:e.localizedMessage)
        }
    }

    fun getUpcomingMovies()=viewModelScope.launch {
        _upcomingMovies.value = Resource.Loading()
        try {

            val upcomingMovieResponse = withContext(Dispatchers.IO){
                movieRepository.getUpComingMovies()
            }

            if (upcomingMovieResponse.isSuccessful){
                _upcomingMovies.value = Resource.Success(upcomingMovieResponse.body()!!)
            }

        }catch (e:Exception){
            _upcomingMovies.value =Resource.Error(e.localizedMessage?:e.message!!)

        }catch (e:IOException){
            _upcomingMovies.value =Resource.Error(e.localizedMessage?:e.message!!)
        }


    }

}