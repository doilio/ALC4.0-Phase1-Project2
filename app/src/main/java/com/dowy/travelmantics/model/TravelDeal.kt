package com.dowy.travelmantics.model

import java.io.Serializable

data class TravelDeal(
    var title: String,
    var filter_title: String,
    var description: String,
    var price: String,
    var imageUrl: String,
    var imageName: String
) : Serializable {

    var id: String? = null
        set(value) {
            if (value != "") {
                field = value
            }
        }

    constructor() : this( "", "", "", "", "", "")
}