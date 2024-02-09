package com.ozanarik.mvvmmovieapp.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ozanarik.mvvmmovieapp.R
import com.ozanarik.mvvmmovieapp.business.models.movie_model.Cast
import com.ozanarik.mvvmmovieapp.business.models.movie_model.Result
import com.ozanarik.mvvmmovieapp.databinding.FragmentCastRelatedMoviesBinding
import com.ozanarik.mvvmmovieapp.ui.adapters.movieadapter.SimilarMoviesAdapter
import com.ozanarik.mvvmmovieapp.ui.adapters.moviecreditadapter.MovieCreditAdapter
import com.ozanarik.mvvmmovieapp.ui.viewmodels.MovieViewModel
import com.ozanarik.mvvmmovieapp.utils.CONSTANTS
import com.ozanarik.mvvmmovieapp.utils.CONSTANTS.Companion.IMAGE_BASE_URL
import com.ozanarik.mvvmmovieapp.utils.CONSTANTS.Companion.YOUTUBE_TRAILER_BASE_URL
import com.ozanarik.mvvmmovieapp.utils.Resource
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CastRelatedMovies : Fragment(){

    private lateinit var binding:FragmentCastRelatedMoviesBinding
    private lateinit var movieViewModel: MovieViewModel

    private lateinit var movieCreditAdapter: MovieCreditAdapter
    private lateinit var similarMoviesAdapter: SimilarMoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = FragmentCastRelatedMoviesBinding.inflate(inflater,container,false)
        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]


        handleCastRelatedMovies()

        handleRv()
        getMovieCredit()
        getMovieYoutubeTrailer()
        handleReviewFragmentNavigation()



        return binding.root
    }


    private fun handleCastRelatedMovies(){

        val movieArgs: CastRelatedMoviesArgs by navArgs()
        val movieData = movieArgs.movieData

        viewLifecycleOwner.lifecycleScope.launch {
            movieViewModel.getDetailedMovieData(movieData)

            movieViewModel.detailedMovieData.collect{detailedMovieData->

                val detailedMovie = detailedMovieData.data
                binding.apply {

                    Picasso.get().load(IMAGE_BASE_URL + detailedMovie?.posterPath).error(R.drawable.baseline_error_24)
                        .placeholder(R.drawable.placeholder)
                        .into(imageViewCastRelatedMoviePersonProfilePath)

                    if (detailedMovie!=null){
                        tvCastRelatedMovieName.text = detailedMovie.originalTitle
                        val productionCompanies = detailedMovie.productionCompanies.let { company-> company.joinToString (", "){it.name} }
                        val productionCountries = detailedMovie.productionCountries.let { country-> country.joinToString (", "){it.name} }

                        tvProductionCompaniesCastRelated.text = productionCompanies
                        tvProductionCountriesCastRelated.text = productionCountries
                        detailedMovie.tagline.let { tvCastRelatedTagline.text = it }

                        val genreList = detailedMovie.genres.let {genre->genre.take(4).joinToString (", "){it.name}  }

                        tvGenreCastRelated.text = genreList

                        tvRunTimeCastRelated.text = "${detailedMovie?.runtime} min"

                        tvOriginalLanguageCastRelated.text = detailedMovie.originalLanguage

                        tvCastRelatedMovieName.text = detailedMovie.originalTitle
                        tvOverViewCastRelated.text = detailedMovie.overview

                        cvHomePageCastRelated.setOnClickListener {

                            val uri = detailedMovie.homepage?.let { Uri.parse(it) }
                            val intent = Intent(Intent.ACTION_VIEW,uri)

                            val intentChooser = Intent.createChooser(intent,"Choose An Action")

                            startActivity(intentChooser)
                        }
                    }
                }
            }
        }
    }

    private fun handleReviewFragmentNavigation(){

        val movieArgs:MovieDetailFragmentArgs by navArgs()

        val movieData = movieArgs.movieData
        binding.tvReviewsCastRelated.setOnClickListener {

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


                        binding.tvYtCastRelated.setOnClickListener {

                            val results = movieTrailerResponse.data?.results
                            if (!results.isNullOrEmpty()) {
                                val movieUri = Uri.parse(results[0].key)
                                val intent = Intent(Intent.ACTION_VIEW, movieUri)
                                val intentChooser = Intent.createChooser(intent, "Choose An Action")
                                startActivity(intentChooser)

                            }
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

                        binding.tvCastRelated.text = "Cast : $cast"

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

                val personToPass = CastRelatedMoviesDirections.actionCastRelatedMoviesToPopularPeopleDetailFragment(currentPerson.id)
                findNavController().navigate(personToPass)

            }
        })
        similarMoviesAdapter= SimilarMoviesAdapter(object : SimilarMoviesAdapter.OnItemClickListener {
            override fun onSimilarMovieClick(currentSimilarMovie: Result?) {
                val movieToPass = CastRelatedMoviesDirections.actionCastRelatedMoviesToSimilarMovieDetailFragment()
                findNavController().navigate(movieToPass)

            }
        })

        binding.apply {
            rvCastRelated.adapter = movieCreditAdapter
            rvCastRelated.setHasFixedSize(true)
            rvCastRelated.layoutManager = StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL)
        }

    }



}