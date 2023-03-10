package com.example.artwatch.Views

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.artwatch.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import java.io.IOException
import java.util.*
import kotlin.math.log

class SplashScreen : AppCompatActivity() {
    private val sharedPrefFile = "kotlinsharedpreference"
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch_screen)

        window.decorView.apply {
            // Hide both the navigation bar and the status bar.
            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        }


        /*AppSignatureHelper signatureHelper = new AppSignatureHelper(MainActivity.this);
        ArrayList<String> appSignatures = signatureHelper.getAppSignatures();
        String hash = appSignatures.get(0);
        Log.e("hash", hash);*/window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        Handler().postDelayed({
            val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,
                Context.MODE_PRIVATE)
            val logginStatus = sharedPreferences.getString("loggedIN","")
            val locationPermission = sharedPreferences.getString("locationPermissionGranted","")

            Log.e("Status", logginStatus.toString())
            if (logginStatus == "1"){
                getLocation()
                val intent = Intent(this@SplashScreen, Home::class.java)
                startActivity(intent)
            }else{
                if (locationPermission == "1"){
                    getLocation()
                    val intent = Intent(this@SplashScreen, LoginAndRegistration::class.java)
                    startActivity(intent)
                }else {
                    if (ContextCompat.checkSelfPermission(this@SplashScreen,
                            Manifest.permission.ACCESS_FINE_LOCATION) !==
                        PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this@SplashScreen,
                                Manifest.permission.ACCESS_FINE_LOCATION)) {
                            ActivityCompat.requestPermissions(this@SplashScreen,
                                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
                        } else {
                            ActivityCompat.requestPermissions(this@SplashScreen,
                                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
                        }
                    }

                }
            }
        }, 2000)
        
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                    if ((ContextCompat.checkSelfPermission(this@SplashScreen,
                            Manifest.permission.ACCESS_FINE_LOCATION) ===
                                PackageManager.PERMISSION_GRANTED)) {
                        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,
                            Context.MODE_PRIVATE)
                        val editor: SharedPreferences.Editor =  sharedPreferences.edit()

                        editor.putString("locationPermissionGranted", "1")
                        editor.apply()
                        editor.commit()

                        getLocation()
                        val intent = Intent(this@SplashScreen, LoginAndRegistration::class.java)
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(this, "Permission Denied. App Cannot function without location", Toast.LENGTH_SHORT).show()

                    val builder = AlertDialog.Builder(this,R.style.AlertDialogCustom)
                        .create()
                    val view = layoutInflater.inflate(R.layout.custom_error_dialogue,null)
                    val  button = view.findViewById<Button>(R.id.dialogDismiss_button)
                    val  message = view.findViewById<TextView>(R.id.contentText)
                    message.setText("Permission Denied. App Cannot function without location. Please allow the permission")
                    builder.setView(view)
                    button.setOnClickListener {
                        builder.dismiss()
                        recreate()
                    }
                    builder.setCanceledOnTouchOutside(false)
                    builder.show()
                }
                return
            }
        }
    }

    private fun getLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, object : CancellationToken() {
            override fun onCanceledRequested(p0: OnTokenCanceledListener) = CancellationTokenSource().token

            override fun isCancellationRequested() = false
        })
            .addOnSuccessListener { location: Location? ->
                if (location == null){
                }
                else {
                    val lat = location.latitude
                    val lon = location.longitude

                    Log.e("Lat: ", lat.toString())
                    Log.e("Long: ", lon.toString())

                    val gcd = Geocoder(this, Locale.getDefault())
                    var addresses: List<Address>? = null
                    try {
                        addresses = gcd.getFromLocation(lat, lon, 1)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                    if (addresses != null && addresses.size > 0) {


                        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,
                            Context.MODE_PRIVATE)
                        val editor: SharedPreferences.Editor =  sharedPreferences.edit()

                        val city: String = addresses[0].getLocality()
                        val country: String = addresses[0].countryName


                        editor.putString("latitude",lat.toString())
                        editor.putString("longitude", lon.toString())
                        editor.putString("city",city)
                        editor.putString("country", country)
                        if (addresses[0].thoroughfare != null){
                            val address: String = addresses[0].thoroughfare
                            editor.putString("address",address)

                            Log.e("Address: ", address)
                            Log.e("country: ", country)
                            Log.e("city: ", city)
                        }


                        editor.apply()
                        editor.commit()
                    }
                }
            }
    }
}