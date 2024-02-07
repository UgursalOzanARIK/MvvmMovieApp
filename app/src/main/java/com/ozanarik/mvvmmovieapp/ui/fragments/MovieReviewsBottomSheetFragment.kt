package com.ozanarik.mvvmmovieapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ozanarik.mvvmmovieapp.R
import com.ozanarik.mvvmmovieapp.databinding.FragmentMovieReviewsBottomSheetBinding
import com.ozanarik.mvvmmovieapp.ui.adapters.movieadapter.MovieReviewAdapter
import com.ozanarik.mvvmmovieapp.ui.viewmodels.MovieViewModel
import com.ozanarik.mvvmmovieapp.utils.Extensions.Companion.showSnackbar
import com.ozanarik.mvvmmovieapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieReviewsBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding:FragmentMovieReviewsBottomSheetBinding
    private lateinit var movieViewModel: MovieViewModel

    private lateinit var movieReviewAdapter: MovieReviewAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMovieReviewsBottomSheetBinding.inflate(inflater,container,false)


        Log.e("asd","oncreate")

        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]



        handleRv()
        getMovieReviews()

        return binding.root
    }


    private fun getMovieReviews(){

        val args: MovieReviewsBottomSheetFragmentArgs by navArgs()

        val movieData = args.movieData



        movieViewModel.getMovieReviews(movieData)

        viewLifecycleOwner.lifecycleScope.launch {
            movieViewModel.movieReviewsData.collect{reviewResponse->

                when(reviewResponse){
                    is Resource.Success->{
                        movieReviewAdapter.asyncDifferList.submitList(reviewResponse.data!!.results)

                        Log.e("asd","success")

                    }
                    is Resource.Loading->{
                        showSnackbar("Loading Data")
                    }
                    is Resource.Error->{
                        showSnackbar(reviewResponse.message!!)
                    }
                }
            }
        }
    }

    private fun handleRv(){

        movieReviewAdapter = MovieReviewAdapter()

        binding.apply {
            rvMovieReviews.adapter = movieReviewAdapter
            rvMovieReviews.setHasFixedSize(true)
            rvMovieReviews.layoutManager = StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL)
        }

    }


}