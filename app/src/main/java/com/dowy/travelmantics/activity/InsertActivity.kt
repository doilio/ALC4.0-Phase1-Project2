package com.dowy.travelmantics.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dowy.travelmantics.R
import com.dowy.travelmantics.databinding.ActivityInsertBinding
import com.dowy.travelmantics.viewmodel.TravelDealViewModel
import com.dowy.travelmantics.model.TravelDeal
import com.dowy.travelmantics.repository.TravelDealRepository
import com.dowy.travelmantics.utils.INSERTACTIVITY_TAG
import com.dowy.travelmantics.utils.REQUEST_GALLERY
import com.dowy.travelmantics.utils.SELECTED_DEAL
import com.dowy.travelmantics.utils.Utils
import com.dowy.travelmantics.viewmodel.TravelDealViewModelFactory
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import java.util.*

class InsertActivity : AppCompatActivity() {

    private lateinit var viewModel: TravelDealViewModel
    private lateinit var viewModelFactory: TravelDealViewModelFactory
    private lateinit var repository: TravelDealRepository
    private lateinit var travelDeal: TravelDeal
    private lateinit var binding: ActivityInsertBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_insert)

        supportActionBar?.title = getString(R.string.insert_travel_deal)

        repository = TravelDealRepository()
        viewModelFactory = TravelDealViewModelFactory(repository)
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(TravelDealViewModel::class.java)

        /**
         * Receiving Intent from the MainActivity with the selected data
         */
        val intent = intent.getSerializableExtra(SELECTED_DEAL)
        if (intent != null) {
            travelDeal = intent as TravelDeal

            supportActionBar?.title = getString(R.string.update_travel_deal)
            binding.apply {
                inputTitle.setText(travelDeal.title)
                inputPrice.setText(travelDeal.price)
                inputDescription.setText(travelDeal.description)
            }

            if (travelDeal.imageUrl.isNotEmpty()) {
                showImage(travelDeal.imageUrl)
            } else {
                Picasso.get().load(R.drawable.travel_icon).fit().centerCrop()
                    .into(binding.imageTravelDeal)
            }

        } else {
            travelDeal = TravelDeal()
        }


        binding.buttonUpload.setOnClickListener { openGallery() }

    }

    private fun openGallery() {
        val i = Intent(Intent.ACTION_GET_CONTENT)
        i.type = "image/jpeg"
        i.putExtra(Intent.EXTRA_LOCAL_ONLY, true)

        startActivityForResult(
            Intent.createChooser(i, getString(R.string.choose_picture)),
            REQUEST_GALLERY
        )
    }

    /**
     * Method that handles the result received from the Intent that opens the gallery
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_GALLERY && resultCode == Activity.RESULT_OK) {
            val imageUri = data!!.data

            val storageRef = Utils.imageRef().child(imageUri?.lastPathSegment!!)
            val uploadTask = storageRef.putFile(imageUri)
            uploadTask.continueWithTask(
                Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    return@Continuation storageRef.downloadUrl
                }).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    val url = downloadUri.toString()
                    val imageName = downloadUri!!.path
                    travelDeal.imageName = imageName!!

                    showImage(url)

                    travelDeal.imageUrl = url
                } else {
                    Log.d(INSERTACTIVITY_TAG, "Failed to Upload Picture: ${task.exception}")
                }
            }
        }
    }

    /**
     * Using Picasso to load the Image into the ImageView
     */
    private fun showImage(imageUrl: String) {
        Picasso.get()
            .load(imageUrl)
            .placeholder(R.drawable.loading)
            .fit()
            .centerCrop()
            .into(binding.imageTravelDeal)
    }

    /**
     * Handling Menu Changes for different user roles (ADMIN vs REGULAR)
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save_menu, menu)
        if (Utils.isAdmin) {
            menu!!.findItem(R.id.save_menu).isVisible = true
            menu.findItem(R.id.delete_deal).isVisible = true
            enableInputFields(true)
        } else {
            supportActionBar?.title = "Travel Deals"
            menu!!.findItem(R.id.save_menu).isVisible = false
            menu.findItem(R.id.delete_deal).isVisible = false
            enableInputFields(false)
            binding.buttonUpload.visibility = View.INVISIBLE

        }
        return true
    }

    /**
     * Handling TextInputEditText's input options
     */
    private fun enableInputFields(enabled: Boolean) {
        binding.apply {
            inputTitle.isEnabled = enabled
            inputDescription.isEnabled = enabled
            inputPrice.isEnabled = enabled
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_menu -> saveDeal()
            R.id.delete_deal -> deleteDeal()
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Method that deletes the deal
     */
    private fun deleteDeal() {
        if (travelDeal.id.isEmpty()) {
            Toast.makeText(
                this,
                getString(R.string.you_have_to_save_deal_before_deleting),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            viewModel.deleteTravelDeal(travelDeal)
            mostrarMsg()
            finish()
        }

    }

    /**
     * Method that saves the deal
     */
    private fun saveDeal() {
        binding.apply {
            travelDeal.title = inputTitle.text.toString().trim()
            travelDeal.price = inputPrice.text.toString().trim()
            travelDeal.description = inputDescription.text.toString().trim()
            travelDeal.filter_title = travelDeal.title.toLowerCase(Locale.getDefault())

        }
        if (travelDeal.id.isEmpty()) {
            viewModel.saveTravelDeal(travelDeal)
            mostrarMsg()
            finish()
        } else {
            viewModel.updateTravelDeal(travelDeal)
            mostrarMsg()
            finish()
        }
    }

    /**
     * Function that receives updates messages from the ViewModel to inform the user what he has done
     */
    private fun mostrarMsg() {
        viewModel.logMsg.observe(
            this,
            Observer { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}
