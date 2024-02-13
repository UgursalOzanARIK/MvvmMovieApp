package com.ozanarik.mvvmmovieapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ozanarik.mvvmmovieapp.business.repository.ShowsRepository
import com.ozanarik.mvvmmovieapp.business.models.shows_model.ShowDetailModel
import com.ozanarik.mvvmmovieapp.business.models.shows_model.ShowsResponse
import com.ozanarik.mvvmmovieapp.business.models.shows_model.show_youtube_trailer_response.ShowsYoutubeTrailerModel
import com.ozanarik.mvvmmovieapp.business.models.shows_model.shows_credits_cast_model.ShowCreditResponse
import com.ozanarik.mvvmmovieapp.ui.adapters.showsadapters.show_review.ShowReviewResponse
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
class ShowsViewModel @Inject constructor(private val showsRepository: ShowsRepository):ViewModel() {

    private val _topRatedShows:MutableStateFlow<Resource<ShowsResponse>> = MutableStateFlow(Resource.Loading())
    val topRatedShows:StateFlow<Resource<ShowsResponse>> = _topRatedShows

    private val _airingShows:MutableStateFlow<Resource<ShowsResponse>> = MutableStateFlow(Resource.Loading())
    val airingShows:StateFlow<Resource<ShowsResponse>> = _airingShows

    private val _onTheAirShows:MutableStateFlow<Resource<ShowsResponse>> = MutableStateFlow(Resource.Loading())
    val onTheAirShows:StateFlow<Resource<ShowsResponse>> = _onTheAirShows

    private val _popularShows:MutableStateFlow<Resource<ShowsResponse>> = MutableStateFlow(Resource.Loading())
    val popularShows:StateFlow<Resource<ShowsResponse>> = _popularShows

    private val _detailedShowData:MutableStateFlow<Resource<ShowDetailModel>> = MutableStateFlow(Resource.Loading())
    val detailedShowData:StateFlow<Resource<ShowDetailModel>> = _detailedShowData

    private val _showCreditData:MutableStateFlow<Resource<ShowCreditResponse>> = MutableStateFlow(Resource.Loading())
    val showCreditData:StateFlow<Resource<ShowCreditResponse>> = _showCreditData

    private val _searchedShowData:MutableStateFlow<Resource<ShowsResponse>> = MutableStateFlow(Resource.Loading())
    val searchedShowData:StateFlow<Resource<ShowsResponse>> = _searchedShowData

    private val _showYoutubeTrailerData:MutableStateFlow<Resource<ShowsYoutubeTrailerModel>> = MutableStateFlow(Resource.Loading())
    val showYoutubeTrailerData:StateFlow<Resource<ShowsYoutubeTrailerModel>> = _showYoutubeTrailerData

    private val _similarShowData:MutableStateFlow<Resource<ShowsResponse>> = MutableStateFlow(Resource.Loading())
    val similarShowData:StateFlow<Resource<ShowsResponse>> = _similarShowData


    private val _showReviewData:MutableStateFlow<Resource<ShowReviewResponse>> = MutableStateFlow(Resource.Loading())
    val showReviewData:StateFlow<Resource<ShowReviewResponse>> = _showReviewData


    fun getShowReviews(series_id: Int)=viewModelScope.launch {
        _showReviewData.value = Resource.Loading()

        try {

            val showReviewResponse = withContext(Dispatchers.IO){
                showsRepository.getShowReviews(series_id)
            }
            if (showReviewResponse.isSuccessful){
                _showReviewData.value = Resource.Success(showReviewResponse.body()!!)
            }

        }catch (e:Exception){
            _showReviewData.value = Resource.Error(e.localizedMessage?:e.message!!)

        }catch (e:IOException){
            _showReviewData.value = Resource.Error(e.localizedMessage?:e.message!!)
        }

    }


    fun getSimilarShows(series_id: Int)=viewModelScope.launch {

        try {

            val similarShowResponse = withContext(Dispatchers.IO){
                showsRepository.getSimilarShows(series_id)
            }
            if (similarShowResponse.isSuccessful){
                _similarShowData.value = Resource.Success(similarShowResponse.body()!!)
            }


        }catch (e:Exception){
            _similarShowData.value = Resource.Error(e.localizedMessage?:e.message!!)

        }catch (e:IOException){
            _similarShowData.value = Resource.Error(e.localizedMessage?:e.message!!)
        }

    }


    fun getShowYoutubeTrailer(series_id: Int)=viewModelScope.launch {

        _showYoutubeTrailerData.value = Resource.Loading()
        try {
            val youtubeTrailerResponse = withContext(Dispatchers.IO){
                showsRepository.getShowYoutubeTrailer(series_id)
            }
            if (youtubeTrailerResponse.isSuccessful){
                _showYoutubeTrailerData.value = Resource.Success(youtubeTrailerResponse.body()!!)
            }

        }catch (e:Exception){
            _showYoutubeTrailerData.value = Resource.Error(e.localizedMessage?:e.message!!)

        }catch (e:IOException){
            _showYoutubeTrailerData.value = Resource.Error(e.localizedMessage?:e.message!!)
        }

    }



    fun searchShow(query:String)=viewModelScope.launch {

        _searchedShowData.value = Resource.Loading()
        try {
            val searchedShowResponse = withContext(Dispatchers.IO){
                showsRepository.searchShow(query)
            }
            if (searchedShowResponse.isSuccessful){
                _searchedShowData.value = Resource.Success(searchedShowResponse.body()!!)
            }

        }catch (e:Exception){
            _searchedShowData.value = Resource.Error(e.localizedMessage?:e.message!!)

        }catch (e:IOException){
            _searchedShowData.value = Resource.Error(e.localizedMessage?:e.message!!)
        }

    }

    fun getShowCredit(series_id:Int)=viewModelScope.launch {

        _showCreditData.value = Resource.Loading()

        try {
            val showCreditResponse = withContext(Dispatchers.IO){
                showsRepository.getShowCredits(series_id)
            }
            if (showCreditResponse.isSuccessful){
                _showCreditData.value = Resource.Success(showCreditResponse.body()!!)
            }

        }catch (e:Exception){
            _showCreditData.value = Resource.Error(e.message?:e.localizedMessage!!)

        }catch (e:IOException){
            _showCreditData.value = Resource.Error(e.message?:e.localizedMessage!!)
        }


    }


    fun getOnTheAirShows()=viewModelScope.launch {

        _onTheAirShows.value = Resource.Loading()


        try {

            val topRatedShowsResponse = withContext(Dispatchers.IO){
                showsRepository.getOnTheAirShows()
            }
            if (topRatedShowsResponse.isSuccessful){
                _onTheAirShows.value = Resource.Success(topRatedShowsResponse.body()!!)
            }

        }catch (e:Exception){
            _onTheAirShows.value = Resource.Error(e.localizedMessage?:e.message!!)
        }catch (e:IOException){
            _onTheAirShows.value = Resource.Error(e.localizedMessage?:e.message!!)
        }



    }


    fun getPopularShows()=viewModelScope.launch {

        _popularShows.value = Resource.Loading()

        try {

            val topRatedShowsResponse = withContext(Dispatchers.IO){
                showsRepository.getPopularShows()
            }
            if (topRatedShowsResponse.isSuccessful){
                _popularShows.value = Resource.Success(topRatedShowsResponse.body()!!)
            }

        }catch (e:Exception){
            _popularShows.value = Resource.Error(e.localizedMessage?:e.message!!)
        }catch (e:IOException){
            _popularShows.value = Resource.Error(e.localizedMessage?:e.message!!)
        }



    }

    fun getAiringTodayShows()=viewModelScope.launch {

        _airingShows.value = Resource.Loading()

        try {

            val airingTodayResponse = withContext(Dispatchers.IO){
                showsRepository.getAiringTodayShows()
            }
            if (airingTodayResponse.isSuccessful){
                _airingShows.value = Resource.Success(airingTodayResponse.body()!!)
            }

        }catch (e:Exception){
            _airingShows.value = Resource.Error(e.localizedMessage?:e.message!!)
        }catch (e:IOException){
            _airingShows.value = Resource.Error(e.localizedMessage?:e.message!!)
        }
    }



    fun getTopRatedShows()=viewModelScope.launch {

        _topRatedShows.value = Resource.Loading()


        try {

            val topRatedShowsResponse = withContext(Dispatchers.IO){
                showsRepository.getTopRatedShows()
            }
            if (topRatedShowsResponse.isSuccessful){
                _topRatedShows.value = Resource.Success(topRatedShowsResponse.body()!!)
            }

        }catch (e:Exception){
            _topRatedShows.value = Resource.Error(e.localizedMessage?:e.message!!)
        }catch (e:IOException){
            _topRatedShows.value = Resource.Error(e.localizedMessage?:e.message!!)
        }
    }

    suspend fun getDetailedShowData(showId:Int){

        _detailedShowData.value = Resource.Loading()


        try {

            val detailedShowResponse = withContext(Dispatchers.IO){
                showsRepository.getShowDetail(showId)
            }

            if (detailedShowResponse.isSuccessful){
                _detailedShowData.value = Resource.Success(detailedShowResponse.body()!!)
            }
        }catch (e:Exception){
            _detailedShowData.value = Resource.Error(e.localizedMessage?:e.message!!)

        }catch (e:IOException){
            _detailedShowData.value = Resource.Error(e.localizedMessage?:e.message!!)
        }
    }

    fun getMovieLanguage(languageValue:String):String {

        return when (languageValue) {

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


            else -> "N/A"
        }
    }
}