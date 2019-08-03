package com.dowy.travelmantics

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: TravelDealViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val adapter = DealAdapter()
        val recyclerView = recycler_deals

        viewModel = ViewModelProviders.of(this).get(TravelDealViewModel::class.java)
        viewModel.readTravelDeal().observe(this, Observer {
            adapter.setDeals(it)
            Log.d("Teste", "Dados $it")
        })


        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        Log.d("Teste", "Numero de Itens no Adapter ${adapter.itemCount}")

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.new_travel_deal ->
                openInsertActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openInsertActivity() {
        val i = Intent(this, InsertActivity::class.java)
        startActivity(i)
    }
}
