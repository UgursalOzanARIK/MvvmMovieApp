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

}