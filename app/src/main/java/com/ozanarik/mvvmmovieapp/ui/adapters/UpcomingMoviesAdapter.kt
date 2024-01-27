package com.ozanarik.mvvmmovieapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ozanarik.mvvmmovieapp.business.model.Result
import com.ozanarik.mvvmmovieapp.databinding.MoviesItemListBinding
import com.ozanarik.mvvmmovieapp.utils.CONSTANTS.Companion.IMAGE_BASE_URL
import com.squareup.picasso.Picasso
import java.text.DecimalFormat

class UpcomingMoviesAdapter(private val onItemClickListener: OnItemClickListener): RecyclerView.Adapter<UpcomingMoviesAdapter.MovieHolder>() {

    inner class MovieHolder(val binding: MoviesItemListBinding):RecyclerView.ViewHolder(binding.root)
    private val diffUtil = object : DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }
    val asyncDifferList = AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val layoutFrom = LayoutInflater.from(parent.context)
        val binding:MoviesItemListBinding = MoviesItemListBinding.inflate(layoutFrom,parent,false)
        return MovieHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val currentMovieOrShow = asyncDifferList.currentList[position]
        holder.apply {
            binding.tvMovieName.text = currentMovieOrShow.originalTitle
            Picasso.get().load(IMAGE_BASE_URL + currentMovieOrShow.posterPath).into(binding.imageViewPosterPath)
            val imdbValueFormat = DecimalFormat("#.##")

            val movieImdb = imdbValueFormat.format(currentMovieOrShow.voteAverage)
            binding.tvImdb.text = movieImdb
        }
        holder.itemView.apply {
            onItemClickListener.onItemClick(currentMovieOrShow)
        }
    }

    override fun getItemCount(): Int {
        return asyncDifferList.currentList.size
    }
    interface OnItemClickListener{
        fun onItemClick(movie:Result)
    }
}