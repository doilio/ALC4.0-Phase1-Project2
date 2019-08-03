package com.dowy.travelmantics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_insert.*

class InsertActivity : AppCompatActivity() {

    private lateinit var viewModel: TravelDealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)

        viewModel = ViewModelProviders.of(this).get(TravelDealViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_menu ->
                saveDeal()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveDeal() {
        val title = input_title.text.toString().trim()
        val price = input_price.text.toString().trim()
        val description = input_description.text.toString().trim()

        val travelDeal = TravelDeal(title, description, price, "")
        viewModel.saveTravelDeal(travelDeal)
        cleanFields()
    }

    private fun cleanFields() {
        input_title.text?.clear()
        input_title.requestFocus()
        input_price.text?.clear()
        input_description.text?.clear()
    }

}
