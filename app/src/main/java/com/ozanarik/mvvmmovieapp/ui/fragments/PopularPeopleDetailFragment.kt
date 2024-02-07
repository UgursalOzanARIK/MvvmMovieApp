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
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ozanarik.mvvmmovieapp.R
import com.ozanarik.mvvmmovieapp.databinding.FragmentPopularPeopleDetailBinding
import com.ozanarik.mvvmmovieapp.ui.viewmodels.PeopleViewModel
import com.ozanarik.mvvmmovieapp.utils.CONSTANTS.Companion.IMAGE_BASE_URL
import com.ozanarik.mvvmmovieapp.utils.Extensions.Companion.showSnackbar
import com.ozanarik.mvvmmovieapp.utils.Resource
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PopularPeopleDetailFragment : BottomSheetDialogFragment() {
    private lateinit var binding:FragmentPopularPeopleDetailBinding
    private lateinit var peopleViewModel: PeopleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        peopleViewModel=ViewModelProvider(this)[PeopleViewModel::class.java]
        binding = FragmentPopularPeopleDetailBinding.inflate(inflater,container,false)

        handleView()



        return binding.root
    }



    private fun handleView(){

        val personArgs:PopularPeopleDetailFragmentArgs by navArgs()

        val personData = personArgs.personData


        peopleViewModel.getPersonDetail(personData)
        viewLifecycleOwner.lifecycleScope.launch {
            peopleViewModel.personDetail.collect{personDetailResponse->
                when(personDetailResponse){
                    is Resource.Success->{

                        val personDetail = personDetailResponse.data

                        binding.apply {

                            Picasso.get().load(IMAGE_BASE_URL + personDetail!!.profilePath).
                            placeholder(R.drawable.placeholder).
                                    error(R.drawable.baseline_error_24).into(imageViewPersonProfile)

                            tvImdbId.text = "Imdb Id : ${personDetail.imdbİd}"
                            tvAlsoKnownAs.text = personDetail.alsoKnownAs.toString()
                            tvPersonBiography.text = personDetail.biography
                            tvPlaceAndDateOfBirth.text = "Place of Birth : ${personDetail.placeOfBirth}\n" +
                                    "Date of Birth : ${personDetail.birthday}"
                            Picasso.get().load(IMAGE_BASE_URL + personDetail!!.profilePath).
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

        binding.apply {






        }
    }

}