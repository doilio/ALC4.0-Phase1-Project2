package com.dowy.travelmantics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_insert.*

class InsertActivity : AppCompatActivity() {

    private lateinit var viewModel: TravelDealViewModel
    private lateinit var travelDeal: TravelDeal


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)
        supportActionBar!!.title = "Insert Travel Deal"
        viewModel = ViewModelProviders.of(this).get(TravelDealViewModel::class.java)
        val intent = intent.getSerializableExtra(SELECTED_DEAL)
        if (intent != null) {
             travelDeal = intent as TravelDeal

            supportActionBar!!.title = "Update Travel Deal"
            input_title.setText(travelDeal.title)
            input_price.setText(travelDeal.price)
            input_description.setText(travelDeal.description)
        }else{
            travelDeal = TravelDeal()
        }

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
        travelDeal.title = input_title.text.toString().trim()
        travelDeal.price = input_price.text.toString().trim()
        travelDeal.description = input_description.text.toString().trim()

        if (travelDeal.id == null || travelDeal.id.equals("")) {
            viewModel.saveTravelDeal(travelDeal)
        } else {
            viewModel.updateTravelDeal(travelDeal)
        }
        cleanFields()
    }

    private fun cleanFields() {
        input_title.text?.clear()
        input_title.requestFocus()
        input_price.text?.clear()
        input_description.text?.clear()
    }

}
