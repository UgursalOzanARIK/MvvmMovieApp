package com.ozanarik.mvvmmovieapp.business.repository

import com.ozanarik.mvvmmovieapp.business.remote.ShowApi
import javax.inject.Inject

class ShowsRepository @Inject constructor (private val showApi: ShowApi) {


    suspend fun getAiringTodayShows()=showApi.getAiringTodayShows()

    suspend fun getOnTheAirShows()=showApi.getOnTheAirShows()

    suspend fun getPopularShows()=showApi.getPopularShows()

    suspend fun getTopRatedShows()=showApi.getTopRatedShows()


}