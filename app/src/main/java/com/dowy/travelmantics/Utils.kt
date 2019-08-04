package com.dowy.travelmantics

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.core.app.ActivityCompat.startActivityForResult
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth

class Utils {

    companion object FirebaseUtil {
        @SuppressLint("StaticFieldLeak")
        private lateinit var activity: Activity

        fun setContext(act: Activity) {
            activity = act
        }

        private var mFirebaseAuth = FirebaseAuth.getInstance()
        private var mAuthStateListener = FirebaseAuth.AuthStateListener {
            if (it.currentUser == null){
                signIn(activity)
            }

        }

        fun signIn(activity: Activity) {
            // Choose authentication providers
            val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build(),
                AuthUI.IdpConfig.GoogleBuilder().build()
            )

            // Create and launch sign-in intent
            activity.startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
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
    }


}