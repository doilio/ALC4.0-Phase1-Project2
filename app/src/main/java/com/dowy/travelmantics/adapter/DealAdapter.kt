package com.dowy.travelmantics.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dowy.travelmantics.adapter.DealAdapter.MyViewHolder.Companion.from
import com.dowy.travelmantics.databinding.DealsItemBinding
import com.dowy.travelmantics.model.TravelDeal

class DealAdapter : ListAdapter<TravelDeal, DealAdapter.MyViewHolder>(TravelDealDiffCallBack()) {


    class MyViewHolder private constructor(val binding: DealsItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(currentTravelDeal: TravelDeal) {
            binding.apply {
                textviewTitle.text = currentTravelDeal.title
                textviewDescription.text = currentTravelDeal.description

                travelDeal = currentTravelDeal
                executePendingBindings()
            }

        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DealsItemBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    class TravelDealDiffCallBack : DiffUtil.ItemCallback<TravelDeal>() {
        override fun areItemsTheSame(oldItem: TravelDeal, newItem: TravelDeal): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TravelDeal, newItem: TravelDeal): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentTravelDeal = getItem(position)
        holder.bind(currentTravelDeal)
    }


}