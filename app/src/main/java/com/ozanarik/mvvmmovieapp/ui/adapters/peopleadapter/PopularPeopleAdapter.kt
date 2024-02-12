package com.ozanarik.mvvmmovieapp.ui.adapters.peopleadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ozanarik.mvvmmovieapp.R
import com.ozanarik.mvvmmovieapp.business.models.people_model.allpeoplelist.Result
import com.ozanarik.mvvmmovieapp.databinding.PopularPeopleItemListBinding
import com.ozanarik.mvvmmovieapp.utils.CONSTANTS.Companion.IMAGE_BASE_URL
import com.squareup.picasso.Picasso

class PopularPeopleAdapter (private val personClickListener: OnItemClickListener) :RecyclerView.Adapter<PopularPeopleAdapter.PopularPeopleHolder>() {

    inner class PopularPeopleHolder(val binding:PopularPeopleItemListBinding):RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }

    val asyncDifferList = AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularPeopleHolder {
        val layoutFrom = LayoutInflater.from(parent.context)
        val binding:PopularPeopleItemListBinding = PopularPeopleItemListBinding.inflate(layoutFrom,parent,false)
        return PopularPeopleHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularPeopleHolder, position: Int) {
        val currentPerson = asyncDifferList.currentList[position]

        holder.binding.apply {

            Picasso.get().load(IMAGE_BASE_URL + currentPerson.profilePath).placeholder(R.drawable.placeholder).error(R.drawable.baseline_error_24).into(imageViewPersonProfilePath)
            tvPersonName.text = currentPerson.name
            tvPersonPopularity.text = "Popularity : ${currentPerson.popularity}"
        }

        holder.itemView.setOnClickListener {
            personClickListener.onPersonClick(currentPerson)
        }

    }

    override fun getItemCount(): Int {
        return asyncDifferList.currentList.size
    }


    interface OnItemClickListener{
        fun onPersonClick(currentPerson: Result)
    }

}