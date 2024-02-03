package com.ozanarik.mvvmmovieapp.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ozanarik.mvvmmovieapp.R
import com.ozanarik.mvvmmovieapp.databinding.FragmentShowDetailBinding
import com.ozanarik.mvvmmovieapp.ui.viewmodels.ShowsViewModel
import com.ozanarik.mvvmmovieapp.utils.CONSTANTS.Companion.IMAGE_BASE_URL
import com.ozanarik.mvvmmovieapp.utils.Extensions.Companion.formatEpisodeTime
import com.ozanarik.mvvmmovieapp.utils.Extensions.Companion.showSnackbar
import com.ozanarik.mvvmmovieapp.utils.Resource
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.DecimalFormat

@AndroidEntryPoint
class ShowDetailFragment : Fragment() {
    private lateinit var binding: FragmentShowDetailBinding
    private lateinit var showsViewModel: ShowsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        showsViewModel = ViewModelProvider(this)[ShowsViewModel::class.java]

        binding = FragmentShowDetailBinding.inflate(inflater,container,false)





        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleShowDetail()

    }




    @SuppressLint("SetTextI18n")
    private fun handleShowDetail(){

        val showDetailArgs:ShowDetailFragmentArgs by navArgs()

        val detailedShowData = showDetailArgs.showData


        viewLifecycleOwner.lifecycleScope.launch {

            showsViewModel.getDetailedShowData(detailedShowData)
        }

        viewLifecycleOwner.lifecycleScope.launch {

            showsViewModel.detailedShowData.collect{detailedShowData->

                when(detailedShowData){
                    is Resource.Success->{


                            val decimalFormat = DecimalFormat("#.##")

                        binding.tvShowImdb.text = "IMDB / ${decimalFormat.format(detailedShowData.data!!.voteAverage)}"
                        binding.tvShowTitle.text = detailedShowData.data.originalName

                            Picasso.get().load(IMAGE_BASE_URL + detailedShowData.data.posterPath).into(binding.imageViewPosterPath)

                        binding.tvShowOriginalLanguage.text = when(detailedShowData.data.originalLanguage){
                            "en"->"English"
                            "de"->"German"
                            "ja"->"Japanese"
                            "es"->"Spanish"
                            else->"en"
                        }


                        binding.tvOverView.text = detailedShowData.data.overview

                        binding.tvShowTagline.text = " \"${detailedShowData.data.tagline}\" "


                        binding.tvShowEpisodeRunTime.text = detailedShowData.data.episodeRunTime.formatEpisodeTime()

                        binding.cvLastEpisode.setOnClickListener {


                                val bundle = Bundle().apply {
                                    putSerializable("showEpisodeDetailData",detailedShowData.data)
                                }



                            val lastEpisodeFragment = LastEpisodeFragment()
                            lastEpisodeFragment.arguments = bundle



                            lastEpisodeFragment.show(requireActivity().supportFragmentManager,"lastEpisodeFragment")




                        }


                        binding.cvSeasons.setOnClickListener {

                            val seasonsList = detailedShowData.data.seasons

                            var totalEpisodeCount = 0

                            val totalEpisodesForAllSeasons = seasonsList.forEach { episodes->
                                totalEpisodeCount+=episodes.episodeCount
                            }

                            val popupMenu = PopupMenu(requireContext(),binding.cvSeasons)

                            popupMenu.menuInflater.inflate(R.menu.popup_menu,popupMenu.menu)

                            popupMenu.setOnMenuItemClickListener { menuItem->
                                when(menuItem.itemId){
                                    R.id.action_Seasons->{
                                        showSnackbar("${seasonsList.size} seasons")
                                        true
                                    }
                                    R.id.action_Episodes->{
                                        showSnackbar("Total Episodes : $totalEpisodeCount")
                                        true
                                    }
                                    else->{
                                        false
                                    }
                                }

                            }
                            popupMenu.show()
                        }

                        binding.cvHomePage.setOnClickListener{

                            val homePageUri = Uri.parse(detailedShowData.data.homepage)

                            val homePage = Intent(Intent.ACTION_VIEW,homePageUri)

                            val homePageIntent = Intent.createChooser(homePage,"Choose An Action")
                            startActivity(homePageIntent)


                        }




                    }
                    is Resource.Error->{
                        Log.e("asd",detailedShowData.message!!)
                    }
                    is Resource.Loading->{
                        Log.e("asd","loading")
                    }
                }


            }



        }






    }

}