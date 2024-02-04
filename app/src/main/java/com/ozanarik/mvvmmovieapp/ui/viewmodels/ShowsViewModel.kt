package com.ozanarik.mvvmmovieapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ozanarik.mvvmmovieapp.business.repository.ShowsRepository
import com.ozanarik.mvvmmovieapp.business.models.shows_model.ShowDetailModel
import com.ozanarik.mvvmmovieapp.business.models.shows_model.ShowsResponse
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
}