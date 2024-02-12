package com.ozanarik.mvvmmovieapp.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ozanarik.mvvmmovieapp.R
import com.ozanarik.mvvmmovieapp.business.models.movie_model.movie_credits_response.Cast
import com.ozanarik.mvvmmovieapp.databinding.FragmentSimilarMovieDetailBinding
import com.ozanarik.mvvmmovieapp.ui.adapters.moviecreditadapter.MovieCreditAdapter
import com.ozanarik.mvvmmovieapp.ui.viewmodels.MovieViewModel
import com.ozanarik.mvvmmovieapp.utils.CONSTANTS
import com.ozanarik.mvvmmovieapp.utils.Extensions
import com.ozanarik.mvvmmovieapp.utils.Extensions.Companion.showSnackbar
import com.ozanarik.mvvmmovieapp.utils.Resource
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.DecimalFormat


@AndroidEntryPoint
class SimilarMovieDetailFragment : BottomSheetDialogFragment() {
   private lateinit var binding:FragmentSimilarMovieDetailBinding
   private lateinit var movieViewModel: MovieViewModel

   private lateinit var movieCreditAdapter: MovieCreditAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        binding = FragmentSimilarMovieDetailBinding.inflate(inflater,container,false)


        handleRv()

        getMovieCredit()
        handleMovieArgs()
        handleReviewFragmentNavigation()
        getMovieYoutubeTrailer()


        return binding.root
    }



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

                        binding.apply {
                            tvTitleDetailBottomSheet.text = detailedMovieResponse.data!!.originalTitle

                            val decimalFormat = DecimalFormat("#.##")


                            tvImdbAverageDetailBottomSheet.text = "IMDB / ${decimalFormat.format(detailedMovieResponse.data.voteAverage)}"
                            tvDescriptionBottomSheet.text = detailedMovieResponse.data.overview
                            Picasso.get().load(CONSTANTS.IMAGE_BASE_URL + detailedMovieResponse.data.posterPath).placeholder(R.drawable.placeholder).into(binding.imageViewPosterPathBottomSheet)

                            tvOriginalLanguageBottomSheet.text = movieViewModel.getMovieLanguage(detailedMovieResponse.data.originalLanguage)
                            tvRunTimeBottomSheet.text = "${detailedMovieResponse.data.runtime} min"
                            tvTaglineBottomSheet.text = " \"${detailedMovieResponse.data.tagline}\" "

                            val genreNames = detailedMovieResponse.data.genres.joinToString(", "){it.name}

                            tvGenreBottomSheet.text = genreNames

                            val productionCompanies = detailedMovieResponse.data.productionCompanies.joinToString(", "){it.name}
                            val productionCountries = detailedMovieResponse.data.productionCountries.joinToString(", "){it.name}

                            tvProductionCompaniesBottomSheet.text = "Production Companies : $productionCompanies"
                            tvProductionCountriesBottomSheet.text = "Production Countries : $productionCountries"

                            cvHomePageBottomSheet.setOnClickListener{
                                val movieHomePage = detailedMovieResponse.data.homepage.let { Uri.parse(it) }


                                val intent = Intent(Intent.ACTION_VIEW,movieHomePage)

                                val intentChooser = Intent.createChooser(intent,"Choose Action")

                                startActivity(intentChooser)
                            }
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

    private fun handleReviewFragmentNavigation(){

        val movieArgs:MovieDetailFragmentArgs by navArgs()

        val movieData = movieArgs.movieData
        binding.tvReviewsBottomSheet.setOnClickListener {



            val bundle = Bundle().apply {
                putInt("movieData",movieData)
            }

            val movieReviewFragment = MovieReviewsBottomSheetFragment()
            movieReviewFragment.arguments = bundle


            movieReviewFragment.show(childFragmentManager,movieReviewFragment.tag)

        }
    }


    private fun getMovieYoutubeTrailer(){

        val movieArgs:MovieDetailFragmentArgs by navArgs()

        val movieData = movieArgs.movieData

        movieViewModel.getMovieTrailer(movieData)

        viewLifecycleOwner.lifecycleScope.launch {

            movieViewModel.movieTrailerData.collect{movieTrailerResponse->
                when(movieTrailerResponse){
                    is Resource.Success->{

                        binding.apply {
                            ytTrailer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener(){
                                override fun onReady(youTubePlayer: YouTubePlayer) {

                                    val showTrailer = movieTrailerResponse.data?.results
                                    if (showTrailer.isNullOrEmpty()){
                                        showSnackbar("No trailer found for the show...")
                                    }else{
                                        val videoId = showTrailer?.first()?.key
                                        if (videoId!=null){
                                            youTubePlayer.loadVideo(videoId,0.5f)
                                        }
                                    }

                                }
                            })
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

                        binding.tvCastBottomSheet.text = "Cast : $cast"


                        movieCreditAdapter.asyncDifferList.submitList(creditData.data.cast)

                    }
                    is Resource.Error->{
                       showSnackbar(creditData.message!!)
                    }
                    is Resource.Loading->{
                        showSnackbar("Fetching Data")
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

                findNavController().navigate(R.id.action_similarMovieDetailFragment_to_popularPeopleDetailFragment,bundle)

            }
        })
        binding.apply {
            rvCastBottomSheet.adapter = movieCreditAdapter
            rvCastBottomSheet.layoutManager = StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL)
            rvCastBottomSheet.setHasFixedSize(true)
        }

    }

}