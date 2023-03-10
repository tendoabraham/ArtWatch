package com.example.artwatch.Views


import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.artwatch.Data.CustomProgressDialogue
import com.example.artwatch.Data.IncidentsModel
import com.example.artwatch.R
import com.example.artwatch.Viewmodels.IncidentsViewModel
import com.example.artwatch.Views.ui.Icommunicator
import com.example.artwatch.databinding.ActivityHomeBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpEntity
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpResponse
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.ClientProtocolException
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpGet
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.DefaultHttpClient
import kotlinx.coroutines.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


class Home : AppCompatActivity(), Icommunicator, CoroutineScope by MainScope(){

    private lateinit var binding: ActivityHomeBinding
    lateinit var viewModel: IncidentsViewModel
    private val progressDialog = CustomProgressDialogue()
    private val sharedPrefFile = "kotlinsharedpreference"
    var timer: Int = 0

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_home)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_info
            )
        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        initViewModel()
        getLocation()


        lifecycleScope.launch(Dispatchers.IO){
            //New Coroutine is created
            val executorService = Executors.newSingleThreadScheduledExecutor()
            executorService.scheduleAtFixedRate({ getLocation() }, 0, 5, TimeUnit.SECONDS)
        }

    }


    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(IncidentsViewModel::class.java)

        viewModel.getCreateNewUserObserver().observe(this, androidx.lifecycle.Observer {
            if (it != null){
                progressDialog.dialog.dismiss()

                val builder = AlertDialog.Builder(this,R.style.AlertDialogCustom)
                    .create()
                val view = layoutInflater.inflate(R.layout.custom_alert_dialog,null)
                val  button = view.findViewById<Button>(R.id.dialogDismiss_button)
                builder.setView(view)
                button.setOnClickListener {
                    builder.dismiss()
                    finish()
                    startActivity(getIntent())
                }
                builder.setCanceledOnTouchOutside(false)
                builder.show()
            }else{

            }
        })
    }

    fun onRights(view: View){
        when (view.id) {
            com.example.artwatch.R.id.rights -> startActivity(Intent(this, Rights::class.java))
            com.example.artwatch.R.id.members -> startActivity(Intent(this, Artists::class.java))
            com.example.artwatch.R.id.support -> startActivity(Intent(this, Organisations::class.java))
            com.example.artwatch.R.id.faqs -> startActivity(Intent(this, Help::class.java))
            com.example.artwatch.R.id.privacy -> {}
            com.example.artwatch.R.id.legal -> {}
            R.id.editBtn -> startActivity(Intent(this, Edit::class.java))

//            TODO:Remove PATCH
            R.id.sosImg -> {
                progressDialog.show(this, "Sending SOS...")
                Handler().postDelayed({
                    progressDialog.dialog.dismiss()
                    val intent = Intent(this@Home, Success::class.java)
                    intent.putExtra("msg", "Your emergency contacts and Our Support team have been notified. Help is on the way")
                    startActivity(intent)
                }, 2000)
            }
            R.id.shareFeedback -> {
                progressDialog.show(this, "Sending your Message...")
                Handler().postDelayed({
                    progressDialog.dialog.dismiss()
                    startActivity(Intent(this, Success::class.java).putExtra("msg",
                        "Your incident has been successfully shared with our Support team. We shall contact you shortly"))
                }, 2000)
            }
        }
    }

    override fun getMsg(risk: String, name: String, email: String,
                        phone: String,
                        otherContact: String,
                        bestReach: String,
                        desc: String,
                        location: String) {
        val incidentDetails  = IncidentsModel(risk, name, email, phone,otherContact,bestReach,desc,location)
        viewModel.createNewIncident(incidentDetails)
        progressDialog.show(this, "Submitting...")

    }

    private fun getLocation() {
        Log.e("We", "are here")
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this@Home,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            return
        }
        fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, object : CancellationToken() {
            override fun onCanceledRequested(p0: OnTokenCanceledListener) = CancellationTokenSource().token

            override fun isCancellationRequested() = false
        })
            .addOnSuccessListener { location: Location? ->
                if (location == null){
                    Log.e("Result", "Can't get location")
                }
                else {
                    val lat = location.latitude
                    val lon = location.longitude


                    Log.e("Lat: ", lat.toString())
                    Log.e("Long: ", lon.toString())
                    timer = timer + 1
                    Log.e("Timer", timer.toString());

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
                    }else{
                        Log.e("No", "Location")
                    }
                }
            }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                    if ((ContextCompat.checkSelfPermission(this@Home,
                            Manifest.permission.ACCESS_FINE_LOCATION) ===
                                PackageManager.PERMISSION_GRANTED)) {
                        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,
                            Context.MODE_PRIVATE)
                        val editor: SharedPreferences.Editor =  sharedPreferences.edit()

                        editor.putString("locationPermissionGranted", "1")
                        editor.apply()
                        editor.commit()

                        getLocation()
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
                        finish()
                    }
                    builder.setCanceledOnTouchOutside(false)
                    builder.show()
                }
                return
            }
        }
    }

}