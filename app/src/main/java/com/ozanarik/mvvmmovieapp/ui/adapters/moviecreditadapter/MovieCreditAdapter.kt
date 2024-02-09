package com.ozanarik.mvvmmovieapp.ui.adapters.moviecreditadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ozanarik.mvvmmovieapp.R
import com.ozanarik.mvvmmovieapp.business.models.movie_model.Cast
import com.ozanarik.mvvmmovieapp.databinding.MovieCreditItemListBinding
import com.ozanarik.mvvmmovieapp.utils.CONSTANTS.Companion.IMAGE_BASE_URL
import com.squareup.picasso.Picasso

class MovieCreditAdapter (private val onItemClickListener: OnItemClickListener) :RecyclerView.Adapter<MovieCreditAdapter.MovieCreditHolder>() {

    inner class MovieCreditHolder(val binding: MovieCreditItemListBinding):RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<Cast>(){
        override fun areItemsTheSame(
            oldItem: Cast, newItem: Cast): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Cast, newItem: Cast
        ): Boolean {
            return oldItem == newItem
        }
    }

    val asyncDifferList = AsyncListDiffer(this,diffUtil)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCreditHolder {
        val layoutFrom = LayoutInflater.from(parent.context)

        val binding:MovieCreditItemListBinding = MovieCreditItemListBinding.inflate(layoutFrom,parent,false)
        return MovieCreditHolder(binding)
    }
    override fun onBindViewHolder(holder: MovieCreditHolder, position: Int) {
        val currentCast = asyncDifferList.currentList[position]

        holder.binding.apply {
            tvCastName.text = currentCast.name
            Picasso.get().load(IMAGE_BASE_URL + currentCast.profilePath).placeholder(R.drawable.placeholder).error(R.drawable.baseline_error_24).into(imageViewCastPath)

        }

        holder.itemView.setOnClickListener {
            onItemClickListener.onPersonClick(currentCast)
        }

    }

    override fun getItemCount(): Int {
        return asyncDifferList.currentList.size
    }

    interface OnItemClickListener{
        fun onPersonClick(currentPerson:Cast)
    }

}