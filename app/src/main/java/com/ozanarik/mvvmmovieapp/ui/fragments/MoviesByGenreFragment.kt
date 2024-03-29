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
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ozanarik.mvvmmovieapp.business.models.movie_model.movie_response.Result
import com.ozanarik.mvvmmovieapp.databinding.FragmentMoviesByGenreBinding
import com.ozanarik.mvvmmovieapp.ui.adapters.movieadapter.UpcomingMoviesAdapter
import com.ozanarik.mvvmmovieapp.ui.viewmodels.MovieViewModel
import com.ozanarik.mvvmmovieapp.utils.Extensions.Companion.showSnackbar
import com.ozanarik.mvvmmovieapp.utils.MovieGenreType
import com.ozanarik.mvvmmovieapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesByGenreFragment : Fragment() {
    private lateinit var binding:FragmentMoviesByGenreBinding

    private lateinit var upcomingMoviesAdapter: UpcomingMoviesAdapter
    private lateinit var movieViewModel: MovieViewModel

    private lateinit var selectedGenre:MovieGenreType


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment


        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        binding = FragmentMoviesByGenreBinding.inflate(inflater,container,false)


        handleAdapter()
        getAllMovies()


        return binding.root
    }
    private fun getAllMovies(){

        movieViewModel.getAllMovies()
        viewLifecycleOwner.lifecycleScope.launch {
            movieViewModel.allMovies.collect{allMoviesResponse->
                when(allMoviesResponse){
                    is Resource.Success->{

                        val allMoviesList = allMoviesResponse.data!!.results

                        binding.imageViewGenreFilter.setOnClickListener {
                            handleFilterDialog(upcomingMoviesAdapter,allMoviesList){
                                upcomingMoviesAdapter.asyncDifferList.submitList(it)
                            }
                        }

                        upcomingMoviesAdapter.asyncDifferList.submitList(allMoviesList)
                    }
                    is Resource.Error->{
                        showSnackbar(allMoviesResponse.message!!)
                    }
                    is Resource.Loading->{
                        showSnackbar("Fetching Data")
                    }
                }
            }
        }
    }

    private fun <T: RecyclerView.Adapter<*>> handleFilterDialog(
        adapter:T,
        movieList:List<Result>,
        updateAdapter :(List<Result>)->Unit
    ){

        val optionList = MovieGenreType.entries.map { it.name }

        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Choose A Genre")
            .setItems(optionList.toTypedArray()){dialog,option->

               selectedGenre = MovieGenreType.valueOf(optionList[option])

                val sortedList = movieList.filter { it.genreIds.contains(selectedGenre.genreId) }
                binding.tvGenreDetail.text = selectedGenre.name

                updateAdapter(sortedList)


            }.create().show()

    }


    private fun handleAdapter(){
        upcomingMoviesAdapter = UpcomingMoviesAdapter(object : UpcomingMoviesAdapter.OnItemClickListener {
            override fun onItemClick(currentMovieOrShow: Result) {

                handleNavigation(currentMovieOrShow.id)
            }
        })

        binding.rvMoviesByGenre.adapter = upcomingMoviesAdapter
        binding.rvMoviesByGenre.setHasFixedSize(true)
        binding.rvMoviesByGenre.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.HORIZONTAL)

    }

    private fun handleNavigation(movieId:Int){
        val movieToPass = MoviesByGenreFragmentDirections.actionMoviesByGenreFragmentToMovieDetailFragment(movieId)
        findNavController().navigate(movieToPass)
    }
}