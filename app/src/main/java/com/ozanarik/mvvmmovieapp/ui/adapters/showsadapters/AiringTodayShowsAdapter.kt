package com.ozanarik.mvvmmovieapp.ui.adapters.showsadapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ozanarik.mvvmmovieapp.business.models.shows_model.Result
import com.ozanarik.mvvmmovieapp.databinding.MoviesItemListBinding
import com.ozanarik.mvvmmovieapp.utils.CONSTANTS.Companion.IMAGE_BASE_URL
import com.squareup.picasso.Picasso

class AiringTodayShowsAdapter(private val onItemClickListener: OnItemClickListener):RecyclerView.Adapter<AiringTodayShowsAdapter.ShowHolder>() {


    inner class ShowHolder(val binding: MoviesItemListBinding):RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }

    val asyncDifferList = AsyncListDiffer(this,diffUtil)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowHolder {
        val layoutFrom = LayoutInflater.from(parent.context)

        val binding:MoviesItemListBinding = MoviesItemListBinding.inflate(layoutFrom,parent,false)
        return ShowHolder(binding)
    }

    override fun onBindViewHolder(holder: ShowHolder, position: Int) {
        val currentShow = asyncDifferList.currentList[position]

        holder.binding.apply {
            tvMovieName.text = currentShow.name
            Picasso.get().load(IMAGE_BASE_URL + currentShow.posterPath).into(imageViewPosterPath)


            holder.itemView.setOnClickListener {
                onItemClickListener.onItemClick(currentShow)
            }
        }

    }

    override fun getItemCount(): Int {
        return asyncDifferList.currentList.size
    }


    interface OnItemClickListener{
        fun onItemClick(currentMovieOrShow: Result)
    }

}