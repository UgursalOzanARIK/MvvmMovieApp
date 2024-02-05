package com.ozanarik.mvvmmovieapp.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
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
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ozanarik.mvvmmovieapp.R
import com.ozanarik.mvvmmovieapp.databinding.FragmentDetailBinding
import com.ozanarik.mvvmmovieapp.ui.adapters.movieadapter.MovieYoutubeTrailerAdapter
import com.ozanarik.mvvmmovieapp.ui.adapters.moviecreditadapter.MovieCreditAdapter
import com.ozanarik.mvvmmovieapp.ui.viewmodels.MovieViewModel
import com.ozanarik.mvvmmovieapp.utils.CONSTANTS.Companion.IMAGE_BASE_URL
import com.ozanarik.mvvmmovieapp.utils.Extensions.Companion.getHoursAndMinutes
import com.ozanarik.mvvmmovieapp.utils.Extensions.Companion.showSnackbar
import com.ozanarik.mvvmmovieapp.utils.Resource
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.DecimalFormat

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private lateinit var movieViewModel: MovieViewModel

    private lateinit var movieYoutubeTrailerAdapter: MovieYoutubeTrailerAdapter
    private lateinit var movieCreditAdapter: MovieCreditAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding = FragmentDetailBinding.inflate(inflater,container,false)
        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]





        handleMovieArgs()
        handleRv()
        getMovieYoutubeTrailer()

        getMovieCredit()


        return binding.root
    }



    private fun getMovieYoutubeTrailer(){

        val movieArgs:MovieDetailFragmentArgs by navArgs()

        val movieData = movieArgs.movieData

        movieViewModel.getMovieTrailer(movieData)

        viewLifecycleOwner.lifecycleScope.launch {

            movieViewModel.movieTrailerData.collect{movieTrailerResponse->
                when(movieTrailerResponse){
                    is Resource.Success->{


                        Log.e("youtubekey",movieTrailerResponse.data!!.results.first().key)

                        movieYoutubeTrailerAdapter.asyncDifferList.submitList(movieTrailerResponse.data!!.results)

                    }
                    is Resource.Error->{
                        Log.e("asd","error")
                    }
                    is Resource.Loading->{
                        Log.e("asd","loading")
                    }
                }
            }

        }



    }



    @SuppressLint("SetTextI18n")
    private fun handleMovieArgs(){


        val movieArgs:MovieDetailFragmentArgs by navArgs()

        val movieData = movieArgs.movieData

        viewLifecycleOwner.lifecycleScope.launch {
            movieViewModel.getDetailedMovieData(movieData)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            movieViewModel.detailedMovieData.collect{detailedMovieResponse->
                when(detailedMovieResponse){
                    is Resource.Success->{

                        binding.tvTitleDetail.text = detailedMovieResponse.data!!.originalTitle

                        val decimalFormat = DecimalFormat("#.##")

                        binding.tvImdbAverageDetail.text = "IMDB / ${decimalFormat.format(detailedMovieResponse.data.voteAverage)}"
                        binding.tvMovieDescription.text = detailedMovieResponse.data.overview
                        Picasso.get().load(IMAGE_BASE_URL + detailedMovieResponse.data.posterPath).placeholder(R.drawable.placeholder).into(binding.imageViewMovieDetail)

                        binding.tvOriginalLanguage.text = when(detailedMovieResponse.data.originalLanguage){
                            "en"->"English"
                            "de"->"German"
                            "ja"->"Japanese"
                            "es"->"Spanish"
                            else->"en"
                        }
                        val (hours,minutes) = getHoursAndMinutes(detailedMovieResponse.data.runtime)
                        binding.tvRunTime.text = "$hours h / $minutes min"
                        binding.tvTagline.text = " \"${detailedMovieResponse.data.tagline}\" "

                        val genreNames = detailedMovieResponse.data.genres.joinToString(", "){it.name}

                        binding.tvGenre.text = genreNames



                        binding.cvHomePage.setOnClickListener{


                            val movieHomePage = detailedMovieResponse.data.homepage.let { Uri.parse(it) }


                            val intent = Intent(Intent.ACTION_VIEW,movieHomePage)

                            val intentChooser = Intent.createChooser(intent,"Choose Action")

                            startActivity(intentChooser)


                        }
                    }
                    is Resource.Error->{
                        showSnackbar(detailedMovieResponse.message!!)
                    }
                    is Resource.Loading->{
                    }
                }
            }
        }
    }


    private fun getMovieCredit(){
        val movieArgs:MovieDetailFragmentArgs by navArgs()

        val movieData = movieArgs.movieData


        viewLifecycleOwner.lifecycleScope.launch {
            movieViewModel.getDetailedMovieDataCredit(movieData)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            movieViewModel.creditData.collect{creditData->
                when(creditData){
                    is Resource.Success->{

                        val maxActorsAndActresses = 4

                        val cast = creditData.data!!.cast.take(maxActorsAndActresses).joinToString(", "){it.name}

                        binding.tvCast.text = "Cast : $cast"


                        movieCreditAdapter.asyncDifferList.submitList(creditData.data.cast)

                    }
                    is Resource.Error->{
                        Log.e("asd","error")
                    }
                    is Resource.Loading->{
                        Log.e("asd","loading")
                    }
                }
            }
        }
    }


    private fun handleRv(){
        movieCreditAdapter = MovieCreditAdapter()
        movieYoutubeTrailerAdapter = MovieYoutubeTrailerAdapter()

        binding.apply {

            rvCast.adapter = movieCreditAdapter
            rvCast.layoutManager = StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL)
            rvCast.setHasFixedSize(true)

            rvMovieYoutubeTrailer.adapter = movieYoutubeTrailerAdapter
            rvMovieYoutubeTrailer.layoutManager = StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL)
            rvMovieYoutubeTrailer.setHasFixedSize(true)


        }

    }


}