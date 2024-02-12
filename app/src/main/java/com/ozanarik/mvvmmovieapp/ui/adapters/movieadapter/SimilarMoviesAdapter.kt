package com.ozanarik.mvvmmovieapp.ui.adapters.movieadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ozanarik.mvvmmovieapp.R
import com.ozanarik.mvvmmovieapp.business.models.movie_model.movie_response.Result
import com.ozanarik.mvvmmovieapp.databinding.PopularPeopleRelatedMoviesItemListBinding
import com.ozanarik.mvvmmovieapp.utils.CONSTANTS.Companion.IMAGE_BASE_URL
import com.squareup.picasso.Picasso

class SimilarMoviesAdapter (private val onItemClickListener: OnItemClickListener) :RecyclerView.Adapter<SimilarMoviesAdapter.SimilarMovieHolder>() {

    inner class SimilarMovieHolder(val binding: PopularPeopleRelatedMoviesItemListBinding):RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }

    val asyncDifferList = AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarMovieHolder {
        val layoutFrom = LayoutInflater.from(parent.context)
        val binding:PopularPeopleRelatedMoviesItemListBinding = PopularPeopleRelatedMoviesItemListBinding.inflate(layoutFrom,parent,false)
        return SimilarMovieHolder(binding)
    }

    override fun onBindViewHolder(holder: SimilarMovieHolder, position: Int) {
        val currentSimilarMovie = asyncDifferList.currentList[position]

        holder.binding.apply {
            Picasso.get().load(IMAGE_BASE_URL + currentSimilarMovie.posterPath).placeholder(R.drawable.placeholder).error(R.drawable.baseline_error_24).into(imageViewPersonRelatedMoviePath)
        }

        holder.itemView.setOnClickListener {
            onItemClickListener.onSimilarMovieClick(currentSimilarMovie)
        }


    }

    override fun getItemCount(): Int {
        return asyncDifferList.currentList.size
    }

    interface OnItemClickListener{
        fun onSimilarMovieClick(currentSimilarMovie: Result?)
    }
}