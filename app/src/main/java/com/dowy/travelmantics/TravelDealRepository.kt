package com.dowy.travelmantics

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import com.google.firebase.storage.FirebaseStorage

class TravelDealRepository {

    private val firebaseDB = FirebaseFirestore.getInstance()
    private val firebaseStorage = FirebaseStorage.getInstance()

    fun saveTravelDeal(travelDeal: TravelDeal): Task<DocumentReference> {
        val travelDealRef = firebaseDB.collection(TRAVEL_DEALS)

        return travelDealRef.add(travelDeal)
    }

    fun readTravelDeals(): Query {
        return firebaseDB.collection(TRAVEL_DEALS).orderBy(FILTER_TITLE)
    }

    fun updateTravelDeal(travelDeal: TravelDeal): Task<Void> {
        val travelDealRef = firebaseDB.collection(TRAVEL_DEALS)
            .document(travelDeal.id!!)

        return travelDealRef.set(travelDeal, SetOptions.merge())
    }

    fun deleteTravelDeal(travelDeal: TravelDeal): Task<Void> {
        val travelDealRef = firebaseDB.collection(TRAVEL_DEALS)
            .document(travelDeal.id!!)

       return travelDealRef.delete()
    }

    fun deleteTravelDealImage() {

    }

}

