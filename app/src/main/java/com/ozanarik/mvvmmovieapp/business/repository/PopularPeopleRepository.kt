package com.ozanarik.mvvmmovieapp.business.repository

import com.ozanarik.mvvmmovieapp.business.remote.PeopleApi
import javax.inject.Inject

class PopularPeopleRepository @Inject constructor(private val peopleApi: PeopleApi) {


    suspend fun getPopularPeople()=peopleApi.getPopularPeople()

    suspend fun searchPopularPerson(query:String)=peopleApi.searchPopularPerson(query)

    suspend fun getPersonDetail(person_id:Int)=peopleApi.getPersonDetail(person_id)

    suspend fun getPersonRelatedMovies(movieId: Int)=peopleApi.getPersonRelatedMovies(movieId)


}