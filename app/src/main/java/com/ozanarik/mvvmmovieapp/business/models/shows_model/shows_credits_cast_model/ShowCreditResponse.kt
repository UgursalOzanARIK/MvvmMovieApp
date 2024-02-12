package com.ozanarik.mvvmmovieapp.business.models.shows_model.shows_credits_cast_model


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class ShowCreditResponse(
    @Expose
    val cast: List<Cast> = listOf(),
    @Expose
    val crew: List<Crew> = listOf(),
    @Expose
    val id: Int = 0 // 1396
)