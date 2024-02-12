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
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ozanarik.mvvmmovieapp.R
import com.ozanarik.mvvmmovieapp.business.models.movie_model.movie_credits_response.Cast
import com.ozanarik.mvvmmovieapp.business.models.movie_model.movie_response.Result
import com.ozanarik.mvvmmovieapp.databinding.FragmentDetailBinding
import com.ozanarik.mvvmmovieapp.ui.adapters.movieadapter.SimilarMoviesAdapter
import com.ozanarik.mvvmmovieapp.ui.adapters.moviecreditadapter.MovieCreditAdapter
import com.ozanarik.mvvmmovieapp.ui.viewmodels.MovieViewModel
import com.ozanarik.mvvmmovieapp.utils.CONSTANTS.Companion.IMAGE_BASE_URL
import com.ozanarik.mvvmmovieapp.utils.Extensions.Companion.showSnackbar
import com.ozanarik.mvvmmovieapp.utils.Resource
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
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
                        viewLifecycleOwner.lifecycle.addObserver(binding.youtubePlayer)

                        binding.youtubePlayer.addYouTubePlayerListener(object:AbstractYouTubePlayerListener(){
                            override fun onReady(youTubePlayer: YouTubePlayer) {

                                val movieTrailer = movieTrailerResponse.data?.results

                                if (movieTrailer.isNullOrEmpty()){
                                    showSnackbar("No trailer found for the movie...")
                                }
                                else{
                                    val videoId = movieTrailer?.first()?.key
                                    if (videoId != null) {
                                        youTubePlayer.loadVideo(videoId,0.5f)
                                    }

                                }
                            }
                        })

                    }
                    is Resource.Error->{
                       showSnackbar("An error occured trying to load video")
                    }
                    is Resource.Loading->{
                        showSnackbar("Fetching Movie Trailer Data")
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

                        binding.tvOriginalLanguage.text = movieViewModel.getMovieLanguage(detailedMovieResponse.data.originalLanguage)

                        binding.tvRunTime.text = "${detailedMovieResponse.data.runtime} min"
                        binding.tvTagline.text = " \"${detailedMovieResponse.data.tagline}\" "

                        val genreNames = detailedMovieResponse.data.genres.joinToString(", "){it.name}

                        binding.tvGenre.text = genreNames

                        val productionCompanies = detailedMovieResponse.data.productionCompanies.joinToString(", "){it.name}
                        val productionCountries = detailedMovieResponse.data.productionCountries.joinToString(", "){it.name}

                        binding.tvProductionCompanies.text = "Production Companies : $productionCompanies"
                        binding.tvProductionCountries.text = "Production Countries : $productionCountries"



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

                Log.e("asd",currentPerson.id.toString())

                val bundle = Bundle().apply {
                    putInt("personData",currentPerson.id)
                }

                findNavController().navigate(R.id.action_movieDetailFragment_to_popularPeopleDetailFragment,bundle)


            }
        })

        similarMoviesAdapter = SimilarMoviesAdapter(object : SimilarMoviesAdapter.OnItemClickListener {
            override fun onSimilarMovieClick(currentSimilarMovie: Result?) {

                val bundle = Bundle().apply {
                    putInt("movieData",currentSimilarMovie!!.id)
                }

                findNavController().navigate(R.id.action_movieDetailFragment_to_similarMovieDetailFragment,bundle)
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