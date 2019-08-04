package com.dowy.travelmantics.repository

import com.dowy.travelmantics.model.TravelDeal
import com.dowy.travelmantics.utils.ADMIN
import com.dowy.travelmantics.utils.DEALS_PICTURES
import com.dowy.travelmantics.utils.FILTER_TITLE
import com.dowy.travelmantics.utils.TRAVEL_DEALS
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import com.google.firebase.storage.FirebaseStorage

class TravelDealRepository {

    private val firebaseDB = FirebaseFirestore.getInstance()
    private val firebaseStorage = FirebaseStorage.getInstance().reference

    /**
     * Saves the Travel deal to Firebase's Firestore
     */
    fun saveTravelDeal(travelDeal: TravelDeal): Task<DocumentReference> {
        val travelDealRef = firebaseDB.collection(TRAVEL_DEALS)

        return travelDealRef.add(travelDeal)
    }

    /**
     * Reads the Travel deal from Firebase's Firestore and filters the results by name
     * PS** I Used filter_title instead of title because,
     * firestore's filtering is case sensetive.
     * filter_title is just filter.toLowerCase()
     */
    fun readTravelDeals(): Query {
        return firebaseDB.collection(TRAVEL_DEALS).orderBy(FILTER_TITLE)
    }

    /**
     * Updates the Travel deals on Firebase's Firestore
     */
    fun updateTravelDeal(travelDeal: TravelDeal): Task<Void> {
        val travelDealRef = firebaseDB.collection(TRAVEL_DEALS)
            .document(travelDeal.id!!)

        return travelDealRef.set(travelDeal, SetOptions.merge())
    }

    /**
     * Deletes the Travel deals on Firebase's Firestore
     */
    fun deleteTravelDeal(travelDeal: TravelDeal): Task<Void> {
        val travelDealRef = firebaseDB.collection(TRAVEL_DEALS)
            .document(travelDeal.id!!)

        return travelDealRef.delete()
    }

    /**
     * Reads the Admin Collectrion to assist on Authorization functions implemented on this app
     */
    fun readAdmins(userId: String): DocumentReference {
        val admin = firebaseDB.collection(ADMIN)

        return admin.document(userId)
    }

    /**
     * Deletes the Travel deal's image from the storage as soon as the Travel Deal is deleted
     */
    fun deleteTravelDealImage(travelDeal: TravelDeal): Task<Void> {
        val imageRef = firebaseStorage
            .child(DEALS_PICTURES).child(travelDeal.imageName)

        return imageRef.delete()
    }

}

