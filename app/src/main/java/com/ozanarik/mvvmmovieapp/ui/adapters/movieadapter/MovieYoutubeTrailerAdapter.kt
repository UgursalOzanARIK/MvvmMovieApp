package com.ozanarik.mvvmmovieapp.ui.adapters.movieadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ozanarik.mvvmmovieapp.business.models.movie_model.MovieYoutubeTrailerModel
import com.ozanarik.mvvmmovieapp.business.models.movie_model.ResultX
import com.ozanarik.mvvmmovieapp.databinding.MovieandshowWebviewYoutubeTrailerItemListBinding
import com.ozanarik.mvvmmovieapp.utils.CONSTANTS.Companion.YOUTUBE_TRAILER_BASE_URL

class MovieYoutubeTrailerAdapter:RecyclerView.Adapter<MovieYoutubeTrailerAdapter.MovieTrailerHolder>() {

    inner class MovieTrailerHolder(val binding: MovieandshowWebviewYoutubeTrailerItemListBinding):RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<ResultX>(){
        override fun areItemsTheSame(
            oldItem: ResultX,
            newItem: ResultX
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ResultX,
            newItem: ResultX
        ): Boolean {
            return oldItem == newItem
        }
    }

    val asyncDifferList = AsyncListDiffer(this,diffUtil)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieTrailerHolder {
        val layoutFrom = LayoutInflater.from(parent.context)
        val binding:MovieandshowWebviewYoutubeTrailerItemListBinding = MovieandshowWebviewYoutubeTrailerItemListBinding.inflate(layoutFrom,parent,false)

        return MovieTrailerHolder(binding)
    }
    override fun onBindViewHolder(holder: MovieTrailerHolder, position: Int) {
        val currentMovieTrailer = asyncDifferList.currentList[position]

        holder.binding.apply {
            webViewYoutube.apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
                settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                loadUrl(YOUTUBE_TRAILER_BASE_URL + currentMovieTrailer.key)


            }
        }

    }

    override fun getItemCount(): Int {
        return asyncDifferList.currentList.size
    }
}