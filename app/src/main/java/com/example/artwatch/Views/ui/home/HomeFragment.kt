package com.example.artwatch.Views.ui.home

import android.R
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.artwatch.databinding.FragmentHomeBinding
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val sharedPrefFile = "kotlinsharedpreference"
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        val executorService = Executors.newSingleThreadScheduledExecutor()
        executorService.scheduleAtFixedRate({ onShakeImage() }, 0, 2, TimeUnit.SECONDS)
        updateLocation()

        return root
    }

    fun onShakeImage() {
        val shake: Animation
        shake =
            AnimationUtils.loadAnimation(context, com.example.artwatch.R.anim.shakeanimation)
        val imgBell: ImageView = binding.sosImg
        imgBell.startAnimation(shake) // starts animation
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            updateLocation()
        } else {
        }
    }

    override fun onResume() {
        updateLocation()
        super.onResume()
    }


    fun updateLocation(){
        val sharedPreferences: SharedPreferences = this.requireActivity().getSharedPreferences(sharedPrefFile,
            Context.MODE_PRIVATE)
        val sharedNameValue = sharedPreferences.getString("name","There")
        val latitude = sharedPreferences.getString("latitude","")
        val longitude = sharedPreferences.getString("longitude","")
        val address = sharedPreferences.getString("address","")
        val city = sharedPreferences.getString("city","")
        val country = sharedPreferences.getString("country","")

        val (firstName, lastName) = splitName(sharedNameValue.toString())

        val textView: TextView = binding.profileName
        val latLogTextView: TextView = binding.latlog
        val addressTextView: TextView = binding.address
        val cityTextView: TextView = binding.city
        textView.text = firstName
        latLogTextView.text = "Latitude: " + latitude + ", Longitude: " + longitude
        addressTextView.text = address
        cityTextView.text = city + " , " + country

    }

    fun splitName(name: String): Pair<String?, String?> {
        val names = name.trim().split(Regex("\\s+"))
        return names.firstOrNull() to names.lastOrNull()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}