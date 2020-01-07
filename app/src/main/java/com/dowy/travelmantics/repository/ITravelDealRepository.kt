package com.dowy.travelmantics.repository

import com.dowy.travelmantics.model.TravelDeal
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query

interface ITravelDealRepository {
    /**
     * Saves the Travel deal to Firebase's Firestore
     */
    fun saveTravelDeal(travelDeal: TravelDeal): Task<DocumentReference>

    /**
     * Reads the Travel deal from Firebase's Firestore and filters the results by name
     * PS** I Used filter_title instead of title because,
     * firestore's filtering is case sensetive.
     * filter_title is just filter.toLowerCase()
     */
    fun readTravelDeals(): Query

    /**
     * Updates the Travel deals on Firebase's Firestore
     */
    fun updateTravelDeal(travelDeal: TravelDeal): Task<Void>

    /**
     * Deletes the Travel deals on Firebase's Firestore
     */
    fun deleteTravelDeal(travelDeal: TravelDeal): Task<Void>

    /**
     * Reads the Admin Collectrion to assist on Authorization functions implemented on this app
     */
    fun readAdmins(userId: String): DocumentReference

    /**
     * Deletes the Travel deal's image from the storage as soon as the Travel Deal is deleted
     */
    fun deleteTravelDealImage(travelDeal: TravelDeal): Task<Void>
}