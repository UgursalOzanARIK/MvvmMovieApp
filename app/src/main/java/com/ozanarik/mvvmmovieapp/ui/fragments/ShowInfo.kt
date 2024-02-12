package com.ozanarik.mvvmmovieapp.ui.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ozanarik.mvvmmovieapp.databinding.FragmentShowInfoBinding
import com.ozanarik.mvvmmovieapp.utils.Extensions.Companion.formatEpisodeTime
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat

@AndroidEntryPoint
class ShowInfo : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentShowInfoBinding
    override fun onCreateView(


        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = FragmentShowInfoBinding.inflate(inflater,container,false)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))



        handleDialogFragment()

        // Inflate the layout for this fragment
        return binding.root
    }



    @SuppressLint("SetTextI18n")
    private fun handleDialogFragment(){

        val args:ShowInfoArgs by navArgs()

        val showInfoDetail = args.showEpisodeDetailData


        binding.apply {
            tvLastEpisodeDateDetail.text = "Last episode date : ${showInfoDetail.lastAirDate}"
            tvEpisodeTypeDetail.text = "Type : ${showInfoDetail.type}"
            tvEpisodeTagline.text = showInfoDetail.tagline
            val decimalFormat= DecimalFormat("#.##")
            tvVoteAverageDetail.text = "Imdb Vote Average : ${decimalFormat.format(showInfoDetail.voteAverage)}"
            tvOverViewDetail.text = showInfoDetail.overview

            val productionCompanies = showInfoDetail.productionCompanies.joinToString(", "){it?.name.toString()}


            tvShowProductionCompanies.text = "Production Companies : $productionCompanies"


        }
    }


}