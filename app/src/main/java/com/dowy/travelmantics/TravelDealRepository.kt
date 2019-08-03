package com.dowy.travelmantics

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class TravelDealRepository {

    private val firebaseDB = FirebaseFirestore.getInstance()
    private val firebaseStorage = FirebaseStorage.getInstance()

    fun saveDeal(travelDeal: TravelDeal): Task<DocumentReference> {
        val travelDealRef = firebaseDB.collection(TRAVEL_DEALS)

        return travelDealRef.add(travelDeal)
    }

}

