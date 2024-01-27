package com.ozanarik.mvvmmovieapp.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.SortedList
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ozanarik.mvvmmovieapp.R
import com.ozanarik.mvvmmovieapp.business.model.Result
import com.ozanarik.mvvmmovieapp.databinding.FragmentMoviesBinding
import com.ozanarik.mvvmmovieapp.ui.adapters.NowPlayingMovieAdapter
import com.ozanarik.mvvmmovieapp.ui.adapters.PopularMoviesAdapter
import com.ozanarik.mvvmmovieapp.ui.adapters.TopRatedMoviesAdapter
import com.ozanarik.mvvmmovieapp.ui.adapters.UpcomingMoviesAdapter
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
    private lateinit var topRatedMoviesAdapter: TopRatedMoviesAdapter
    private lateinit var upcomingMoviesAdapter: UpcomingMoviesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = FragmentMoviesBinding.inflate(inflater,container,false)
        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]



        handleMovieRv()
        handleNowPlayingMovies()
        handlePopularMovies()
        handleTopRatedMovies()
        handleUpcomingMovies()



        // Inflate the layout for this fragment
        return binding.root
    }



    private fun handleNowPlayingMovies(){

        movieViewModel.getNowPlayingMovies()
        viewLifecycleOwner.lifecycleScope.launch {
            movieViewModel.nowPlayingMovies.collect{ nowPlayingMoviesResponse->
                when(nowPlayingMoviesResponse){
                    is Resource.Success->{

                        val nowPlayingMovieList = nowPlayingMoviesResponse.data!!.results

                        binding.imageViewNowPlayingFilter.setOnClickListener {

                            val alertDialog = AlertDialog.Builder(requireContext())
                            val optionList = arrayOf<String>("Order By Imdb Rate","Order By Release Date","Order By Adult Content","Default")
                            alertDialog.setTitle("Choose An Option")
                                .setItems(optionList){_,option->
                                    val sortedList = when(option){
                                        0->{nowPlayingMovieList.sortedBy { it.voteAverage }}
                                        1->{nowPlayingMovieList.sortedBy { it.releaseDate }}
                                        2->{nowPlayingMovieList.sortedBy { it.adult }}
                                        else->nowPlayingMovieList
                                    }
                                    nowPlayingMoviesAdapter.asyncDifferList.submitList(sortedList)
                                    nowPlayingMoviesAdapter.notifyDataSetChanged()
                                }.create().show()
                        }

                        nowPlayingMoviesAdapter.asyncDifferList.submitList(nowPlayingMovieList)

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
    private fun handleTopRatedMovies(){
        movieViewModel.getTopRatedMovies()
        viewLifecycleOwner.lifecycleScope.launch {
            movieViewModel.topRatedMovies.collect{topRatedMoviesResponse->
                when(topRatedMoviesResponse){
                    is Resource.Success->topRatedMoviesAdapter.asyncDifferList.submitList(topRatedMoviesResponse.data!!.results)
                    is Resource.Error->Log.e("asd","error")
                    is Resource.Loading->Log.e("asd","error")
                }

            }
        }
    }

    private fun handleUpcomingMovies(){
        movieViewModel.getUpcomingMovies()
        viewLifecycleOwner.lifecycleScope.launch {
            movieViewModel.upcomingMovies.collect{upcomingMoviesResponse->

                when(upcomingMoviesResponse){
                    is Resource.Success->{
                        val upcomingMoviesList = upcomingMoviesResponse.data!!.results

                        binding.imageViewUpcomingFilter.setOnClickListener {
                            val optionList = arrayOf<String>("Order By Imdb Rate","Order By Release Date","Order By Adult Content","Default")
                            val alertDialog = AlertDialog.Builder(requireContext())
                            alertDialog.setTitle("Choose An Option")
                                .setItems(optionList){_,option->

                                    val sortedList = when(option){
                                        0->{upcomingMoviesList.sortedBy { movie->movie.voteAverage }}
                                        1->{upcomingMoviesList.sortedBy { movie->movie.releaseDate }}
                                        2->{upcomingMoviesList.sortedBy { movie->movie.adult }}
                                        else->upcomingMoviesList
                                    }

                                    upcomingMoviesAdapter.asyncDifferList.submitList(sortedList)
                                    upcomingMoviesAdapter.notifyDataSetChanged()
                                }.create().show()
                        }
                        upcomingMoviesAdapter.asyncDifferList.submitList(upcomingMoviesList)

                    }
                    is Resource.Error->{Log.e("asd",upcomingMoviesResponse.message!!)}
                    is Resource.Loading->{Log.e("asd","loading")}
                }
            }
        }
    }

    private fun handleMovieRv(){

        binding.apply {

            popularMoviesAdapter = PopularMoviesAdapter(object : PopularMoviesAdapter.OnItemClickListener {
                override fun onItemClick(currentMovieOrShow: Result) {
                    Log.e("asd",currentMovieOrShow.voteAverage.toString())
                    Log.e("asd",currentMovieOrShow.releaseDate.toString())
                    Log.e("asd",currentMovieOrShow.adult.toString())

                }
            })
            nowPlayingMoviesAdapter = NowPlayingMovieAdapter(object : NowPlayingMovieAdapter.OnItemClickListener {
                override fun onItemClick(currentMovieOrShow: Result) {
                    Log.e("asd",currentMovieOrShow.voteAverage.toString())
                    Log.e("asd",currentMovieOrShow.releaseDate.toString())
                    Log.e("asd",currentMovieOrShow.adult.toString())
                }
            })
            topRatedMoviesAdapter = TopRatedMoviesAdapter(object : TopRatedMoviesAdapter.OnItemClickListener {
                override fun onItemClickListener(currentMovieOrShow: Result) {
                    Log.e("asd",currentMovieOrShow.id.toString())
                }
            })

            upcomingMoviesAdapter = UpcomingMoviesAdapter(object : UpcomingMoviesAdapter.OnItemClickListener {
                override fun onItemClick(movie: Result) {
                    Log.e("asd","asdas")
                }
            })



            rvNowPlayingMovies.adapter = nowPlayingMoviesAdapter
            rvNowPlayingMovies.layoutManager = StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL)

            rvPopularMovies.adapter = popularMoviesAdapter
            rvPopularMovies.layoutManager = StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL)

            rvTopRatedMovies.adapter = topRatedMoviesAdapter
            rvTopRatedMovies.layoutManager = StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL)

            rvUpcomingMovies.adapter = upcomingMoviesAdapter
            rvUpcomingMovies.layoutManager = StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL)

        }

    }




}