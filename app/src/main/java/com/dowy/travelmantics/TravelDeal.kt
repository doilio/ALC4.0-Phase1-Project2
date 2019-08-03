package com.dowy.travelmantics

import java.io.Serializable


data class TravelDeal(
    var title: String,
    var description: String,
    var price: String,
    var imageUrl: String
) : Serializable {

    var id: String? = null
        set(value) {
            if (value != "")
                field = value
        }

    constructor() : this("", "", "", "")
}