package com.dowy.travelmantics.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.dowy.travelmantics.R
import com.dowy.travelmantics.model.TravelDeal
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.deals_item.view.*
import java.text.DecimalFormat

@BindingAdapter("travelDealImage")
fun ImageView.setTravelDealImage(item: TravelDeal?) {
    if (item?.imageUrl!!.isNotEmpty()) {
        Picasso.get()
            .load(item.imageUrl)
            .placeholder(R.drawable.loading)
            .fit()
            .centerCrop()
            .into(imageView)
    }
}

@BindingAdapter("travelDealPrice")
fun TextView.setDollarSign(item: TravelDeal?) {
    if (item?.price!!.isNotEmpty()) {
        //textview_price.text = "${item.price}$"
        val dec = DecimalFormat("#,###.00")
        val priceDouble = item.price.toDouble()
        textview_price.text =   "${dec.format(priceDouble)}$"
    }

}