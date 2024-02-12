package com.ozanarik.mvvmmovieapp.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ozanarik.mvvmmovieapp.R
import com.ozanarik.mvvmmovieapp.databinding.FragmentPopularPeopleDetailBinding
import com.ozanarik.mvvmmovieapp.ui.adapters.peopleadapter.PopularPeopleRelatedMoviesAdapter
import com.ozanarik.mvvmmovieapp.ui.viewmodels.PeopleViewModel
import com.ozanarik.mvvmmovieapp.utils.CONSTANTS.Companion.IMAGE_BASE_URL
import com.ozanarik.mvvmmovieapp.utils.Extensions.Companion.showSnackbar
import com.ozanarik.mvvmmovieapp.utils.Resource
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PopularPeopleDetailFragment : Fragment() {
    private lateinit var binding:FragmentPopularPeopleDetailBinding
    private lateinit var peopleViewModel: PeopleViewModel

    private lateinit var popularPeopleRelatedMoviesAdapter: PopularPeopleRelatedMoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        peopleViewModel=ViewModelProvider(this)[PeopleViewModel::class.java]
        binding = FragmentPopularPeopleDetailBinding.inflate(inflater,container,false)

        handlePopularPersonDetail()
        handlePersonRelatedMoviesRv()

        getPersonRelatedMovies()

        return binding.root
    }


    private fun getPersonRelatedMovies(){
        val personArgs:PopularPeopleDetailFragmentArgs by navArgs()

        val personData = personArgs.personData

        peopleViewModel.getPersonRelatedMovies(personData)

        viewLifecycleOwner.lifecycleScope.launch {
            peopleViewModel.personRelatedMovie.collect{personRelatedMovieResponse->

                when(personRelatedMovieResponse){
                    is Resource.Success->{

                        popularPeopleRelatedMoviesAdapter.asyncDifferList.submitList(personRelatedMovieResponse.data!!.cast)

                    }
                    is Resource.Error->{
                        showSnackbar(personRelatedMovieResponse.message!!)
                    }
                    is Resource.Loading->{
                        showSnackbar("Fetching Data")
                    }
                }

            }
        }

    }



    private fun handlePopularPersonDetail(){

        val personArgs:PopularPeopleDetailFragmentArgs by navArgs()

        val personData = personArgs.personData


        peopleViewModel.getPersonDetail(personData)
        viewLifecycleOwner.lifecycleScope.launch {
            peopleViewModel.personDetail.collect{personDetailResponse->
                when(personDetailResponse){
                    is Resource.Success->{

                        val personDetail = personDetailResponse.data

                        binding.apply {


                            tvPopularPersonName.text = personDetail?.name?:"N/A"
                            tvImdbId.text = "Imdb Id : ${personDetail!!.imdbİd}"
                            tvAlsoKnownAs.text = personDetail.alsoKnownAs.toString()
                            tvPersonBiography.text = personDetail.biography
                            tvPlaceAndDateOfBirth.text = "Place of Birth : ${personDetail.placeOfBirth}\n" +
                                    "Date of Birth : ${personDetail.birthday}"
                            Picasso.get().load(IMAGE_BASE_URL + personDetail.profilePath).
                            placeholder(R.drawable.placeholder).
                            error(R.drawable.baseline_error_24).into(imageViewPersonKnownFor)

                            val alsoKnownAs = personDetail.alsoKnownAs.joinToString ( ", " ){it.toString()}

                            tvAlsoKnownAs.text = "Also Known As : $alsoKnownAs"


                            imageViewHomePagePersonDetail.setOnClickListener {

                                val popularPersonHomePageUri = personDetail.homepage?.let { Uri.parse(it) }

                                val homePage = Intent(Intent.ACTION_VIEW,popularPersonHomePageUri)


                                startActivity(homePage)

                            }



                        }


                    }
                    is Resource.Error->{
                        showSnackbar(personDetailResponse.message!!)

                    }
                    is Resource.Loading->{
                        showSnackbar("Fetching Person Data...")
                    }
                }
            }
        }

    }

    private fun handlePersonRelatedMoviesRv(){
        popularPeopleRelatedMoviesAdapter = PopularPeopleRelatedMoviesAdapter(object : PopularPeopleRelatedMoviesAdapter.OnItemClickListener {
            override fun onPersonRelatedMoviesClick(currentPersonRelatedMovie: com.ozanarik.mvvmmovieapp.business.models.people_model.people_related_movies.Cast) {
                val movieToPass = PopularPeopleDetailFragmentDirections.actionPopularPeopleDetailFragmentToCastRelatedMovies(movieData = currentPersonRelatedMovie.id)
                findNavController().navigate(movieToPass)
            }
        })

        binding.apply {
            rvPopularPeopleMovies.layoutManager = StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL)
            rvPopularPeopleMovies.setHasFixedSize(true)
            rvPopularPeopleMovies.adapter = popularPeopleRelatedMoviesAdapter
        }
    }

}