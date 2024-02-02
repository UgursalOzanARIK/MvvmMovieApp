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
import com.ozanarik.mvvmmovieapp.business.shows_model.Result
import com.ozanarik.mvvmmovieapp.databinding.FragmentShowsBinding
import com.ozanarik.mvvmmovieapp.ui.adapters.showsadapters.TopRatedShowsAdapter
import com.ozanarik.mvvmmovieapp.ui.viewmodels.ShowsViewModel
import com.ozanarik.mvvmmovieapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShowsFragment : Fragment() {
    private lateinit var binding:FragmentShowsBinding
    private lateinit var showsViewModel:ShowsViewModel
    private lateinit var topRatedShowsAdapter: TopRatedShowsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        showsViewModel = ViewModelProvider(this)[ShowsViewModel::class.java]



        binding = FragmentShowsBinding.inflate(inflater,container,false)


        topRatedShowsAdapter = TopRatedShowsAdapter(object : TopRatedShowsAdapter.OnItemClickListener {
            override fun onItemClick(currentMovieOrShow: Result) {
                Log.e("asd","asd")
            }
        })

        binding.rvTopRated.adapter = topRatedShowsAdapter
        binding.rvTopRated.layoutManager = StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL)

        showsViewModel.getTopRatedShows()
        viewLifecycleOwner.lifecycleScope.launch {
            showsViewModel.topRatedShows.collect{topRatedShowsResponse->
                when(topRatedShowsResponse){
                    is Resource.Success->{

                        topRatedShowsAdapter.asyncDifferList.submitList(topRatedShowsResponse.data!!.results)

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


        return binding.root
    }

}