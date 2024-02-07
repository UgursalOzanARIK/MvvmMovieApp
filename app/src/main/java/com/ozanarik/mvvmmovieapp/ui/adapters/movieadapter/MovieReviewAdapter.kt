package com.ozanarik.mvvmmovieapp.ui.adapters.movieadapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ozanarik.mvvmmovieapp.R
import com.ozanarik.mvvmmovieapp.business.models.movie_model.MovieReviewModel
import com.ozanarik.mvvmmovieapp.business.models.movie_model.ResultXX
import com.ozanarik.mvvmmovieapp.databinding.MovieReviewsListItemBinding
import com.ozanarik.mvvmmovieapp.utils.CONSTANTS.Companion.IMAGE_BASE_URL
import com.squareup.picasso.Picasso
import java.time.format.DateTimeFormatter

class MovieReviewAdapter:RecyclerView.Adapter<MovieReviewAdapter.MovieReviewHolder>() {

    inner class MovieReviewHolder(val binding:MovieReviewsListItemBinding):RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<ResultXX>(){
        override fun areItemsTheSame(oldItem: ResultXX, newItem: ResultXX
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ResultXX, newItem: ResultXX): Boolean {
            return oldItem == newItem
        }
    }

    val asyncDifferList = AsyncListDiffer(this,diffUtil)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieReviewHolder {
        val layoutFrom = LayoutInflater.from(parent.context)
        val binding:MovieReviewsListItemBinding = MovieReviewsListItemBinding.inflate(layoutFrom,parent,false)

        return MovieReviewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MovieReviewHolder, position: Int) {
        val currentReview = asyncDifferList.currentList[position]

        holder.binding.apply {

            tvAuthor.text = currentReview.author
            Picasso.get().load(IMAGE_BASE_URL + currentReview.authorDetails.avatarPath).placeholder(R.drawable.placeholder).error(R.drawable.baseline_error_24).into(imageViewAuthorPath)
            tvComment.text = currentReview.content



            val createdDate = currentReview.createdAt.substring(0,10)
            val updatedAt = currentReview.updatedAt.substring(0,10)


            tvCreatedAt.text = "Created at : $createdDate"
            tvUpdatedAt.text = "Updated at : $updatedAt"

        }

    }

    override fun getItemCount(): Int {
        return asyncDifferList.currentList.size
    }
}