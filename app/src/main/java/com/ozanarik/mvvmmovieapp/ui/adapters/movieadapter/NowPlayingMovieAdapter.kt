package com.ozanarik.mvvmmovieapp.ui.adapters.movieadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ozanarik.mvvmmovieapp.business.movie_model.Result
import com.ozanarik.mvvmmovieapp.databinding.NowPlayingListItemBinding
import com.ozanarik.mvvmmovieapp.utils.CONSTANTS.Companion.IMAGE_BASE_URL
import com.squareup.picasso.Picasso

class NowPlayingMovieAdapter(private val onItemClickListener: OnItemClickListener) :RecyclerView.Adapter<NowPlayingMovieAdapter.MovieHolder>() {

    inner class MovieHolder(val binding: NowPlayingListItemBinding):RecyclerView.ViewHolder(binding.root)

    private val diffUtilCallBack = object : DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }

    val asyncDifferList = AsyncListDiffer(this,diffUtilCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val layoutFrom = LayoutInflater.from(parent.context)
        val binding:NowPlayingListItemBinding = NowPlayingListItemBinding.inflate(layoutFrom,parent,false)
        return MovieHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {

        val currentMovie = asyncDifferList.currentList[position]

        holder.apply {

            Picasso.get().load(IMAGE_BASE_URL + currentMovie.posterPath).into(binding.imageViewNowPlaying)

        }

        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(currentMovie)
        }
    }

    override fun getItemCount(): Int {
        return asyncDifferList.currentList.size
    }

    interface OnItemClickListener{
        fun onItemClick(currentMovieOrShow:Result)
    }
}