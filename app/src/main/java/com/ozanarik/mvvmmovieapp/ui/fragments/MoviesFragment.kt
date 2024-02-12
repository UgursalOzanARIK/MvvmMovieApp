package com.ozanarik.mvvmmovieapp.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ozanarik.mvvmmovieapp.R
import com.ozanarik.mvvmmovieapp.business.models.movie_model.movie_response.Result
import com.ozanarik.mvvmmovieapp.databinding.FragmentMoviesBinding
import com.ozanarik.mvvmmovieapp.ui.adapters.movieadapter.NowPlayingMovieAdapter
import com.ozanarik.mvvmmovieapp.ui.adapters.movieadapter.PopularMoviesAdapter
import com.ozanarik.mvvmmovieapp.ui.adapters.movieadapter.TopRatedMoviesAdapter
import com.ozanarik.mvvmmovieapp.ui.adapters.movieadapter.UpcomingMoviesAdapter
import com.ozanarik.mvvmmovieapp.ui.viewmodels.MovieViewModel
import com.ozanarik.mvvmmovieapp.utils.Extensions.Companion.showSnackbar
import com.ozanarik.mvvmmovieapp.utils.MovieType
import com.ozanarik.mvvmmovieapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : Fragment(), SearchView.OnQueryTextListener {
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
        handleTopRatedMovies()
        handleUpcomingMovies()
        handlePopularMovies()
        handleNowPlayingMovies()

        (activity as AppCompatActivity).setSupportActionBar(binding.moviesToolbar)


        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.movie_search_menu,menu)
                val searchItem = menu.findItem(R.id.action_SearchMovie)
                val searchView = searchItem.actionView as SearchView
                searchView.setOnQueryTextListener(this@MoviesFragment)


            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }
        },viewLifecycleOwner,Lifecycle.State.RESUMED)

        // Inflate the layout for this fragment
        return binding.root
    }


    private fun handleMovieSearch(query:String?){

        query?.let { movieViewModel.searchMovie(it) }

        viewLifecycleOwner.lifecycleScope.launch {

            movieViewModel.searchedMovieData.collect{searchedMovieResponse->
                when(searchedMovieResponse){
                    is Resource.Success->{nowPlayingMoviesAdapter.asyncDifferList.submitList(searchedMovieResponse.data!!.results)}
                    is Resource.Error->{showSnackbar(searchedMovieResponse.message!!)}
                    is Resource.Loading->{showSnackbar("Fetching Data")}
                }
            }
        }

    }



    private fun handleNowPlayingMovies(){

        movieViewModel.getNowPlayingMovies()
        viewLifecycleOwner.lifecycleScope.launch {
            movieViewModel.nowPlayingMovies.collect{ nowPlayingMoviesResponse->
                when(nowPlayingMoviesResponse){
                    is Resource.Success->{


                        val nowPlayingMovieList = nowPlayingMoviesResponse.data!!.results

                        handleFilterDialog(nowPlayingMoviesAdapter,nowPlayingMovieList,"Choose An Option",binding.imageViewNowPlayingFilter)
                        {
                            nowPlayingMoviesAdapter.asyncDifferList.submitList(it)
                        }


                        nowPlayingMoviesAdapter.asyncDifferList.submitList(nowPlayingMovieList)

                        nowPlayingMovieList.forEach { movie->

                            when(movie.genreIds){


                            }

                        }


                    }
                    is Resource.Error->{
                        showSnackbar(nowPlayingMoviesResponse.message!!)
                    }
                    is Resource.Loading->{Log.e("loading","movies")
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

                        val popularMoviesList = popularMovieResponse.data!!.results

                        handleFilterDialog(popularMoviesAdapter,popularMoviesList,"Choose An Option",binding.imageViewPopularMoviesFilter)
                        {
                            popularMoviesAdapter.asyncDifferList.submitList(it)
                        }

                        popularMoviesAdapter.asyncDifferList.submitList(popularMovieResponse.data.results)
                    }
                    is Resource.Error-> {
                        showSnackbar(popularMovieResponse.message!!)
                         }
                    is Resource.Loading->{Log.e("loading","movies")
                    }
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

                        val topRatedMoviesList = topRatedMoviesResponse.data!!.results

                        handleFilterDialog(topRatedMoviesAdapter,topRatedMoviesList,"Choose An Option",binding.imageViewTopRatedMoviesFilter)
                        {
                            topRatedMoviesAdapter.asyncDifferList.submitList(it)
                        }

                        topRatedMoviesAdapter.asyncDifferList.submitList(topRatedMoviesResponse.data.results)
                        topRatedMoviesAdapter.notifyDataSetChanged()
                    }
                    is Resource.Error->{
                        showSnackbar(topRatedMoviesResponse.message!!)
                    }
                    is Resource.Loading->{Log.e("loading","movies")
                    }
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

                        handleFilterDialog(upcomingMoviesAdapter,upcomingMoviesList,"Choose An Option",binding.imageViewUpcomingFilter)
                        {
                                upcomingMoviesAdapter.asyncDifferList.submitList(it)
                        }

                        upcomingMoviesAdapter.asyncDifferList.submitList(upcomingMoviesList)

                    }
                    is Resource.Error->{
                        showSnackbar(upcomingMoviesResponse.message!!)
                                        }
                    is Resource.Loading->{Log.e("loading","movies")
                    }
                }
            }
        }
    }

    private fun handleMovieRv(){

        binding.apply {

            popularMoviesAdapter = PopularMoviesAdapter(object : PopularMoviesAdapter.OnItemClickListener {
                override fun onItemClick(currentMovieOrShow: Result) {
                    handleNavigation(currentMovieOrShow.id)
                }
            })
            nowPlayingMoviesAdapter = NowPlayingMovieAdapter(object : NowPlayingMovieAdapter.OnItemClickListener {
                override fun onItemClick(currentMovieOrShow: Result) {
                    handleNavigation(currentMovieOrShow.id)
                }
            })
            topRatedMoviesAdapter = TopRatedMoviesAdapter(object : TopRatedMoviesAdapter.OnItemClickListener {
                override fun onItemClick(currentMovieOrShow: Result) {
                    handleNavigation(currentMovieOrShow.id)
                }
            })

            upcomingMoviesAdapter = UpcomingMoviesAdapter(object : UpcomingMoviesAdapter.OnItemClickListener {
                override fun onItemClick(currentMovieOrShow: Result) {
                    handleNavigation(currentMovieOrShow.id)
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

    private fun <T : RecyclerView.Adapter<*>>  handleFilterDialog(
        adapter:T,
        movieList:List<Result>,
        title:String,
        filterImage:ImageView,
        updateAdapter:(List<Result>)->Unit

    ){
        val optionList = arrayOf(MovieType.IMDB_RATE.movieType ,MovieType.RELEASE_DATE.movieType,MovieType.DEFAULT.movieType)
        filterImage.setOnClickListener {
            val alertDialog = AlertDialog.Builder(requireContext())
            alertDialog.setTitle(title)
            alertDialog.setItems(optionList){_,option->

                val sortedList = when(option){
                    0->movieList.sortedBy { movie->movie.voteAverage }
                    1->movieList.sortedBy { movie->movie.releaseDate }
                    else->movieList
                }
                updateAdapter(sortedList)
            }.create().show()
        }

    }
    private fun handleNavigation(movieData:Int){
        val movieToPass = MoviesFragmentDirections.actionMoviesFragmentToDetailFragment(movieData)
        findNavController().navigate(movieToPass)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let { handleMovieSearch(it) }
        return true

    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return false
    }
}