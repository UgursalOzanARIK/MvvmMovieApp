package com.ozanarik.mvvmmovieapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ozanarik.mvvmmovieapp.business.model.MovieResponse
import com.ozanarik.mvvmmovieapp.business.model.Result
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

    private val _movieResponse:MutableStateFlow<Resource<MovieResponse>> = MutableStateFlow(Resource.Loading())
    val movieResponse:StateFlow<Resource<MovieResponse>> = _movieResponse



    fun getNowPlayingMovies()=viewModelScope.launch {


        _movieResponse.value = Resource.Loading()

        try {

            val movieResponse = withContext(Dispatchers.IO){
                movieRepository.getMoviesNowPlaying()
            }

            if (movieResponse.isSuccessful){
                _movieResponse.value = Resource.Success(movieResponse.body()!!)
            }

        }catch (e:Exception){
            _movieResponse.value = Resource.Error(e.message?:e.localizedMessage)
        }catch (e:IOException){
            _movieResponse.value = Resource.Error(e.message?:e.localizedMessage)
        }

    }

}