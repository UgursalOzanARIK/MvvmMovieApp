package com.ozanarik.mvvmmovieapp.ui.adapters.showsadapters.shows_credit_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ozanarik.mvvmmovieapp.R
import com.ozanarik.mvvmmovieapp.business.models.shows_model.shows_credits_cast_model.Cast
import com.ozanarik.mvvmmovieapp.databinding.MovieCreditItemListBinding
import com.ozanarik.mvvmmovieapp.utils.CONSTANTS.Companion.IMAGE_BASE_URL
import com.squareup.picasso.Picasso

class ShowsCreditAdapter (private val onItemClickListener: OnItemClickListener) :RecyclerView.Adapter<ShowsCreditAdapter.ShowsCreditHolder>() {

    inner class ShowsCreditHolder(val binding:MovieCreditItemListBinding):RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<Cast>(){
        override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
            return oldItem == newItem
        }
    }

    val asyncDifferList = AsyncListDiffer(this,diffUtil)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowsCreditHolder {
        val layoutFrom = LayoutInflater.from(parent.context)
        val binding:MovieCreditItemListBinding = MovieCreditItemListBinding.inflate(layoutFrom,parent,false)
        return ShowsCreditHolder(binding)
    }

    override fun onBindViewHolder(holder: ShowsCreditHolder, position: Int) {
        val currentCast = asyncDifferList.currentList[position]

        holder.binding.apply {
            Picasso.get().load(IMAGE_BASE_URL + currentCast.profilePath).error(R.drawable.baseline_error_24)
                .placeholder(R.drawable.placeholder)
                .into(imageViewCastPath)

            tvCastName.text = currentCast.name
        }

        holder.itemView.setOnClickListener {
            onItemClickListener.onCastClicked(currentCast)
        }

    }

    override fun getItemCount(): Int {
        return asyncDifferList.currentList.size

    }

    interface OnItemClickListener{
        fun onCastClicked(currentCast:Cast)
    }

}