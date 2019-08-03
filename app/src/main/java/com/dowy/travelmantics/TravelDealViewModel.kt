package com.dowy.travelmantics

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TravelDealViewModel : ViewModel() {

    private val repository = TravelDealRepository()
    private val travelDeals = MutableLiveData<List<TravelDeal>>()

    /**
     * Save Data on Firestore
     */
    fun saveTravelDeal(travelDeal: TravelDeal) {
        repository.saveTravelDeal(travelDeal).addOnFailureListener {
            Log.d("TravelDealViewModel", "Error saving TravelDeal")
        }
    }

    /**
     * Read TravelDeal
     */

}