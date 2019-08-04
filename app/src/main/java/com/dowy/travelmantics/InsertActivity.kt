package com.dowy.travelmantics

import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import io.grpc.perfmark.PerfMark.task
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

            if (travelDeal.imageUrl.isNotEmpty()) {
                showImage(travelDeal.imageUrl)
            } else {
                Picasso.get().load(R.drawable.travel_icon).fit().centerCrop().into(image_travel_deal)
            }

        } else {
            travelDeal = TravelDeal()
        }
        button_upload.setOnClickListener {
            val i = Intent(Intent.ACTION_GET_CONTENT)
            i.type = "image/jpeg"
            i.putExtra(Intent.EXTRA_LOCAL_ONLY, true)

            startActivityForResult(Intent.createChooser(i, "Choose Picture:"), REQUEST_GALLERY)
        }

    }

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
                    Log.d("foto nome", imageName)
                    Log.d("foto url", url)

                    showImage(url)

                    travelDeal.imageUrl = url
                } else {
                    Log.d("InsertActivity", "Failed to Upload Picture: ${task.exception}")
                }
            }
        }
    }

    fun showImage(imageUrl: String) {
        val width = Resources.getSystem().displayMetrics.widthPixels
        Picasso.get()
            .load(imageUrl)
            .placeholder(R.drawable.loading)
            .fit()
            .centerCrop()
            .into(image_travel_deal)
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
            button_upload.visibility = View.INVISIBLE

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
