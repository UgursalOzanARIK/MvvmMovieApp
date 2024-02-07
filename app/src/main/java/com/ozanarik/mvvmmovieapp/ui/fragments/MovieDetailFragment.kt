package com.ozanarik.mvvmmovieapp.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ozanarik.mvvmmovieapp.R
import com.ozanarik.mvvmmovieapp.business.models.movie_model.Cast
import com.ozanarik.mvvmmovieapp.business.models.movie_model.Result
import com.ozanarik.mvvmmovieapp.databinding.FragmentDetailBinding
import com.ozanarik.mvvmmovieapp.ui.adapters.movieadapter.SimilarMoviesAdapter
import com.ozanarik.mvvmmovieapp.ui.adapters.moviecreditadapter.MovieCreditAdapter
import com.ozanarik.mvvmmovieapp.ui.viewmodels.MovieViewModel
import com.ozanarik.mvvmmovieapp.utils.CONSTANTS.Companion.IMAGE_BASE_URL
import com.ozanarik.mvvmmovieapp.utils.CONSTANTS.Companion.YOUTUBE_TRAILER_BASE_URL
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

    private lateinit var movieCreditAdapter: MovieCreditAdapter
    private lateinit var similarMoviesAdapter: SimilarMoviesAdapter


    @RequiresApi(Build.VERSION_CODES.O)
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

        handleReviewFragmentNavigation()


        getSimilarMovies()





        return binding.root
    }


    private fun handleReviewFragmentNavigation(){

        val movieArgs:MovieDetailFragmentArgs by navArgs()

        val movieData = movieArgs.movieData
        binding.tvReviews.setOnClickListener {



            val bundle = Bundle().apply {
                putInt("movieData",movieData)
            }

            val movieReviewFragment = MovieReviewsBottomSheetFragment()
            movieReviewFragment.arguments = bundle


            movieReviewFragment.show(childFragmentManager,movieReviewFragment.tag)

        }
    }

    private fun getSimilarMovies(){

        val movieArgs:MovieDetailFragmentArgs by navArgs()

        val movieData = movieArgs.movieData

        movieViewModel.getSimilarMovies(movieData)
        viewLifecycleOwner.lifecycleScope.launch {
            movieViewModel.similarMovieData.collect{similarMovieResponse->
                when(similarMovieResponse){
                    is Resource.Success->{

                        similarMoviesAdapter.asyncDifferList.submitList(similarMovieResponse.data!!.results)


                    }
                    is Resource.Error->{
                        showSnackbar(similarMovieResponse.message!!)
                    }
                    is Resource.Loading->{
                        showSnackbar("Fetching Data")
                    }
                }
            }
        }

    }



    @RequiresApi(Build.VERSION_CODES.O)
    private fun getMovieYoutubeTrailer(){

        val movieArgs:MovieDetailFragmentArgs by navArgs()

        val movieData = movieArgs.movieData

        movieViewModel.getMovieTrailer(movieData)

        viewLifecycleOwner.lifecycleScope.launch {

            movieViewModel.movieTrailerData.collect{movieTrailerResponse->
                when(movieTrailerResponse){
                    is Resource.Success->{


                        Log.e("youtubekey",movieTrailerResponse.data!!.results.first().key)


                        binding.webViewYt.apply {

                            clearHistory()
                            clearCache(true)
                            settings.domStorageEnabled = true
                            settings.javaScriptEnabled = true

                            webViewClient = WebViewClient()
                            settings.mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
                            isNestedScrollingEnabled = true
                            loadUrl(YOUTUBE_TRAILER_BASE_URL + movieTrailerResponse.data.results[0].key)

                        }
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
        movieCreditAdapter = MovieCreditAdapter(object : MovieCreditAdapter.OnItemClickListener {
            override fun onPersonClick(currentPerson: Cast) {

                val bundle = Bundle().apply {
                    putInt("personData",currentPerson.id)
                }

                val personDetailFragment = PopularPeopleDetailFragment()
                personDetailFragment.arguments = bundle


                personDetailFragment.show(requireActivity().supportFragmentManager,PopularPeopleDetailFragment().tag)


            }
        })

        similarMoviesAdapter = SimilarMoviesAdapter(object : SimilarMoviesAdapter.OnItemClickListener {
            override fun onSimilarMovieClick(currentSimilarMovie: Result) {

                val bundle = Bundle().apply {
                    putInt("movieData",currentSimilarMovie.id)
                }

                val similarMovieDetailFragment = SimilarMovieDetailFragment()
                similarMovieDetailFragment.arguments = bundle

                similarMovieDetailFragment.show(requireActivity().supportFragmentManager,similarMovieDetailFragment.tag)



            }
        })

        binding.apply {

            rvCast.adapter = movieCreditAdapter
            rvCast.layoutManager = StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL)
            rvCast.setHasFixedSize(true)


            rvSimilarMovies.adapter = similarMoviesAdapter
            rvSimilarMovies.layoutManager = StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL)
            rvSimilarMovies.setHasFixedSize(true)



        }

    }

}