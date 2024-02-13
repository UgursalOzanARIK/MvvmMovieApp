package com.ozanarik.mvvmmovieapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ozanarik.mvvmmovieapp.business.models.movie_model.movie_credits_response.MovieCreditsModel
import com.ozanarik.mvvmmovieapp.business.models.movie_detail_response.MovieDetailResponse
import com.ozanarik.mvvmmovieapp.business.models.movie_model.movie_response.MovieResponse
import com.ozanarik.mvvmmovieapp.business.models.movie_model.movie_review_response.MovieReviewModel
import com.ozanarik.mvvmmovieapp.business.models.movie_youtube_response.MovieYoutubeTrailerModel
import com.ozanarik.mvvmmovieapp.business.repository.MovieRepository
import com.ozanarik.mvvmmovieapp.utils.MovieLanguage
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

    private val _movieReviewsData:MutableStateFlow<Resource<MovieReviewModel>> = MutableStateFlow(Resource.Loading())
    val movieReviewsData:StateFlow<Resource<MovieReviewModel>> = _movieReviewsData

    private val _similarMovieData:MutableStateFlow<Resource<MovieResponse>> = MutableStateFlow(Resource.Loading())
    val similarMovieData:StateFlow<Resource<MovieResponse>> = _similarMovieData

    private val _searchedMovieData:MutableStateFlow<Resource<MovieResponse>> = MutableStateFlow(Resource.Loading())
    val searchedMovieData:StateFlow<Resource<MovieResponse>> = _searchedMovieData


    fun searchMovie(query:String) = viewModelScope.launch {
        _searchedMovieData.value = Resource.Loading()
        try {
            val searchedItemResponse = withContext(Dispatchers.IO){
                movieRepository.searchMovieOrShow(query)
            }
            if (searchedItemResponse.isSuccessful){
                _searchedMovieData.value = Resource.Success(searchedItemResponse.body()!!)
            }

        }catch (e:Exception){
            _searchedMovieData.value = Resource.Error(e.message?:e.localizedMessage!!)

        }catch (e:IOException){
            _searchedMovieData.value = Resource.Error(e.message?:e.localizedMessage!!)
        }
    }



    fun getSimilarMovies(movieId: Int)=viewModelScope.launch {

        _similarMovieData.value = Resource.Loading()

        try {
            val similarMovieResponse = withContext(Dispatchers.IO){
                movieRepository.getSimilarMovies(movieId)
            }
            if (similarMovieResponse.isSuccessful){
                _similarMovieData.value = Resource.Success(similarMovieResponse.body()!!)
            }

        }catch (e:Exception){
            _similarMovieData.value = Resource.Error(e.message?:e.localizedMessage!!)

        }catch (e:IOException){
            _similarMovieData.value = Resource.Error(e.message?:e.localizedMessage!!)
        }

    }

    fun getMovieReviews(movieId: Int)=viewModelScope.launch {
        _movieReviewsData.value = Resource.Loading()

        try {

            val movieReviewsData = withContext(Dispatchers.IO){
                movieRepository.getMovieReviews(movieId)
            }
            if (movieReviewsData.isSuccessful){
                _movieReviewsData.value = Resource.Success(movieReviewsData.body()!!)
            }

        }catch (e:Exception){
            _movieReviewsData.value = Resource.Error(e.localizedMessage?:e.message!!)

        }catch (e:IOException){
            _movieReviewsData.value = Resource.Error(e.localizedMessage?:e.message!!)
        }


    }

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

    fun getMovieLanguage(languageValue:String):String{

        return when(languageValue){

            MovieLanguage.English.language->"English"
            MovieLanguage.Russian.language->"Russian"
            MovieLanguage.Korean.language->"Korean"
            MovieLanguage.Japanese.language->"Japanese"
            MovieLanguage.Italian.language->"Italian"
            MovieLanguage.Mexican.language->"Mexican"
            MovieLanguage.French.language->"French"
            MovieLanguage.German.language->"German"
            MovieLanguage.Australia.language->"Australia"
            MovieLanguage.Aragonese.language->"Aragonese"
            MovieLanguage.Akan.language->"Akan"
            MovieLanguage.Azerbaijani.language->"Azerbaijani"
            MovieLanguage.Czech.language->"Czech"
            MovieLanguage.Afrikaans.language->"Afrikaans"
            MovieLanguage.Latin.language->"Latin"
            MovieLanguage.Turkish.language->"Turkish"
            MovieLanguage.Icelandic.language->"Icelandic"
            MovieLanguage.Thai.language->"Thai"
            MovieLanguage.Kazakh.language->"Kazakh"
            MovieLanguage.Indonesian.language->"Indonesian"
            MovieLanguage.Portuguese.language->"Portuguese"
            MovieLanguage.Dutch.language->"Dutch"
            MovieLanguage.Irish.language->"Irish"
            MovieLanguage.Hungarian.language->"Hungarian"
            MovieLanguage.Armenian.language->"Armenian"
            MovieLanguage.Polish.language->"Polish"
            MovieLanguage.Ukrainian.language->"Ukrainian"
            MovieLanguage.Persian.language->"Persian"
            MovieLanguage.Swedish.language->"Swedish"
            MovieLanguage.Tatar.language->"Tatar"
            MovieLanguage.Danish.language->"Danish"
            MovieLanguage.Turkmen.language->"Turkmen"
            MovieLanguage.Uzbek.language->"Uzbek"
            MovieLanguage.Spanish.language->"Spanish"
            MovieLanguage.Bulgarian.language->"Bulgarian"
            MovieLanguage.Macedonian.language->"Macedonian"
            MovieLanguage.Croatian.language->"Croatian"
            MovieLanguage.Belarusian.language->"Belarusian"
            MovieLanguage.Slovenian.language->"Slovenian"

            else->"N/A"
        }



    }





}