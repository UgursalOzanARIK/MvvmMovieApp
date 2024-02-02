package com.ozanarik.mvvmmovieapp.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ozanarik.mvvmmovieapp.business.movie_model.Result
import com.ozanarik.mvvmmovieapp.databinding.FragmentMoviesBinding
import com.ozanarik.mvvmmovieapp.ui.adapters.movieadapter.NowPlayingMovieAdapter
import com.ozanarik.mvvmmovieapp.ui.adapters.movieadapter.PopularMoviesAdapter
import com.ozanarik.mvvmmovieapp.ui.adapters.movieadapter.TopRatedMoviesAdapter
import com.ozanarik.mvvmmovieapp.ui.adapters.movieadapter.UpcomingMoviesAdapter
import com.ozanarik.mvvmmovieapp.ui.viewmodels.MovieViewModel
import com.ozanarik.mvvmmovieapp.utils.Extensions.Companion.setVisibility
import com.ozanarik.mvvmmovieapp.utils.Extensions.Companion.showSnackbar
import com.ozanarik.mvvmmovieapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : Fragment() {
    private lateinit var binding: FragmentMoviesBinding
    lateinit var movieViewModel: MovieViewModel
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

                        binding.movieLoadingAnim.setVisibility(false)
                        binding.movieLoadingAnim.playAnimation()

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
                        showSnackbar(nowPlayingMoviesResponse.message!!)
                        binding.movieLoadingAnim.setVisibility(false)
                    }
                    is Resource.Loading->{
                        binding.movieLoadingAnim.setVisibility(true)
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
                    is Resource.Success->{
                        binding.movieLoadingAnim.setVisibility(false)
                        binding.movieLoadingAnim.playAnimation()

                        val popularMoviesList = popularMovieResponse.data!!.results

                        binding.imageViewPopularMoviesFilter.setOnClickListener {

                            val optionList = arrayOf<String>("Order By Imdb Rate","Order By Release Date","Order By Adult Content","Default")

                            val alertDialog = AlertDialog.Builder(requireContext())
                            alertDialog.setTitle("Choose An Option")
                                .setItems(optionList){_,option->
                                    val sortedList = when(option){

                                        0->{popularMoviesList.sortedBy { movie->movie.voteAverage }}
                                        1->{popularMoviesList.sortedBy { movie->movie.voteAverage }}
                                        2->{popularMoviesList.sortedBy { movie->movie.voteAverage }}
                                        else->popularMoviesList
                                    }

                                    popularMoviesAdapter.asyncDifferList.submitList(sortedList)
                                }.create().show()


                        }

                        popularMoviesAdapter.asyncDifferList.submitList(popularMovieResponse.data.results)
                        popularMoviesAdapter.notifyDataSetChanged()



                    }


                    is Resource.Error-> {
                        showSnackbar(popularMovieResponse.message!!)
                        binding.movieLoadingAnim.setVisibility(false) }
                    is Resource.Loading->{binding.movieLoadingAnim.setVisibility(true)}
                }
            }
        }
    }
    private fun handleTopRatedMovies(){
        movieViewModel.getTopRatedMovies()
        viewLifecycleOwner.lifecycleScope.launch {
            movieViewModel.topRatedMovies.collect{topRatedMoviesResponse->
                when(topRatedMoviesResponse){
                    is Resource.Success->{
                        binding.movieLoadingAnim.setVisibility(false)
                        binding.movieLoadingAnim.playAnimation()

                        val topRatedMoviesList = topRatedMoviesResponse.data!!.results

                        binding.imageViewTopRatedMoviesFilter.setOnClickListener {

                            val optionList = arrayOf<String>("Order By Imdb Rate","Order By Release Date","Order By Adult Content","Default")
                            val alertDialog = AlertDialog.Builder(requireContext())
                            alertDialog.setTitle("Choose An Option")
                                .setItems(optionList){_,option->

                                    val sortedList = when(option){
                                        0->topRatedMoviesList.sortedBy { movie->movie.voteAverage }
                                        1->topRatedMoviesList.sortedBy { movie->movie.releaseDate }
                                        2->topRatedMoviesList.sortedBy { movie->movie.adult }
                                        else->topRatedMoviesList

                                    }

                                    topRatedMoviesAdapter.asyncDifferList.submitList(sortedList)

                                }.create().show()
                        }

                        topRatedMoviesAdapter.asyncDifferList.submitList(topRatedMoviesResponse.data.results)
                        topRatedMoviesAdapter.notifyDataSetChanged()
                    }
                    is Resource.Error->{binding.movieLoadingAnim.setVisibility(false)
                    showSnackbar(topRatedMoviesResponse.message!!)}
                    is Resource.Loading->{binding.movieLoadingAnim.setVisibility(true)}
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

                        binding.movieLoadingAnim.setVisibility(false)
                        binding.movieLoadingAnim.playAnimation()

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

                                }.create().show()
                        }

                        upcomingMoviesAdapter.asyncDifferList.submitList(upcomingMoviesList)

                    }
                    is Resource.Error->{showSnackbar(upcomingMoviesResponse.message!!)
                    binding.movieLoadingAnim.setVisibility(false)}
                    is Resource.Loading->{binding.movieLoadingAnim.setVisibility(true)}
                }
            }
        }
    }

    private fun handleMovieRv(){

        binding.apply {

            popularMoviesAdapter = PopularMoviesAdapter(object : PopularMoviesAdapter.OnItemClickListener {
                override fun onItemClick(currentMovieOrShow: Result) {
                    val movieData = MoviesFragmentDirections.actionMoviesFragmentToDetailFragment(currentMovieOrShow.id)
                    findNavController().navigate(movieData)

                }
            })
            nowPlayingMoviesAdapter = NowPlayingMovieAdapter(object : NowPlayingMovieAdapter.OnItemClickListener {
                override fun onItemClick(currentMovieOrShow: Result) {
                    val movieData = MoviesFragmentDirections.actionMoviesFragmentToDetailFragment(currentMovieOrShow.id)
                    findNavController().navigate(movieData)

                }
            })
            topRatedMoviesAdapter = TopRatedMoviesAdapter(object : TopRatedMoviesAdapter.OnItemClickListener {
                override fun onItemClick(currentMovieOrShow: Result) {
                    val movieData = MoviesFragmentDirections.actionMoviesFragmentToDetailFragment(currentMovieOrShow.id)
                    findNavController().navigate(movieData)

                }
            })

            upcomingMoviesAdapter = UpcomingMoviesAdapter(object : UpcomingMoviesAdapter.OnItemClickListener {
                override fun onItemClick(currentMovieOrShow:Result) {
                    val movieData = MoviesFragmentDirections.actionMoviesFragmentToDetailFragment(currentMovieOrShow.id)
                    findNavController().navigate(movieData)

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