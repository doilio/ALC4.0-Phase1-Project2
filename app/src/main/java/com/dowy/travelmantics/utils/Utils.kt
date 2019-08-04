package com.dowy.travelmantics.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import com.dowy.travelmantics.R
import com.dowy.travelmantics.activity.MainActivity
import com.dowy.travelmantics.repository.TravelDealRepository
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class Utils {

    /**
     * Companion Object to assist with firebase issues.
     */
    companion object FirebaseUtil {
        private val repository = TravelDealRepository()
        var isAdmin: Boolean = false

        @SuppressLint("StaticFieldLeak")
        private lateinit var activity: Activity

        fun setContext(act: Activity) {
            activity = act
        }

        private var mFirebaseAuth = FirebaseAuth.getInstance()
        private var mAuthStateListener = FirebaseAuth.AuthStateListener {
            if (it.currentUser == null) {
                signIn(activity)
            } else {
                val userId = mFirebaseAuth.currentUser!!.uid
                Log.d("Utils", userId)
                checkAdmin(userId)
            }

        }

        /**
         * Verifies if user is Admin or Regular user
         */
        private fun checkAdmin(userId: String) {
            repository.readAdmins(userId).get().addOnSuccessListener { document ->
                if (document.data != null) {
                    isAdmin = true
                    //activity = MainActivity()
                    (activity as MainActivity).showMenu()
                    Log.d("Utils", "DocumentSnapshot data: ${document.data}")
                } else {
                    isAdmin = false
                    Log.d("Utils", "No such document")
                }
            }.addOnFailureListener {
                Log.d("Utils", "Failed: $it")
            }
        }


        /**
         * Signs User into the App using AuthUI
         */
        private fun signIn(activity: Activity) {
            // Choose authentication providers
            val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build(),
                AuthUI.IdpConfig.GoogleBuilder().build()
            )

            // Create and launch sign-in intent
            activity.startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setLogo(R.drawable.logo)
                    .setAvailableProviders(providers)
                    .setIsSmartLockEnabled(false)
                    .build(), RC_SIGN_IN
            )
        }

        fun attachListener() {
            return mFirebaseAuth.addAuthStateListener(mAuthStateListener)
        }

        fun detachListener() {
            return mFirebaseAuth.removeAuthStateListener(mAuthStateListener)
        }

        fun imageRef(): StorageReference {
            val storageReference = FirebaseStorage.getInstance().reference
            return storageReference.child(DEALS_PICTURES)
        }
    }


}