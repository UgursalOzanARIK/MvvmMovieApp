package com.ozanarik.mvvmmovieapp.ui.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ozanarik.mvvmmovieapp.business.model.Result
import com.ozanarik.mvvmmovieapp.databinding.MoviesListItemBinding
import com.ozanarik.mvvmmovieapp.utils.CONSTANTS.Companion.IMAGE_BASE_URL
import com.ozanarik.mvvmmovieapp.utils.Extensions.Companion.doubleToFloat
import com.squareup.picasso.Picasso

class MovieAdapter(private val onItemClickListener: OnItemClickListener) :RecyclerView.Adapter<MovieAdapter.MovieHolder>() {


    inner class MovieHolder(val binding: MoviesListItemBinding):RecyclerView.ViewHolder(binding.root)


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
        val binding:MoviesListItemBinding = MoviesListItemBinding.inflate(layoutFrom,parent,false)
        return MovieHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MovieHolder, position: Int) {

        val currentMovie = asyncDifferList.currentList[position]

        holder.apply {
            binding.tvMovieName.text = currentMovie.originalTitle
            binding.tvReleaseDate.text = "Release Date : ${currentMovie.releaseDate}"
            Picasso.get().load(IMAGE_BASE_URL + currentMovie.posterPath).into(binding.imageViewPosterPath)
            binding.ratingBar.rating = currentMovie.voteAverage.doubleToFloat()
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