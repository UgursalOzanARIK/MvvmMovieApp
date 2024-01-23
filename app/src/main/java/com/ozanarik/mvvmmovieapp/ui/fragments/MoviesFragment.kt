package com.ozanarik.mvvmmovieapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ozanarik.mvvmmovieapp.R
import com.ozanarik.mvvmmovieapp.business.model.Result
import com.ozanarik.mvvmmovieapp.databinding.FragmentMoviesBinding
import com.ozanarik.mvvmmovieapp.ui.adapters.MovieAdapter
import com.ozanarik.mvvmmovieapp.ui.viewmodels.MovieViewModel
import com.ozanarik.mvvmmovieapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MoviesFragment : Fragment(),MovieAdapter.OnItemClickListener {

    private lateinit var binding: FragmentMoviesBinding
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var movieAdapter: MovieAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]

        binding = FragmentMoviesBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment

        handleMovieRv()

        handleNowPlayingMovies()


        return binding.root
    }




    private fun handleNowPlayingMovies(){

        movieViewModel.getNowPlayingMovies()
        viewLifecycleOwner.lifecycleScope.launch {
            movieViewModel.movieResponse.collect{movieResponse->
                when(movieResponse){
                    is Resource.Success->{
                        movieAdapter.asyncDifferList.submitList(movieResponse.data!!.results)
                    }
                    is Resource.Loading->{
                        Log.e("as","loading")
                    }
                    is Resource.Error->{
                        Log.e("as","error")
                    }
                }
            }
        }
    }


    private fun handleMovieRv(){
        movieAdapter = MovieAdapter(object : MovieAdapter.OnItemClickListener {
            override fun onItemClick(currentMovieOrShow: Result) {
                Log.e("asd",currentMovieOrShow.id.toString())
            }
        })
        binding.nowPlayingRv.apply {
            layoutManager = StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL)
            adapter = movieAdapter
            setHasFixedSize(true)
        }
    }

    override fun onItemClick(currentMovieOrShow: Result) {

    }


}