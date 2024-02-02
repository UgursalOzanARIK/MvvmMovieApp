package com.ozanarik.mvvmmovieapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ozanarik.mvvmmovieapp.business.repository.ShowsRepository
import com.ozanarik.mvvmmovieapp.business.shows_model.ShowsResponse
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



}