package com.dowy.travelmantics.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dowy.travelmantics.model.TravelDeal
import com.dowy.travelmantics.repository.ITravelDealRepository
import com.dowy.travelmantics.utils.VIEWMODEL_TAG
import java.util.ArrayList

class TravelDealViewModel(private val repository: ITravelDealRepository) : ViewModel() {

    //private val repository = TravelDealRepository()
    private val travelDeals = MutableLiveData<List<TravelDeal>>()
    private val _navigateToInsertActivity = MutableLiveData<TravelDeal>()
    val navigateToInsertActivity: LiveData<TravelDeal>
        get() = _navigateToInsertActivity

    private val _logMsg = MutableLiveData<String>()
    val logMsg: LiveData<String>
        get() = _logMsg

    /**
     * Save Data on Firestore
     */
    fun saveTravelDeal(travelDeal: TravelDeal) {
        repository.saveTravelDeal(travelDeal).addOnFailureListener {
            Log.d(VIEWMODEL_TAG, "Error saving TravelDeal: $it")
        }.addOnSuccessListener {
            _logMsg.value = "Travel Deal Saved!"
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
                return@addSnapshotListener
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
        }.addOnSuccessListener {
            _logMsg.value = "Travel Deal Updated!"
        }
    }

    /**
     * Delete TravelDeal
     */
    fun deleteTravelDeal(travelDeal: TravelDeal) {
        repository.deleteTravelDeal(travelDeal).addOnFailureListener {
            Log.d(VIEWMODEL_TAG, "Error deleting TravelDeal: $it")
        }.addOnSuccessListener {
            if (travelDeal.imageName.isNotEmpty()) {
                deleteTravelDealImage(travelDeal)
            }
            _logMsg.value = "${travelDeal.title} deleted!"
        }

    }

    /**
     * Delete TravelDeal's Image
     */
    private fun deleteTravelDealImage(travelDeal: TravelDeal) {
        repository.deleteTravelDealImage(travelDeal).addOnFailureListener {
            Log.d(VIEWMODEL_TAG, "Error deleting TravelDeal's Image: $it")
        }

    }

    fun onTravelDealClicked(travelDeal: TravelDeal?) {
        _navigateToInsertActivity.value = travelDeal
    }

    fun onInsertActivityNavigated() {
        _navigateToInsertActivity.value = null
    }
}


