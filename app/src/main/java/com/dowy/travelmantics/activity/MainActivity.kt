package com.dowy.travelmantics.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dowy.travelmantics.R
import com.dowy.travelmantics.viewmodel.TravelDealViewModel
import com.dowy.travelmantics.adapter.DealAdapter
import com.dowy.travelmantics.databinding.ActivityMainBinding
import com.dowy.travelmantics.utils.MAINACTIVITY_TAG
import com.dowy.travelmantics.utils.Utils
import com.firebase.ui.auth.AuthUI

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: TravelDealViewModel
    private lateinit var adapter: DealAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // This is important so that we can make the interaction between
        // Utils and MainActivity.class
        Utils.setContext(this)

    }

    /**
     * Prepares the RecyclerView And the Adapter to receive
     * Data and populate it accordingly
     */
    private fun loadTravelDeals() {
        adapter = DealAdapter()
        //val recyclerView = recycler_deals

        viewModel = ViewModelProviders.of(this).get(TravelDealViewModel::class.java)
        viewModel.readTravelDeal().observe(this, Observer {
            if (it.isNullOrEmpty()) {
                showEmptyLayout()
            } else {
                adapter.setDeals(it)
                hideEmptyLayout()
            }
        })
        binding.recyclerDeals.adapter = adapter
    }

    /**
     *  Shows Travel Deals
     */
    private fun hideEmptyLayout() {
        binding.apply {
            imageEmpty.visibility = View.GONE
            textEmpty.visibility = View.GONE
            recyclerDeals.visibility = View.VISIBLE
        }
    }

    /**
     *  Shows a special layout when there are no Travel Deals
     */
    private fun showEmptyLayout() {
        binding.apply {
            imageEmpty.visibility = View.VISIBLE
            textEmpty.visibility = View.VISIBLE
            recyclerDeals.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val insertMenu = menu!!.findItem(R.id.new_travel_deal)
        insertMenu.isVisible = Utils.isAdmin
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.new_travel_deal -> openInsertActivity()
            R.id.logout -> logout()
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * User Sign out function
     */
    private fun logout() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                Log.d(MAINACTIVITY_TAG, getString(R.string.user_logged_out))
                Utils.attachListener()
            }
        Utils.detachListener()
    }

    private fun openInsertActivity() {
        val i = Intent(this, InsertActivity::class.java)
        startActivity(i)
    }

    /**
     * Notifying the App to stop listening to who is logged in, when
     * the user navigates away from the app
     */
    override fun onPause() {
        super.onPause()
        Utils.detachListener()
    }

    /**
     * Notifying the App about which user is Signed In, when
     * the user returns to the app
     */
    override fun onResume() {
        super.onResume()
        loadTravelDeals()
        Utils.attachListener()
    }

    /**
     * function used to alter Menu state in runtime
     */
    fun showMenu() {
        invalidateOptionsMenu()
    }
}
