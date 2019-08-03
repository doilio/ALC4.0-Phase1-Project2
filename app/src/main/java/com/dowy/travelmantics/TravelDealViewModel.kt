package com.dowy.travelmantics

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.ArrayList

class TravelDealViewModel : ViewModel() {

    private val repository = TravelDealRepository()
    private val travelDeals = MutableLiveData<List<TravelDeal>>()

    /**
     * Save Data on Firestore
     */
    fun saveTravelDeal(travelDeal: TravelDeal) {
        repository.saveTravelDeal(travelDeal).addOnFailureListener {
            Log.d(VIEWMODEL_TAG, "Error saving TravelDeal: $it")
        }
    }

    /**
     * Read TravelDeal
     */
    fun readTravelDeal(): LiveData<List<TravelDeal>> {
        repository.readTravelDeals().addSnapshotListener { querySnapshot, firebaseFirestoreException ->

            if (firebaseFirestoreException != null) {
                Log.d(VIEWMODEL_TAG, "Error reading TravelDeal $firebaseFirestoreException")
                travelDeals.value = null
            }

            val travelDealList = ArrayList<TravelDeal>()
            for (document in querySnapshot!!) {
                val travelDeal: TravelDeal = document.toObject(TravelDeal::class.java)
                travelDeal.id = document.id
                travelDealList.add(travelDeal)
            }
            travelDeals.value = travelDealList

        }
        return travelDeals
    }

    /**
     * Update TravelDeal
     */
    fun updateTravelDeal(travelDeal: TravelDeal) {
        repository.updateTravelDeal(travelDeal).addOnFailureListener {
            Log.d(VIEWMODEL_TAG, "Error updating TravelDeal: $it")
        }
    }
}


