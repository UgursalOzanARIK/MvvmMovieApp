package com.ozanarik.mvvmmovieapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ozanarik.mvvmmovieapp.R
import com.ozanarik.mvvmmovieapp.business.models.people_model.allpeoplelist.Result
import com.ozanarik.mvvmmovieapp.databinding.FragmentPopularPeopleBinding
import com.ozanarik.mvvmmovieapp.ui.adapters.peopleadapter.PopularPeopleAdapter
import com.ozanarik.mvvmmovieapp.ui.viewmodels.PeopleViewModel
import com.ozanarik.mvvmmovieapp.utils.Extensions.Companion.showSnackbar
import com.ozanarik.mvvmmovieapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PopularPeopleFragment : Fragment(),SearchView.OnQueryTextListener {
    private lateinit var binding:FragmentPopularPeopleBinding
    private lateinit var peopleViewModel: PeopleViewModel

    private lateinit var popularPeopleAdapter: PopularPeopleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment


        binding = FragmentPopularPeopleBinding.inflate(inflater,container,false)
        peopleViewModel = ViewModelProvider(this)[PeopleViewModel::class.java]

        handleRv()
        getPopularPeople()



        (activity as AppCompatActivity).setSupportActionBar(binding.popularPeopleToolbar)


        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.popular_people_menu,menu)
                val searchItem = menu.findItem(R.id.action_Search_Person)
                val searchView = searchItem.actionView as SearchView
                searchView.setOnQueryTextListener(this@PopularPeopleFragment)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }
        },viewLifecycleOwner,Lifecycle.State.RESUMED)



        return binding.root
    }


    private fun getPopularPeople(){
        peopleViewModel.getPopularPeople()
        viewLifecycleOwner.lifecycleScope.launch {
            peopleViewModel.popularPeopleList.collect{popularPeopleResponse->
                when(popularPeopleResponse){
                    is Resource.Success->{

                        popularPeopleAdapter.asyncDifferList.submitList(popularPeopleResponse.data!!.results)

                    }
                    is Resource.Loading->{
                        showSnackbar("Fetching People")
                    }
                    is Resource.Error->{
                        showSnackbar(popularPeopleResponse.message!!)
                    }
                }
            }
        }
    }

    private fun handleRv(){
        popularPeopleAdapter = PopularPeopleAdapter(object : PopularPeopleAdapter.OnItemClickListener {
            override fun onPersonClick(currentPerson: Result) {


                val bundle = Bundle().apply {
                    putInt("personData",currentPerson.id)
                }

                findNavController().navigate(R.id.action_popularPeopleFragment_to_popularPeopleDetailFragment,bundle)


            }
        })

        binding.rvPopularPeople.adapter = popularPeopleAdapter
        binding.rvPopularPeople.layoutManager = StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL)
        binding.rvPopularPeople.setHasFixedSize(false)


    }

    override fun onQueryTextSubmit(query: String?): Boolean {

        query?.let { handlePersonSearch(it) }


        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }


    private fun handlePersonSearch(query: String){
        peopleViewModel.searchPopularPerson(query)
        viewLifecycleOwner.lifecycleScope.launch {
            peopleViewModel.searchedPerson.collect{searchedPersonResponse->
                when(searchedPersonResponse){
                    is Resource.Success->{
                        searchedPersonResponse?.let { popularPeopleAdapter.asyncDifferList.submitList(searchedPersonResponse.data!!.results) }
                    }
                    is Resource.Error->{
                        showSnackbar(searchedPersonResponse.message!!)
                    }
                    is Resource.Loading->{
                        showSnackbar("Fetching Data")
                    }
                }
            }
        }
    }

}