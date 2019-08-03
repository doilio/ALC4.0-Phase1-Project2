package com.dowy.travelmantics

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DealAdapter : RecyclerView.Adapter<DealAdapter.MyViewHolder>() {

    private var deals = emptyList<TravelDeal>()

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textTitle: TextView = itemView.findViewById(R.id.textview_title)
        val textDescription: TextView = itemView.findViewById(R.id.textview_description)
        val textPrice: TextView = itemView.findViewById(R.id.textview_price)
    }

    internal fun setDeals(deals: List<TravelDeal>) {
        this.deals = deals
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.deals_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return deals.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentTravelDeal = deals[position]

        holder.textTitle.text = currentTravelDeal.title
        holder.textDescription.text = currentTravelDeal.description
        holder.textPrice.text = currentTravelDeal.price
    }


}