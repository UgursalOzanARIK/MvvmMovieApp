package com.ozanarik.mvvmmovieapp.business.repository

import com.ozanarik.mvvmmovieapp.business.remote.ShowApi
import javax.inject.Inject

class ShowsRepository @Inject constructor (private val showApi: ShowApi) {


    suspend fun getAiringTodayShows()=showApi.getAiringTodayShows()

    suspend fun getOnTheAirShows()=showApi.getOnTheAirShows()

    suspend fun getPopularShows()=showApi.getPopularShows()

    suspend fun getTopRatedShows()=showApi.getTopRatedShows()

    suspend fun getShowDetail(show_id:Int)=showApi.getShowDetail(show_id)

    suspend fun getShowCredits(series_id:Int)=showApi.getShowCredits(series_id)

    suspend fun searchShow(query:String)=showApi.searchShow(query)

    suspend fun getShowYoutubeTrailer(series_id:Int)=showApi.getShowTrailer(series_id)

    suspend fun getSimilarShows(series_id: Int)=showApi.getSimilarShows(series_id)


}