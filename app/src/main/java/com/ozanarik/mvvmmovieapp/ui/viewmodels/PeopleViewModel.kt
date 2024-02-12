package com.ozanarik.mvvmmovieapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ozanarik.mvvmmovieapp.business.models.people_model.allpeoplelist.PopularPeopleModel
import com.ozanarik.mvvmmovieapp.business.models.people_model.people_related_movies.PersonRelatedMoviesResponse
import com.ozanarik.mvvmmovieapp.business.models.people_model.persondetail.PersonDetailResponse
import com.ozanarik.mvvmmovieapp.business.repository.PopularPeopleRepository
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
class PeopleViewModel @Inject constructor(private val peopleRepository: PopularPeopleRepository) :ViewModel() {

    private val _popularPeopleList:MutableStateFlow<Resource<PopularPeopleModel>> = MutableStateFlow(Resource.Loading())
    val popularPeopleList:StateFlow<Resource<PopularPeopleModel>> = _popularPeopleList

    private val _searchedValue:MutableStateFlow<Resource<PopularPeopleModel>> = MutableStateFlow(Resource.Loading())
    val searchedPerson:StateFlow<Resource<PopularPeopleModel>> = _searchedValue

    private val _personDetail:MutableStateFlow<Resource<PersonDetailResponse>> = MutableStateFlow(Resource.Loading())
    val personDetail:StateFlow<Resource<PersonDetailResponse>> = _personDetail

    private val _personRelatedMovie:MutableStateFlow<Resource<PersonRelatedMoviesResponse>> = MutableStateFlow(Resource.Loading())
    val personRelatedMovie:StateFlow<Resource<PersonRelatedMoviesResponse>> = _personRelatedMovie

    fun getPersonRelatedMovies(movie_id:Int)=viewModelScope.launch {

        _personRelatedMovie.value = Resource.Loading()
        try {

            val personRelatedMovieResponse = withContext(Dispatchers.IO){
                peopleRepository.getPersonRelatedMovies(movie_id)
            }
            if (personRelatedMovieResponse.isSuccessful){
                _personRelatedMovie.value = Resource.Success(personRelatedMovieResponse.body()!!)
            }

        }catch (e:Exception){
            _personRelatedMovie.value = Resource.Error(e.message?:e.localizedMessage!!)

        }catch (e:IOException){
            _personRelatedMovie.value = Resource.Error(e.message?:e.localizedMessage!!)
        }

    }

    fun getPersonDetail(person_id:Int)=viewModelScope.launch {

        _personDetail.value = Resource.Loading()

        try {
            val personDetailResponse = withContext(Dispatchers.IO){
                peopleRepository.getPersonDetail(person_id)
            }

            if (personDetailResponse.isSuccessful){
                _personDetail.value = Resource.Success(personDetailResponse.body()!!)
            }


        }catch (e:Exception){
            _personDetail.value = Resource.Error(e.message?:e.localizedMessage!!)

        }catch (e:IOException){
            _personDetail.value = Resource.Error(e.message?:e.localizedMessage!!)
        }
    }

    fun searchPopularPerson(query:String)=viewModelScope.launch {

        _searchedValue.value = Resource.Loading()

        try {

            val searchedPersonResponse = withContext(Dispatchers.IO){
                peopleRepository.searchPopularPerson(query)
            }
            if (searchedPersonResponse.isSuccessful){
                _searchedValue.value = Resource.Success(searchedPersonResponse.body()!!)
            }


        }catch (e:Exception){
            _searchedValue.value = Resource.Error(e.message?:e.localizedMessage!!)

        }catch (e:IOException){
            _searchedValue.value = Resource.Error(e.message?:e.localizedMessage!!)
        }
    }

    fun getPopularPeople() = viewModelScope.launch {

        _popularPeopleList.value = Resource.Loading()

        try {

            val popularPeopleListResponse = withContext(Dispatchers.IO){
                peopleRepository.getPopularPeople()
            }
            if (popularPeopleListResponse.isSuccessful){
                _popularPeopleList.value = Resource.Success(popularPeopleListResponse.body()!!)
            }

        }catch (e:Exception){
            _popularPeopleList.value = Resource.Error(e.message?:e.localizedMessage!!)

        }catch (e:IOException){
            _popularPeopleList.value = Resource.Error(e.message?:e.localizedMessage!!)
        }
    }
}