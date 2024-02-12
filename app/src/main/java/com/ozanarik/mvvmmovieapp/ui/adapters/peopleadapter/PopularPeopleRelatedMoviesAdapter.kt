package com.ozanarik.mvvmmovieapp.ui.adapters.peopleadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ozanarik.mvvmmovieapp.R
import com.ozanarik.mvvmmovieapp.business.models.people_model.people_related_movies.Cast
import com.ozanarik.mvvmmovieapp.databinding.PopularPeopleItemListBinding
import com.ozanarik.mvvmmovieapp.databinding.PopularPeopleRelatedMoviesItemListBinding
import com.ozanarik.mvvmmovieapp.utils.CONSTANTS.Companion.IMAGE_BASE_URL
import com.squareup.picasso.Picasso

class PopularPeopleRelatedMoviesAdapter (private val onItemClickListener: OnItemClickListener) :RecyclerView.Adapter<PopularPeopleRelatedMoviesAdapter.PopularPeopleRelatedMoviesHolder>() {

    inner class PopularPeopleRelatedMoviesHolder(val binding: PopularPeopleRelatedMoviesItemListBinding):RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object :DiffUtil.ItemCallback<Cast>(){
        override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
            return oldItem==newItem
        }
    }


    val asyncDifferList =AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PopularPeopleRelatedMoviesHolder {
        val layoutFrom = LayoutInflater.from(parent.context)
        val binding:PopularPeopleRelatedMoviesItemListBinding = PopularPeopleRelatedMoviesItemListBinding.inflate(layoutFrom,parent,false)
        return PopularPeopleRelatedMoviesHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularPeopleRelatedMoviesHolder, position: Int) {
        val currentPersonRelatedMovie = asyncDifferList.currentList[position]
        holder.binding.apply {
            Picasso.get().load(IMAGE_BASE_URL +currentPersonRelatedMovie.posterPath).error(R.drawable.baseline_error_24).placeholder(R.drawable.placeholder)
                .into(imageViewPersonRelatedMoviePath)
        }

        holder.itemView.setOnClickListener { onItemClickListener.onPersonRelatedMoviesClick(currentPersonRelatedMovie) }

    }

    override fun getItemCount(): Int {
        return asyncDifferList.currentList.size
    }

    interface OnItemClickListener{
        fun onPersonRelatedMoviesClick(currentPersonRelatedMovie:Cast)
    }
}