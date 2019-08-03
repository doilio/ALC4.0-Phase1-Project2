package com.dowy.travelmantics

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DealAdapter : RecyclerView.Adapter<DealAdapter.MyViewHolder>() {

    private var deals = emptyList<TravelDeal>()

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val textTitle: TextView = itemView.findViewById(R.id.textview_title)
        val textDescription: TextView = itemView.findViewById(R.id.textview_description)
        val textPrice: TextView = itemView.findViewById(R.id.textview_price)

        override fun onClick(view: View?) {
            val position = adapterPosition
            Log.d("Teste", "Posicao: $position")
            val travelDeal: TravelDeal = deals[position]
            val i = Intent(view!!.context, InsertActivity::class.java)
            i.putExtra(SELECTED_DEAL, travelDeal)
            view.context.startActivity(i)
        }
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

        holder.itemView.setOnClickListener {
            holder.onClick(holder.itemView)
        }
        holder.textTitle.text = currentTravelDeal.title
        holder.textDescription.text = currentTravelDeal.description
        holder.textPrice.text = currentTravelDeal.price
    }


}