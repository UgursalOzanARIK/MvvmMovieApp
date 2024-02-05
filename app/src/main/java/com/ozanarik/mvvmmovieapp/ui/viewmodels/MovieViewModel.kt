package com.ozanarik.mvvmmovieapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ozanarik.mvvmmovieapp.business.models.movie_model.MovieCreditsModel
import com.ozanarik.mvvmmovieapp.business.models.movie_model.MovieDetailResponse
import com.ozanarik.mvvmmovieapp.business.models.movie_model.MovieResponse
import com.ozanarik.mvvmmovieapp.business.models.movie_model.MovieYoutubeTrailerModel
import com.ozanarik.mvvmmovieapp.business.models.movie_model.ResultX
import com.ozanarik.mvvmmovieapp.business.repository.MovieRepository
import com.ozanarik.mvvmmovieapp.utils.MovieGenreType
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

    private val _detaiedMovieData:MutableStateFlow<Resource<MovieDetailResponse>> = MutableStateFlow(Resource.Loading())
    val detailedMovieData:StateFlow<Resource<MovieDetailResponse>> = _detaiedMovieData

    private val _allMovies:MutableStateFlow<Resource<MovieResponse>> = MutableStateFlow(Resource.Loading())
    val allMovies:StateFlow<Resource<MovieResponse>> = _allMovies

    private val _creditData:MutableStateFlow<Resource<MovieCreditsModel>> = MutableStateFlow(Resource.Loading())
    val creditData:StateFlow<Resource<MovieCreditsModel>> = _creditData

    private val _movieTrailerData:MutableStateFlow<Resource<MovieYoutubeTrailerModel>> = MutableStateFlow(Resource.Loading())
    val movieTrailerData:StateFlow<Resource<MovieYoutubeTrailerModel>> = _movieTrailerData

    fun getMovieTrailer(movieId: Int)=viewModelScope.launch {

        _movieTrailerData.value = Resource.Loading()

        try {

            val movieTrailerResponse = withContext(Dispatchers.IO){
                movieRepository.getMovieTrailer(movieId)
            }

            if (movieTrailerResponse.isSuccessful){
                _movieTrailerData.value = Resource.Success(movieTrailerResponse.body()!!)
            }


        }catch (e:Exception){
            _movieTrailerData.value = Resource.Error(e.localizedMessage?:e.message!!)

        }catch (e:IOException){
            _movieTrailerData.value = Resource.Error(e.localizedMessage?:e.message!!)
        }

    }

    fun getAllMovies()=viewModelScope.launch {
        _allMovies.value = Resource.Loading()

        try {

            val allMoviesResponse = withContext(Dispatchers.IO){
                movieRepository.getAllMovies()
            }
            if (allMoviesResponse.isSuccessful){
                _allMovies.value = Resource.Success(allMoviesResponse.body()!!)
            }


        }catch (e:Exception){
            _allMovies.value=Resource.Error(e.localizedMessage?:e.message!!)

        }catch (e:IOException){
            _allMovies.value=Resource.Error(e.localizedMessage?:e.message!!)
        }


    }

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

    suspend fun getDetailedMovieDataCredit(movieId:Int){
        _creditData.value = Resource.Loading()

        try {

            val detailedCreditDataResponse = withContext(Dispatchers.IO){
                movieRepository.getMovieCastAndCredits(movieId)
            }
            if (detailedCreditDataResponse.isSuccessful){
                _creditData.value =Resource.Success(detailedCreditDataResponse.body()!!)
            }

        }catch (e:Exception){
            _creditData.value = Resource.Error(e.localizedMessage?:e.message!!)
        }catch (e:IOException){
            _creditData.value = Resource.Error(e.localizedMessage?:e.message!!)
        }


    }

    suspend fun getDetailedMovieData(movieId:Int){
        _detaiedMovieData.value = Resource.Loading()

        try {

            val detailedMovieResponse = withContext(Dispatchers.IO){
                movieRepository.getMovieDetail(movieId)
            }
            if (detailedMovieResponse.isSuccessful){
                _detaiedMovieData.value =Resource.Success(detailedMovieResponse.body()!!)
            }

        }catch (e:Exception){
            _detaiedMovieData.value = Resource.Error(e.localizedMessage?:e.message!!)
        }catch (e:IOException){
            _detaiedMovieData.value = Resource.Error(e.localizedMessage?:e.message!!)
        }


    }



    fun getMovieGenreNames(genreId:Int):String{

        return when(genreId){
            MovieGenreType.Action.genreId->"Action"
            MovieGenreType.Adventure.genreId->"Adventure"
            MovieGenreType.Animation.genreId->"Adventure"
            MovieGenreType.Comedy.genreId->"Comedy"
            MovieGenreType.Crime.genreId->"Crime"
            MovieGenreType.Documentary.genreId->"Documentary"
            MovieGenreType.Drama.genreId->"Drama"
            MovieGenreType.Family.genreId->"Family"
            MovieGenreType.Fantasy.genreId->"Fantasy"
            MovieGenreType.History.genreId->"History"
            MovieGenreType.Horror.genreId->"Horror"
            MovieGenreType.Music.genreId->"Music"
            MovieGenreType.Mystery.genreId->"Mystery"
            MovieGenreType.Romance.genreId->"Romance"
            MovieGenreType.ScienceFiction.genreId->"Science Fiction"
            MovieGenreType.TvMovie.genreId->"Tv Movie"
            MovieGenreType.Thriller.genreId->"Thriller"
            MovieGenreType.War.genreId->"War"
            MovieGenreType.Western.genreId->"Western"

            else->"N/a"

        }



    }

}