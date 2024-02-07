package com.ozanarik.mvvmmovieapp.ui.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ozanarik.mvvmmovieapp.databinding.FragmentLastEpisodeBinding
import com.ozanarik.mvvmmovieapp.utils.Extensions.Companion.formatEpisodeTime
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat

@AndroidEntryPoint
class LastEpisodeFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentLastEpisodeBinding
    override fun onCreateView(


        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = FragmentLastEpisodeBinding.inflate(inflater,container,false)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))



        handleDialogFragment()

        // Inflate the layout for this fragment
        return binding.root
    }



    @SuppressLint("SetTextI18n")
    private fun handleDialogFragment(){

        val args:LastEpisodeFragmentArgs by navArgs()

        val showEpisodeDetail = args.showEpisodeDetailData



        binding.apply {
                tvLastEpisodeDateDetail.text = "Last episode date : ${showEpisodeDetail.lastAirDate}"
                tvEpisodeTypeDetail.text = "Type : ${showEpisodeDetail.type}"
                tvEpisodeRunTimeDetail.text = showEpisodeDetail.episodeRunTime.formatEpisodeTime()
                tvSeasonNumberDetail.text = "Seasons : ${showEpisodeDetail.numberOfSeasons}"
                tvEpisodeTagline.text = showEpisodeDetail.tagline
                val decimalFormat= DecimalFormat("#.##")
                tvVoteAverageDetail.text = "Imdb Vote Average : ${decimalFormat.format(showEpisodeDetail.voteAverage)}"
                tvOverViewDetail.text = showEpisodeDetail.overview
        }
    }


}