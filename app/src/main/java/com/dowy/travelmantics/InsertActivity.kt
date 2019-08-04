package com.dowy.travelmantics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
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
        } else {
            travelDeal = TravelDeal()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save_menu, menu)
        if (Utils.isAdmin) {
            menu!!.findItem(R.id.save_menu).setVisible(true)
            menu.findItem(R.id.delete_deal).setVisible(true)
            enableInputFields(true)
        } else {
            supportActionBar!!.title = "Travel Deals"
            menu!!.findItem(R.id.save_menu).setVisible(false)
            menu.findItem(R.id.delete_deal).setVisible(false)
            enableInputFields(false)
        }
        return true
    }

    fun enableInputFields(enabled: Boolean) {
        input_title.isEnabled = enabled
        input_description.isEnabled = enabled
        input_price.isEnabled = enabled
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_menu ->
                saveDeal()
            R.id.delete_deal ->
                deleteDeal()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteDeal() {
        if (travelDeal.id == null || travelDeal.id.equals("")) {
            Toast.makeText(this, "You have to save the deal before deleting!", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.deleteTravelDeal(travelDeal)
            mostrarMsg()
            finish()
        }

    }

    private fun saveDeal() {
        travelDeal.title = input_title.text.toString().trim()
        travelDeal.price = input_price.text.toString().trim()
        travelDeal.description = input_description.text.toString().trim()
        travelDeal.filter_title = travelDeal.title.toLowerCase()

        if (travelDeal.id == null || travelDeal.id.equals("")) {
            viewModel.saveTravelDeal(travelDeal)
            mostrarMsg()
            finish()
        } else {
            viewModel.updateTravelDeal(travelDeal)
            mostrarMsg()
            finish()
        }
    }

    private fun mostrarMsg() {
        viewModel.logMsg.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }

}
