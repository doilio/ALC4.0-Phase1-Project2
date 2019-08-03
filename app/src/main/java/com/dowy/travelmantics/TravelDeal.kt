package com.dowy.travelmantics

data class TravelDeal(
    val title: String,
    val description: String,
    val price: String,
    val imageUrl: String
){

    var id: String = ""
        set(value) {
            if (value != "")
                field = value
        }

constructor(): this("","","","")
}