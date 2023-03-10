package com.example.artwatch.Adapters

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.opengl.ETC1.decodeImage
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.artwatch.Data.ArtistsModel
import com.example.artwatch.R
import java.util.*

class ArtistsAdapter(val activity: Activity): RecyclerView.Adapter<ArtistsAdapter.ViewHolder>() {

    private var artistsList: List<ArtistsModel>? = null

    fun setArtistsList(artistsList: List<ArtistsModel>?) {
        this.artistsList = artistsList
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.artists_details, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(artistsList?.get(position)!!, activity)
    }

    override fun getItemCount(): Int {
        if(artistsList == null)return 0
        else return artistsList?.size!!
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

//        val flagImage = view.flagImage
        private val tvName: TextView? = view.findViewById(R.id.txtName)
        private val description: TextView? = view.findViewById(R.id.description)
        private val country: TextView? = view.findViewById(R.id.country)
        private val address: TextView? = view.findViewById(R.id.address)
        private val dob: TextView? = view.findViewById(R.id.dOB)
        private val countryOfResidence: TextView? = view.findViewById(R.id.countryOfResidence)
        private val phoneNumber: TextView? = view.findViewById(R.id.phoneNumber)
        private val email: TextView? = view.findViewById(R.id.email)
        private val dp: ImageView? = view.findViewById(R.id.dpImageView)

        fun bind(get: ArtistsModel, activity: Activity) {
            tvName?.text = get.fullNames
            description?.text = get.descriptionOfWork
            country?.text = "Country: " + get.countryOfOrigin
            address?.text = "Address: " + get.address
            dob?.text = "Date Of Birth: " + get.dateOfBirth
            countryOfResidence?.text = "Country Of Residence: " + get.countryOfResidence
            phoneNumber?.text = get.phoneNumber
            email?.text = get.emailAddress

//            GlideToVectorYou.justLoadImage(activity, Uri.parse(data.flag), flagImage)

            val imageString = get.passportPhoto.toString()
            Log.e("ImageString", imageString)
            dp?.setImageBitmap(decodeImage(imageString))
        }

        private fun decodeImage(imageString: String?): Bitmap? {
            val decodedString: ByteArray = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Base64.getDecoder().decode(imageString)
            } else {
                TODO("VERSION.SDK_INT < O")
            }
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)

            return decodedByte
        }

    }

}
