package com.ozanarik.mvvmmovieapp.ui.adapters.showsadapters.similar_shows

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ozanarik.mvvmmovieapp.business.models.shows_model.Result
import com.ozanarik.mvvmmovieapp.databinding.MoviesItemListBinding
import com.ozanarik.mvvmmovieapp.utils.CONSTANTS
import com.squareup.picasso.Picasso

class SimilarShowsAdapter (private val onItemClickListener: OnItemClickListener) :RecyclerView.Adapter<SimilarShowsAdapter.SimilarShowsHolder>() {

    inner class SimilarShowsHolder(val binding:MoviesItemListBinding):RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object :DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }

    val asyncDifferList = AsyncListDiffer(this,diffUtil)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarShowsHolder {
        val layoutFrom = LayoutInflater.from(parent.context)
        val binding:MoviesItemListBinding = MoviesItemListBinding.inflate(layoutFrom,parent,false)
        return SimilarShowsHolder(binding)
    }

    override fun onBindViewHolder(holder: SimilarShowsHolder, position: Int) {
        val similarShow = asyncDifferList.currentList[position]

        holder.binding.apply {
            tvMovieName.text = similarShow.name
            Picasso.get().load(CONSTANTS.IMAGE_BASE_URL + similarShow.posterPath).into(imageViewPosterPath)

        }
        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(similarShow)
        }

    }

    override fun getItemCount(): Int {
        return asyncDifferList.currentList.size
    }

    interface OnItemClickListener{
        fun onItemClick(similarShow:Result)
    }

}