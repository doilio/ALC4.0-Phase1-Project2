package com.dowy.travelmantics.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dowy.travelmantics.adapter.DealAdapter.MyViewHolder.Companion.from
import com.dowy.travelmantics.databinding.DealsItemBinding
import com.dowy.travelmantics.model.TravelDeal

class DealAdapter(private val clickListener: TravelDealListener) :
    ListAdapter<TravelDeal, DealAdapter.MyViewHolder>(TravelDealDiffCallBack()) {


    class MyViewHolder private constructor(val binding: DealsItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(currentTravelDeal: TravelDeal, clickListener: TravelDealListener) {
            binding.apply {
                textviewTitle.text = currentTravelDeal.title
                textviewDescription.text = currentTravelDeal.description
                binding.clickListener = clickListener
                travelDeal = currentTravelDeal
                binding.root.setOnClickListener {

                }
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int){
        holder.bind(getItem(position), clickListener)
    }

    class TravelDealDiffCallBack : DiffUtil.ItemCallback<TravelDeal>() {
        override fun areItemsTheSame(oldItem: TravelDeal, newItem: TravelDeal): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TravelDeal, newItem: TravelDeal): Boolean {
            return oldItem == newItem
        }

    }

    class TravelDealListener(val clickListener: (travelDeal: TravelDeal) -> Unit) {
        fun onClick(travelDeal: TravelDeal) = clickListener(travelDeal)
    }



}