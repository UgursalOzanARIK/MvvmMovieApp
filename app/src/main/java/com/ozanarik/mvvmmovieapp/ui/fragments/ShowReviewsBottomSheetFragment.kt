package com.ozanarik.mvvmmovieapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ozanarik.mvvmmovieapp.R
import com.ozanarik.mvvmmovieapp.databinding.FragmentMovieReviewsBottomSheetBinding
import com.ozanarik.mvvmmovieapp.databinding.FragmentShowReviewsBottomSheetBinding
import com.ozanarik.mvvmmovieapp.ui.adapters.movieadapter.MovieReviewAdapter
import com.ozanarik.mvvmmovieapp.ui.viewmodels.ShowsViewModel
import com.ozanarik.mvvmmovieapp.utils.Extensions.Companion.showSnackbar
import com.ozanarik.mvvmmovieapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShowReviewsBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentShowReviewsBottomSheetBinding
    private lateinit var showsViewModel: ShowsViewModel

    private lateinit var movieReviewAdapter: MovieReviewAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentShowReviewsBottomSheetBinding.inflate(inflater,container,false)
        showsViewModel = ViewModelProvider(this)[ShowsViewModel::class.java]


        handleRv()

        getShowReview()




        // Inflate the layout for this fragment
        return binding.root
    }



    private fun getShowReview(){

        val showArgs:ShowReviewsBottomSheetFragmentArgs by navArgs()
        val showData = showArgs.showData

        viewLifecycleOwner.lifecycleScope.launch {
            showsViewModel.getDetailedShowData(showData)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            showsViewModel.detailedShowData.collect{detailedShowData->
                when(detailedShowData){
                    is Resource.Success->{

                    }
                    is Resource.Error->{
                        showSnackbar(detailedShowData.message!!)
                    }
                    is Resource.Loading->{
                        showSnackbar("Fetching Data")
                    }
                }
            }
        }

    }
    private fun  handleRv(){
        movieReviewAdapter = MovieReviewAdapter()

        binding.apply {
            rvShowReviews.adapter = movieReviewAdapter
            rvShowReviews.layoutManager = StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL)
            rvShowReviews.setHasFixedSize(true)
        }


    }

}