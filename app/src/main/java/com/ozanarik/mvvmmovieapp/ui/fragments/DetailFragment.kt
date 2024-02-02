package com.ozanarik.mvvmmovieapp.ui.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.airbnb.lottie.LottieDrawable
import com.ozanarik.mvvmmovieapp.R
import com.ozanarik.mvvmmovieapp.databinding.FragmentDetailBinding
import com.ozanarik.mvvmmovieapp.ui.viewmodels.MovieViewModel
import com.ozanarik.mvvmmovieapp.utils.CONSTANTS.Companion.IMAGE_BASE_URL
import com.ozanarik.mvvmmovieapp.utils.Extensions.Companion.doubleToFloat
import com.ozanarik.mvvmmovieapp.utils.Extensions.Companion.getHoursAndMinutes
import com.ozanarik.mvvmmovieapp.utils.Extensions.Companion.setVisibility
import com.ozanarik.mvvmmovieapp.utils.Extensions.Companion.showSnackbar
import com.ozanarik.mvvmmovieapp.utils.Resource
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.DecimalFormat

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private lateinit var movieViewModel: MovieViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding = FragmentDetailBinding.inflate(inflater,container,false)
        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]


        handleMovieArgs()



        return binding.root
    }



    @SuppressLint("SetTextI18n")
    private fun handleMovieArgs(){

        val movieArgs:DetailFragmentArgs by navArgs()

        val movieData = movieArgs.movieData


        viewLifecycleOwner.lifecycleScope.launch {
            movieViewModel.getDetailedMovieData(movieData)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            movieViewModel.detailedMovieData.collect{detailedMovieResponse->
                when(detailedMovieResponse){
                    is Resource.Success->{

                        binding.movieLoadingAnim.setVisibility(false)


                        binding.tvTitleDetail.text = detailedMovieResponse.data!!.originalTitle

                        val decimalFormat = DecimalFormat("#.##")

                        binding.tvImdbAverageDetail.text = "IMDB / ${decimalFormat.format(detailedMovieResponse.data.voteAverage)}"
                        binding.tvMovieDescription.text = detailedMovieResponse.data.overview
                        Picasso.get().load(IMAGE_BASE_URL + detailedMovieResponse.data.posterPath).into(binding.imageViewMovieDetail)
                        binding.tvOriginalLanguage.text = when(detailedMovieResponse.data.originalLanguage){
                            "en"->"English"
                            else->"en"
                        }
                        val (hours,minutes) = getHoursAndMinutes(detailedMovieResponse.data.runtime)
                        binding.tvRunTime.text = "$hours h / $minutes min"

                        binding.tvTagline.text = " \"${detailedMovieResponse.data.tagline}\" "




                        binding.cvHomePage.setOnClickListener{


                            val movieHomePage = detailedMovieResponse.data.homepage.let { Uri.parse(it) }


                            val intent = Intent(Intent.ACTION_VIEW,movieHomePage)

                            val intentChooser = Intent.createChooser(intent,"Choose Action")

                            startActivity(intentChooser)


                        }
                    }
                    is Resource.Error->{
                        showSnackbar(detailedMovieResponse.message!!)
                        binding.movieLoadingAnim.setVisibility(false)
                    }
                    is Resource.Loading->{
                        binding.movieLoadingAnim.setVisibility(true)
                        binding.movieLoadingAnim.playAnimation()
                    }
                }
            }
        }
    }
}