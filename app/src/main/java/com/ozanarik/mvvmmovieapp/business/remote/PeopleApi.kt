package com.ozanarik.mvvmmovieapp.business.remote

import com.ozanarik.mvvmmovieapp.business.models.people_model.allpeoplelist.PopularPeopleModel
import com.ozanarik.mvvmmovieapp.business.models.people_model.people_related_movies.PersonRelatedMoviesResponse
import com.ozanarik.mvvmmovieapp.business.models.people_model.persondetail.PersonDetailResponse
import com.ozanarik.mvvmmovieapp.utils.CONSTANTS.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PeopleApi {

    @GET("/3/person/popular")
    suspend fun getPopularPeople(
        @Query("api_key")api_key:String = API_KEY
    ):Response<PopularPeopleModel>


    @GET("/3/search/multi")
    suspend fun searchPopularPerson(
        @Query("query")query:String,
        @Query("api_key")api_key: String= API_KEY
    ):Response<PopularPeopleModel>


    @GET("/3/person/{person_id}")
    suspend fun getPersonDetail(
        @Path("person_id")person_id:Int,
        @Query("api_key")api_key:String= API_KEY
    ):Response<PersonDetailResponse>

    @GET("/3/person/{person_id}/combined_credits")
    suspend fun getPersonRelatedMovies(
        @Path("person_id")person_id:Int,
        @Query("api_key")api_key: String= API_KEY
    ):Response<PersonRelatedMoviesResponse>


}