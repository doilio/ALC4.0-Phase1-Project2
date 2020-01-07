package com.dowy.travelmantics.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dowy.travelmantics.repository.ITravelDealRepository
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class TravelDealViewModelFactory(private val repository: ITravelDealRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TravelDealViewModel::class.java)) {
            return TravelDealViewModel(repository) as T
        }
        throw IllegalArgumentException("Invalid ViewModel Class")
    }

}