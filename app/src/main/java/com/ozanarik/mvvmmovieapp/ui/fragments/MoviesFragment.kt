package com.ozanarik.mvvmmovieapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ozanarik.mvvmmovieapp.business.model.Result
import com.ozanarik.mvvmmovieapp.databinding.FragmentMoviesBinding
import com.ozanarik.mvvmmovieapp.ui.adapters.NowPlayingMovieAdapter
import com.ozanarik.mvvmmovieapp.ui.adapters.PopularMoviesAdapter
import com.ozanarik.mvvmmovieapp.ui.viewmodels.MovieViewModel
import com.ozanarik.mvvmmovieapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : Fragment() {
    private lateinit var binding: FragmentMoviesBinding
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var nowPlayingMoviesAdapter: NowPlayingMovieAdapter
    private lateinit var popularMoviesAdapter: PopularMoviesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = FragmentMoviesBinding.inflate(inflater,container,false)
        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]


        handleMovieRv()
        handleNowPlayingMovies()
        handlePopularMovies()



        // Inflate the layout for this fragment
        return binding.root
    }

    private fun handleNowPlayingMovies(){

        movieViewModel.getNowPlayingMovies()
        viewLifecycleOwner.lifecycleScope.launch {
            movieViewModel.nowPlayingMovies.collect{ nowPlayingMoviesResponse->
                when(nowPlayingMoviesResponse){
                    is Resource.Success->{
                        nowPlayingMoviesAdapter.asyncDifferList.submitList(nowPlayingMoviesResponse.data!!.results)
                    }
                    is Resource.Error->{
                        Log.e("asd","loading")
                    }
                    is Resource.Loading->{
                        Log.e("asd","loading")
                    }
                }
            }
        }
    }
    private fun handlePopularMovies(){
        movieViewModel.getPopularMovies()
        viewLifecycleOwner.lifecycleScope.launch {
            movieViewModel.popularMovies.collect{popularMovieResponse->

                when(popularMovieResponse){
                    is Resource.Success->popularMoviesAdapter.asyncDifferList.submitList(popularMovieResponse.data!!.results)
                    is Resource.Error->Log.e("asd",popularMovieResponse.message!!)
                    is Resource.Loading->Log.e("asd","loading")
                }
            }
        }
    }

    private fun handleMovieRv(){

        binding.apply {

            popularMoviesAdapter = PopularMoviesAdapter(object : PopularMoviesAdapter.OnItemClickListener {
                override fun onItemClick(currentMovieOrShow: Result) {
                    Log.e("asd",currentMovieOrShow.id.toString())
                }
            })
            nowPlayingMoviesAdapter = NowPlayingMovieAdapter(object : NowPlayingMovieAdapter.OnItemClickListener {
                override fun onItemClick(currentMovieOrShow: Result) {
                    Log.e("asd",currentMovieOrShow.id.toString())
                }
            })



            rvNowPlayingMovies.adapter = nowPlayingMoviesAdapter
            rvNowPlayingMovies.layoutManager = StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL)

            rvPopularMovies.adapter = popularMoviesAdapter
            rvPopularMovies.layoutManager = StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL)

        }

    }

}