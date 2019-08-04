package com.dowy.travelmantics

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: TravelDealViewModel
    private lateinit var adapter: DealAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Utils.setContext(this)


    }

    private fun loadTravelDeals() {
        adapter = DealAdapter()
        val recyclerView = recycler_deals

        viewModel = ViewModelProviders.of(this).get(TravelDealViewModel::class.java)
        viewModel.readTravelDeal().observe(this, Observer {
            if (it.isNullOrEmpty()) {
                showEmptyLayout()
            } else {
                adapter.setDeals(it)
                hideEmptyLayout()
            }
        })
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun hideEmptyLayout() {
        image_empty.visibility = View.GONE
        text_empty.visibility = View.GONE
        recycler_deals.visibility = View.VISIBLE
    }

    private fun showEmptyLayout() {
        image_empty.visibility = View.VISIBLE
        text_empty.visibility = View.VISIBLE
        recycler_deals.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val insertMenu = menu!!.findItem(R.id.new_travel_deal)
        if (Utils.isAdmin){
            insertMenu.setVisible(true)
        }else{
            insertMenu.setVisible(false)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.new_travel_deal ->
                openInsertActivity()
            R.id.logout ->
                logout()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun logout() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                Log.d("MainActivity", "User logged out!")
                Utils.attachListener()
            }
        Utils.detachListener()
    }

    private fun openInsertActivity() {
        val i = Intent(this, InsertActivity::class.java)
        startActivity(i)
    }

    override fun onPause() {
        super.onPause()
        Utils.detachListener()
    }

    override fun onResume() {
        super.onResume()
        loadTravelDeals()
        Utils.attachListener()
    }

     fun showMenu() {
        invalidateOptionsMenu()
    }
}
