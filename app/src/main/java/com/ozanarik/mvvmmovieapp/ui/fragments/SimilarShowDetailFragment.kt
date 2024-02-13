package com.ozanarik.mvvmmovieapp.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import com.ozanarik.mvvmmovieapp.business.models.people_model.allpeoplelist.Result
import com.ozanarik.mvvmmovieapp.databinding.FragmentSimilarShowDetailBinding
import com.ozanarik.mvvmmovieapp.ui.adapters.peopleadapter.PopularPeopleAdapter
import com.ozanarik.mvvmmovieapp.ui.adapters.showsadapters.shows_credit_adapter.ShowsCreditAdapter
import com.ozanarik.mvvmmovieapp.ui.adapters.showsadapters.similar_shows.SimilarShowsAdapter
import com.ozanarik.mvvmmovieapp.ui.viewmodels.ShowsViewModel
import com.ozanarik.mvvmmovieapp.utils.CONSTANTS
import com.ozanarik.mvvmmovieapp.utils.Extensions.Companion.showSnackbar
import com.ozanarik.mvvmmovieapp.utils.Resource
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.DecimalFormat

@AndroidEntryPoint
class SimilarShowDetailFragment : Fragment() {
    private lateinit var binding:FragmentSimilarShowDetailBinding
    private lateinit var showsViewModel: ShowsViewModel
    private lateinit var showsCreditAdapter: ShowsCreditAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentSimilarShowDetailBinding.inflate(inflater,container,false)


        showsViewModel = ViewModelProvider(this)[ShowsViewModel::class.java]
        handleRv()

        handleShowCredit()
        getShowYoutubeTrailer()
        handleShowDetail()
        handleReviewFragmentNavigation()


        return binding.root
    }





    private fun handleShowCredit(){
        val showDetailArgs:ShowDetailFragmentArgs by navArgs()

        val detailedShowData = showDetailArgs.showData

        showsViewModel.getShowCredit(detailedShowData)
        viewLifecycleOwner.lifecycleScope.launch {
            showsViewModel.showCreditData.collect{showCreditResponse->
                when(showCreditResponse){
                    is Resource.Success->{

                        binding.apply {
                            val maxActorsAndActresses = 4
                            val actorActressList = showCreditResponse.data?.cast

                            val castList = actorActressList?.let { it.take(maxActorsAndActresses).joinToString(", "){it.name} }

                            tvSimilarShowCast.text = "Cast : $castList"
                            showsCreditAdapter.asyncDifferList.submitList(showCreditResponse.data!!.cast)
                        }
                    }
                    is Resource.Loading-> {showSnackbar("Fetching Data")}

                    is Resource.Error->showSnackbar(showCreditResponse.message!!)
                }

            }
        }

    }


    private fun getShowYoutubeTrailer(){

        val showDetailArgs:ShowDetailFragmentArgs by navArgs()

        val detailedShowData = showDetailArgs.showData

        showsViewModel.getShowYoutubeTrailer(detailedShowData)
        viewLifecycleOwner.lifecycleScope.launch {
            showsViewModel.showYoutubeTrailerData.collect{showYoutubeTrailerResponse->
                when(showYoutubeTrailerResponse){
                    is Resource.Success->{

                        binding.tvSimilarShowYtTrailer.setOnClickListener {

                            val result = showYoutubeTrailerResponse.data?.results

                            if (!result.isNullOrEmpty()){
                                val movieUri = Uri.parse(result[0].key)
                                val intent = Intent(Intent.ACTION_VIEW,movieUri)
                                val intentChooser = Intent.createChooser(intent, "Choose An Action")
                                startActivity(intentChooser)
                            }


                        }

                    }
                    is Resource.Error->{showSnackbar(showYoutubeTrailerResponse.message!!)}
                    is Resource.Loading->{showSnackbar("Fetching Data")}
                }
            }
        }

    }


    private fun handleReviewFragmentNavigation(){

        val showDataDetail:SimilarShowDetailFragmentArgs by navArgs()

        val showData = showDataDetail.showData
        binding.tvSimilarShowReview.setOnClickListener {


            val bundle = Bundle().apply {
                putInt("showData",showData)
            }

            val showReviewFragment = ShowReviewsBottomSheetFragment()
            showReviewFragment.arguments = bundle


            showReviewFragment.show(requireActivity().supportFragmentManager,showReviewFragment.tag)




        }
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

                        val showDetail = detailedShowData.data

                        val decimalFormat = DecimalFormat("#.##")

                        binding.apply {
                            tvSimilarShowImdbRate.text = "IMDB / ${decimalFormat.format(detailedShowData.data!!.voteAverage)}"
                            tvSimilarShowOriginalName.text = showDetail!!.name
                            Picasso.get().load(CONSTANTS.IMAGE_BASE_URL + showDetail.posterPath).into(binding.imageViewSimilarShowDetail)
                            tvSimilarShowDescription.text = showDetail.overview

                            showDetail?.tagline?.let {

                                if (it.isEmpty()){
                                    tvSimilarShowTagline.text = ""
                                }else{
                                    tvSimilarShowTagline.text = it
                                }
                            }

                            cvHomePageSimilarShowDetail.setOnClickListener {

                                val uri = Uri.parse(detailedShowData.data.homepage)
                                val intent = Intent(Intent.ACTION_VIEW,uri)

                                val intentChooser = Intent.createChooser(intent,"Choose An Action")
                                startActivity(intentChooser)

                            }


                            val showGenres = showDetail.genres.joinToString(", "){it.name}
                            tvSimilarShowGenre.text = showGenres

                            val episodeRunTimeList = showDetail.episodeRunTime


                            val episodeRunTimeString = episodeRunTimeList.joinToString(", "){it.toString()}

                            tvSimilarShowRunTime.text = "$episodeRunTimeString min"
                            tvSimilarShowOriginalLanguage.text = showsViewModel.getMovieLanguage(showDetail.originalLanguage)
                        }
                    }
                    is Resource.Error->{
                        showSnackbar(detailedShowData.message!!)
                    }
                    is Resource.Loading->{
                        showSnackbar("Fetching Data")
                    }
                }
            }
        }
    }



    private fun  handleRv(){

        showsCreditAdapter = ShowsCreditAdapter(object : ShowsCreditAdapter.OnItemClickListener {
            override fun onCastClicked(currentCast: com.ozanarik.mvvmmovieapp.business.models.shows_model.shows_credits_cast_model.Cast) {

                val personToPass = SimilarShowDetailFragmentDirections.actionSimilarShowDetailFragmentToPopularPeopleDetailFragment (currentCast.id)
                findNavController().navigate(personToPass)


            }
        })

        binding.apply {
            rvSimilarShowCast.adapter = showsCreditAdapter
            rvSimilarShowCast.setHasFixedSize(true)
            rvSimilarShowCast.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
        }

    }
}