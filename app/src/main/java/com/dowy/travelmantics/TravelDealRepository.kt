package com.dowy.travelmantics

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage

class TravelDealRepository {

    private val firebaseDB = FirebaseFirestore.getInstance()
    private val firebaseStorage = FirebaseStorage.getInstance()

    fun saveTravelDeal(travelDeal: TravelDeal): Task<DocumentReference> {
        val travelDealRef = firebaseDB.collection(TRAVEL_DEALS)

        return travelDealRef.add(travelDeal)
    }

    fun readTravelDeals(): CollectionReference {
        return firebaseDB.collection(TRAVEL_DEALS)
    }

    fun updateTravelDeal(travelDeal: TravelDeal): Task<Void> {
        val travelDealRef = firebaseDB.collection(TRAVEL_DEALS)
            .document(travelDeal.id!!)

        return travelDealRef.set(travelDeal, SetOptions.merge())
    }

    fun deleteTravelDeal() {

    }

    fun deleteTravelDealImage() {

    }

}

