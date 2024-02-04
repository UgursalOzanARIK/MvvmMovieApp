package com.ozanarik.mvvmmovieapp.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ozanarik.mvvmmovieapp.business.models.shows_model.Result
import com.ozanarik.mvvmmovieapp.databinding.FragmentShowsBinding
import com.ozanarik.mvvmmovieapp.ui.adapters.showsadapters.AiringTodayShowsAdapter
import com.ozanarik.mvvmmovieapp.ui.adapters.showsadapters.OnTheAirShowsAdapter
import com.ozanarik.mvvmmovieapp.ui.adapters.showsadapters.PopularShowsAdapter
import com.ozanarik.mvvmmovieapp.ui.adapters.showsadapters.TopRatedShowsAdapter
import com.ozanarik.mvvmmovieapp.ui.viewmodels.ShowsViewModel
import com.ozanarik.mvvmmovieapp.utils.Extensions.Companion.showSnackbar
import com.ozanarik.mvvmmovieapp.utils.MovieType
import com.ozanarik.mvvmmovieapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShowsFragment : Fragment() {
    private lateinit var binding:FragmentShowsBinding
    private lateinit var showsViewModel:ShowsViewModel
    private lateinit var topRatedShowsAdapter: TopRatedShowsAdapter
    private lateinit var popularShowsAdapter: PopularShowsAdapter
    private lateinit var onTheAirShowsAdapter: OnTheAirShowsAdapter
    private lateinit var airingTodayShowsAdapter: AiringTodayShowsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment


        showsViewModel = ViewModelProvider(this)[ShowsViewModel::class.java]


        binding = FragmentShowsBinding.inflate(inflater,container,false)

        handleRv()

        getPopularShows()
        getTopRatedShows()
        getAiringTodayShows()
        getOnTheAirShows()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getTopRatedShows()
    }


    private fun getOnTheAirShows(){
        showsViewModel.getOnTheAirShows()
        viewLifecycleOwner.lifecycleScope.launch {
            showsViewModel.onTheAirShows.collect{onTheAirShowsResponse->
                when(onTheAirShowsResponse){
                    is Resource.Success->{

                        val onTheAirShowsList = onTheAirShowsResponse.data!!.results

                        handleFilterDialog(onTheAirShowsAdapter,onTheAirShowsList,binding.imageViewOnTheAirFilter,"Choose An Option"){
                            onTheAirShowsAdapter.asyncDifferList.submitList(it)
                        }

                        onTheAirShowsAdapter.asyncDifferList.submitList(onTheAirShowsList)

                    }
                    is Resource.Error->{
                        showSnackbar(onTheAirShowsResponse.message!!)

                    }
                    is Resource.Loading->{
                        Log.e("data","loading")
                    }
                }
            }
        }
    }

    private fun getAiringTodayShows(){
        showsViewModel.getAiringTodayShows()
        viewLifecycleOwner.lifecycleScope.launch {
            showsViewModel.airingShows.collect{airingTodayShowsResponse->
                when(airingTodayShowsResponse){
                    is Resource.Success->{

                        val airingTodayShowsList = airingTodayShowsResponse.data!!.results

                        handleFilterDialog(airingTodayShowsAdapter,airingTodayShowsList,binding.imageViewAiringTodayFilter,"Choose An Option"){
                            airingTodayShowsAdapter.asyncDifferList.submitList(it)
                        }

                        airingTodayShowsAdapter.asyncDifferList.submitList(airingTodayShowsList)

                    }
                    is Resource.Error->{
                        showSnackbar(airingTodayShowsResponse.message!!)

                    }
                    is Resource.Loading->{
                        Log.e("data","loading")
                    }
                }
            }
        }
    }

    private fun getPopularShows(){
        showsViewModel.getPopularShows()
        viewLifecycleOwner.lifecycleScope.launch {
            showsViewModel.popularShows.collect{popularShowsResponse->
                when(popularShowsResponse){
                    is Resource.Success->{

                        val popularShowsList = popularShowsResponse.data!!.results

                        handleFilterDialog(popularShowsAdapter,popularShowsList,binding.imageViewPopularShowsFilter,"Choose An Option"){
                            popularShowsAdapter.asyncDifferList.submitList(it)
                        }

                        popularShowsAdapter.asyncDifferList.submitList(popularShowsList)

                    }
                    is Resource.Error->{
                        showSnackbar(popularShowsResponse.message!!)
                    }
                    is Resource.Loading->{
                        Log.e("data","loading")
                    }
                }
            }
        }
    }

    private fun getTopRatedShows(){
        showsViewModel.getTopRatedShows()
        viewLifecycleOwner.lifecycleScope.launch {
            showsViewModel.topRatedShows.collect{topRatedShowsResponse->
                when(topRatedShowsResponse){
                    is Resource.Success->{

                        val topRatedShowsList = topRatedShowsResponse.data!!.results

                        handleFilterDialog(topRatedShowsAdapter,topRatedShowsList,binding.imageViewTopRatedShows,"Choose An Option"){
                            topRatedShowsAdapter.asyncDifferList.submitList(it)
                        }

                        topRatedShowsAdapter.asyncDifferList.submitList(topRatedShowsList)

                    }
                    is Resource.Error->{
                        showSnackbar(topRatedShowsResponse.message!!)

                    }
                    is Resource.Loading->{
                        Log.e("data","loading")
                    }
                }
            }
        }
    }




    private fun handleRv(){

        binding.apply {

            topRatedShowsAdapter = TopRatedShowsAdapter(object : TopRatedShowsAdapter.OnItemClickListener {
                override fun onItemClick(currentMovieOrShow: Result) {
                    handleNavigation(currentMovieOrShow.id)

                }
            })
            onTheAirShowsAdapter = OnTheAirShowsAdapter(object : OnTheAirShowsAdapter.OnItemClickListener {
                override fun onItemClick(currentMovieOrShow: Result) {
                    handleNavigation(currentMovieOrShow.id)
                }
            })
            popularShowsAdapter = PopularShowsAdapter(object : PopularShowsAdapter.OnItemClickListener {
                override fun onItemClick(currentMovieOrShow: Result) {
                    handleNavigation(currentMovieOrShow.id)

                }
            })
            airingTodayShowsAdapter = AiringTodayShowsAdapter(object : AiringTodayShowsAdapter.OnItemClickListener {
                override fun onItemClick(currentMovieOrShow: Result) {
                    handleNavigation(currentMovieOrShow.id)

                }
            })

            rvTopRatedShows.adapter = topRatedShowsAdapter
            rvTopRatedShows.layoutManager = StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL)

            rvPopularShows.adapter = popularShowsAdapter
            rvPopularShows.layoutManager = StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL)

            rvAiringTodayShows.adapter = airingTodayShowsAdapter
            rvAiringTodayShows.layoutManager = StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL)

            rvOnTheAirShows.adapter = onTheAirShowsAdapter
            rvOnTheAirShows.layoutManager = StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL)
        }
    }


    private fun handleNavigation(showData:Int){
        val showToPass = ShowsFragmentDirections.actionShowsFragmentToShowDetailFragment(showData)
        findNavController().navigate(showToPass)
    }


    private fun <T : RecyclerView.Adapter<*>> handleFilterDialog(
        adapter:T,
        showsList:List<Result>,
        filterImage:ImageView,
        title:String,
        updateAdapter:(List<Result>)->Unit
    ){
        val optionsList = arrayOf(MovieType.IMDB_RATE.movieType,MovieType.RELEASE_DATE.movieType,MovieType.ADULT_CONTENT.movieType,MovieType.DEFAULT.movieType)
        filterImage.setOnClickListener {

            val alertDialog = AlertDialog.Builder(requireContext())
            alertDialog.setTitle(title)
            alertDialog.setItems(optionsList){_,options->

                val sortedList = when(options){

                    0->showsList.sortedBy { show->show.voteAverage }
                    1->showsList.sortedBy { show->show.firstAirDate }
                    2->showsList.sortedBy { show->show.adult }
                    else->showsList
                }

                updateAdapter(sortedList)

            }.create().show()

        }




    }



}