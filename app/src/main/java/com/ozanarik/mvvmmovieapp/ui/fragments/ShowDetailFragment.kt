package com.ozanarik.mvvmmovieapp.ui.fragments

import android.annotation.SuppressLint
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
import com.ozanarik.mvvmmovieapp.business.models.shows_model.Result
import com.ozanarik.mvvmmovieapp.databinding.FragmentShowDetailBinding
import com.ozanarik.mvvmmovieapp.ui.adapters.showsadapters.shows_credit_adapter.ShowsCreditAdapter
import com.ozanarik.mvvmmovieapp.ui.adapters.showsadapters.similar_shows.SimilarShowsAdapter
import com.ozanarik.mvvmmovieapp.ui.viewmodels.ShowsViewModel
import com.ozanarik.mvvmmovieapp.utils.CONSTANTS.Companion.IMAGE_BASE_URL
import com.ozanarik.mvvmmovieapp.utils.Extensions.Companion.showSnackbar
import com.ozanarik.mvvmmovieapp.utils.Resource
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.DecimalFormat

@AndroidEntryPoint
class ShowDetailFragment : Fragment() {
    private lateinit var binding: FragmentShowDetailBinding
    private lateinit var showsViewModel: ShowsViewModel

    private lateinit var showsCreditAdapter: ShowsCreditAdapter
    private lateinit var similarShowsAdapter: SimilarShowsAdapter

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
        handleRv()
        handleShowCredit()
        getShowYoutubeTrailer()
        getSimilarShows()
        handleReviewFragmentNavigation()

    }


    private fun getSimilarShows(){
        val showDetailArgs:ShowDetailFragmentArgs by navArgs()

        val detailedShowData = showDetailArgs.showData

        showsViewModel.getSimilarShows(detailedShowData)
        viewLifecycleOwner.lifecycleScope.launch {
            showsViewModel.similarShowData.collect{similarShowsResponse->
                when(similarShowsResponse){
                    is Resource.Success->{similarShowsAdapter.asyncDifferList.submitList(similarShowsResponse.data!!.results)}
                    is Resource.Loading->{"Fetching Data"}
                    is Resource.Error->{showSnackbar(similarShowsResponse.message!!)}
                }
            }
        }
    }

    private fun handleReviewFragmentNavigation(){

        val showDataDetail:SimilarShowDetailFragmentArgs by navArgs()

        val showData = showDataDetail.showData
        binding.tvShowReview.setOnClickListener {


            val bundle = Bundle().apply {
                putInt("showData",showData)
            }

            val showReviewFragment = ShowReviewsBottomSheetFragment()
            showReviewFragment.arguments = bundle


            showReviewFragment.show(requireActivity().supportFragmentManager,showReviewFragment.tag)




        }
    }

    private fun handleShowCredit(){
        val showDetailArgs:ShowDetailFragmentArgs by navArgs()

        val detailedShowData = showDetailArgs.showData

        showsViewModel.getShowCredit(detailedShowData)
        viewLifecycleOwner.lifecycleScope.launch {
            showsViewModel.showCreditData.collect{showCreditResponse->
                when(showCreditResponse){
                    is Resource.Success->{showsCreditAdapter.asyncDifferList.submitList(showCreditResponse.data!!.cast) }
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

                        viewLifecycleOwner.lifecycle.addObserver(binding.showTrailerYoutubePlayer)

                        binding.apply {
                            showTrailerYoutubePlayer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener(){
                                override fun onReady(youTubePlayer: YouTubePlayer) {

                                    val showTrailer = showYoutubeTrailerResponse.data?.results
                                    if (showTrailer.isNullOrEmpty()){
                                        showSnackbar("No trailer found for the show...")
                                    }else{
                                        val videoId = showTrailer?.first()?.key
                                        if (videoId!=null){
                                            youTubePlayer.loadVideo(videoId,0.5f)
                                        }
                                    }

                                }
                            })
                        }

                    }
                    is Resource.Error->{showSnackbar(showYoutubeTrailerResponse.message!!)}
                    is Resource.Loading->{showSnackbar("Fetching Data")}
                }
            }
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
                            tvShowImdb.text = "IMDB / ${decimalFormat.format(detailedShowData.data!!.voteAverage)}"
                            tvShowTitle.text = showDetail!!.originalName
                            Picasso.get().load(IMAGE_BASE_URL + showDetail.posterPath).into(binding.imageViewPosterPath)
                            tvOverView.text = showDetail.overview

                            showDetail?.tagline?.let {

                                if (it.isEmpty()){
                                    tvShowTagline.text = ""
                                }else{
                                    tvShowTagline.text = it
                                }
                            }

                            val showGenres = showDetail.genres.joinToString(", "){it.name}
                            tvShowGenre.text = showGenres

                            val episodeRunTimeList = showDetail.episodeRunTime


                            val episodeRunTimeString = episodeRunTimeList.joinToString(", "){it.toString()}

                            tvShowRunTime.text = "$episodeRunTimeString min"
                            tvShowLanguage.text = showsViewModel.getMovieLanguage(showDetail.originalLanguage)

                        }

                        binding.fabInfo.setOnClickListener {

                            val bundle = Bundle().apply {
                                putSerializable("showEpisodeDetailData",showDetail)
                            }
                            val showInfoFragment = ShowInfo()
                            showInfoFragment.arguments = bundle


                            showInfoFragment.show(requireActivity().supportFragmentManager,showInfoFragment.tag)

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


    private fun handleRv(){

        showsCreditAdapter = ShowsCreditAdapter(object : ShowsCreditAdapter.OnItemClickListener {
            override fun onCastClicked(currentCast: com.ozanarik.mvvmmovieapp.business.models.shows_model.shows_credits_cast_model.Cast) {

                val personToPass = ShowDetailFragmentDirections.actionShowDetailFragmentToPopularPeopleDetailFragment(currentCast.id)
                findNavController().navigate(personToPass)


            }
        })

        similarShowsAdapter = SimilarShowsAdapter(object : SimilarShowsAdapter.OnItemClickListener {
            override fun onItemClick(similarShow: Result) {

                val similarShowToPass = ShowDetailFragmentDirections.actionShowDetailFragmentToSimilarShowDetailFragment(similarShow.id)
                findNavController().navigate(similarShowToPass)

            }
        })


        binding.apply {
            rvShowCast.adapter = showsCreditAdapter
            rvShowCast.setHasFixedSize(true)
            rvShowCast.layoutManager = StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL)

            rvSimilarShows.adapter = similarShowsAdapter
            rvSimilarShows.layoutManager = StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL)
            rvSimilarShows.setHasFixedSize(true)
        }

    }
}