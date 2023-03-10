package com.example.artwatch.Views.ui.profile

import android.content.Context
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.artwatch.Views.ui.Icommunicator
import com.example.artwatch.databinding.FragmentProfileBinding
import java.util.*

class profileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val sharedPrefFile = "kotlinsharedpreference"

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textNotifications
//        profileViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it

        val sharedPreferences: SharedPreferences = this.requireActivity().getSharedPreferences(sharedPrefFile,
            Context.MODE_PRIVATE)
        val name = sharedPreferences.getString("name","")
        val emailAddress = sharedPreferences.getString("email","")
        val phoneNumber = sharedPreferences.getString("phone","")
        val artistAddress = sharedPreferences.getString("address","")
        val countryOfResidence = sharedPreferences.getString("countryOfResidence","")
        val countryOfOrigin = sharedPreferences.getString("countryOfOrigin","")
        val socialID = sharedPreferences.getString("socialID","")
        val description = sharedPreferences.getString("details","")
        val NOK = sharedPreferences.getString("NOK","")
        val NOKPhone = sharedPreferences.getString("NOKPhone","")
        val DOB = sharedPreferences.getString("DOB","")
        val dp = sharedPreferences.getString("dp","")


        val artistDetails: TextView = binding.artistDetails
        val artistCountry: TextView = binding.artistCountry
        val artistDOB: TextView = binding.artistDOB
        val artistSsn: TextView = binding.artistSsn
        val artistNOK: TextView = binding.artistNOK
        val artistNOKPhone: TextView = binding.artistNOKPhone
        val email: TextView = binding.email
        val phone: TextView = binding.phone
        val address: TextView = binding.address
        val country: TextView = binding.country
        val profileName: TextView = binding.artistName


        profileName.text = name
        country.text = countryOfOrigin
        address.text = artistAddress
        phone.text = phoneNumber
        email.text = emailAddress
        artistNOKPhone.text = NOKPhone
        artistNOK.text = NOK
        artistSsn.text = socialID
        artistDOB.text = DOB
        artistCountry.text = countryOfResidence
        artistDetails.text = description

        decodeImage(dp.toString())
//        }

        return root
    }

    private fun decodeImage(imageString: String){
//        val decodedString: ByteArray = Base64.decode(encodedImage, Base64.DEFAULT)
        val profilePicture: ImageView = binding.dpImageView

        val decodedString: ByteArray = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Base64.getDecoder().decode(imageString)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        profilePicture.setImageBitmap(decodedByte)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}