package com.example.artwatch.Views

import android.Manifest
import android.content.*
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.artwatch.Data.CustomProgressDialogue
import com.example.artwatch.Data.EditArtistModel
import com.example.artwatch.Data.IncidentsModel
import com.example.artwatch.R
import com.example.artwatch.Viewmodels.EditArtistViewModel
import com.example.artwatch.Viewmodels.IncidentsViewModel
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.util.*

class Edit : AppCompatActivity() {
    private val pickImage = 100
    private var imageUri: Uri? = null
    lateinit var dPimageView: ImageView
    val REQUEST_IMAGE_CAPTURE = 1
    var StringImage: String = ""

    private val sharedPrefFile = "kotlinsharedpreference"

    lateinit var viewModel: EditArtistViewModel
    private val progressDialog = CustomProgressDialogue()

    lateinit var countrySpinner2: Spinner
    lateinit var countrySpinner3: Spinner

    lateinit var emailAddress: EditText
    lateinit var lcn: EditText
    lateinit var phoneNo: EditText
    lateinit var nextOfKin: EditText
    lateinit var nextOfKinPhone: EditText
    lateinit var socialNo: EditText
    lateinit var description: EditText
    lateinit var editBTN: Button

    lateinit var newEmail: String
    lateinit var newSocialID : String
    lateinit var newCountryOfResidence: String
    lateinit var newPhoneNo: String
    lateinit var newAddress: String
    lateinit var newNOK: String
    lateinit var newNOKPhoneNo: String
    lateinit var newDesc: String
    lateinit var passportPhoto: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        dPimageView = findViewById(R.id.dpImageView)
        countrySpinner2 = findViewById<View>(R.id.countriesSpinner2) as Spinner
        countrySpinner3 = findViewById<View>(R.id.countriesSpinner3) as Spinner

        emailAddress = findViewById(R.id.emailAddress)
        lcn = findViewById(R.id.address)
        phoneNo = findViewById(R.id.phoneNumber)
        nextOfKin = findViewById(R.id.nextOfKin)
        nextOfKinPhone = findViewById(R.id.nextOfKinPhoneNumber)
        socialNo = findViewById(R.id.socialSecNo)
        description = findViewById(R.id.descriptionOfWork)
        editBTN = findViewById(R.id.submitEdit)


        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,
            Context.MODE_PRIVATE)
        val name = sharedPreferences.getString("name","")
        val email = sharedPreferences.getString("email","")
        val phoneNumber = sharedPreferences.getString("phone","")
        val artistAddress = sharedPreferences.getString("address","")
        val countryOfResidence = sharedPreferences.getString("countryOfResidence","")
        val countryOfOrigin = sharedPreferences.getString("countryOfOrigin","")
        val socialID = sharedPreferences.getString("socialID","")
        val desc = sharedPreferences.getString("details","")
        val NOK = sharedPreferences.getString("NOK","")
        val NOKPhone = sharedPreferences.getString("NOKPhone","")
        val DOB = sharedPreferences.getString("DOB","")
        val id = sharedPreferences.getString("ID","")
        val dp = sharedPreferences.getString("dp","")

        emailAddress.setText(email)
//        emailAddress.setTextColor(resources.getColor(R.color.textGrey2))
        lcn.setText(artistAddress)
        phoneNo.setText(phoneNumber)
        nextOfKin.setText(NOK)
        nextOfKinPhone.setText(NOKPhone)
        socialNo.setText(socialID)
        description.setText(desc)

        val adapter = ArrayAdapter(
            this@Edit,
            R.layout.edit_drop_downs,
            resources.getStringArray(R.array.countries_)
        )
        adapter.setDropDownViewResource(R.layout.edit_drop_downs)
        countrySpinner2.adapter = adapter
        countrySpinner3.adapter = adapter

        val spinnerPosition = adapter.getPosition(countryOfResidence)
        countrySpinner2.setSelection(spinnerPosition)

        initEditArtistViewModel()

        editBTN.setOnClickListener {
             newEmail = emailAddress.text.toString()
             newSocialID = socialNo.text.toString()
             newCountryOfResidence = countrySpinner2.selectedItem.toString()
             newPhoneNo = phoneNo.text.toString()
             newAddress = lcn.text.toString()
             newNOK = nextOfKin.text.toString()
             newNOKPhoneNo = nextOfKinPhone.text.toString()
             newDesc = description.text.toString()
             passportPhoto = if (StringImage == ""){
                dp.toString()
            }else{
                StringImage
            }


            if (newEmail.isNullOrBlank() || newSocialID.isNullOrBlank() || newPhoneNo.isNullOrBlank() ||
                newAddress.isNullOrBlank() || newNOK.isNullOrBlank() || newNOKPhoneNo.isNullOrBlank() ||
                newDesc.isNullOrBlank()){
                Toast.makeText(this@Edit, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }else{
//                TODO: PATCH
//                val artistNewDetails  = EditArtistModel(id.toString(),name,DOB,countryOfOrigin,newAddress,
//                    newCountryOfResidence,newEmail,newPhoneNo,newSocialID,passportPhoto,newDesc,newNOK,newNOKPhoneNo)
//                viewModel.createNewIncident(artistNewDetails)
//                progressDialog.show(this, "Submitting...")


                val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,
                    Context.MODE_PRIVATE)
                val editor: SharedPreferences.Editor =  sharedPreferences.edit()

                editor.putString("desc",newDesc)
                editor.putString("address",newAddress)
                editor.putString("countryOfResidence",newCountryOfResidence)
                editor.putString("email",newEmail)
                editor.putString("socialID",newSocialID)
                editor.putString("dp",StringImage)
                editor.putString("NOK",newNOK)
                editor.putString("NOKPhone",newNOKPhoneNo)
                editor.putString("dp",passportPhoto)
                editor.putString("phone",newPhoneNo)
                editor.putString("ID","3")
                editor.putString("loggedIN", "1")
                editor.apply()
                editor.commit()
                progressDialog.show(this, "Updating details...")
                Handler().postDelayed({
                    progressDialog.dialog.dismiss()

                    startActivity(Intent(this, Success::class.java).putExtra("msg",
                        "Your details have been updated successfully. Thank you for using ArtWatch"))
                }, 2000)
            }
        }
    }

    private fun initEditArtistViewModel() {
        viewModel = ViewModelProvider(this).get(EditArtistViewModel::class.java)

        viewModel.getCreateNewUserObserver().observe(this, androidx.lifecycle.Observer {
            if (it != null){
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                progressDialog.dialog.dismiss()

                val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,
                    Context.MODE_PRIVATE)
                val editor: SharedPreferences.Editor =  sharedPreferences.edit()
                editor.putString("address",newEmail)
                editor.putString("countryOfResidence",newCountryOfResidence)
                editor.putString("email",newEmail)
                editor.putString("socialID",newSocialID)
                editor.putString("dp",passportPhoto)
                editor.putString("NOK",newNOK)
                editor.putString("NOKPhone",newNOKPhoneNo)
                editor.putString("details",newDesc)
                editor.putString("phone",newPhoneNo)
                editor.apply()
                editor.commit()

                val builder = AlertDialog.Builder(this,R.style.AlertDialogCustom)
                    .create()
                val view = layoutInflater.inflate(R.layout.custom_alert_dialog,null)
                val  button = view.findViewById<Button>(R.id.dialogDismiss_button)
                builder.setView(view)
                button.setOnClickListener {
                    builder.dismiss()
                    val intent = Intent(this@Edit, Home::class.java)
                    startActivity(intent)
                }
                builder.setCanceledOnTouchOutside(false)
                builder.show()
            }else{

            }
        })
    }


    fun onDpAction(view: View){
        let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(R.string.dpActionTitle)
                .setItems(
                    R.array.dpActions,
                    DialogInterface.OnClickListener { dialog, which ->
                        when (which){
                            0 -> takePicture()
                            1 -> choosePhoto()
                            2 -> removePicture()
                        }
                    })
            val alertDialog = builder.create()
            alertDialog.show()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    fun takePicture(){
        if (ContextCompat.checkSelfPermission(
                this@Edit,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@Edit,
                arrayOf(Manifest.permission.CAMERA),
                pickImage
            )
        } else {
            val photo = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            Log.e("Photo", photo.toString());
            try {
                startActivityForResult(photo, REQUEST_IMAGE_CAPTURE)
            } catch (e: ActivityNotFoundException) {
                // display error state to the user
            }
        }
    }



    fun choosePhoto(){
        if (ContextCompat.checkSelfPermission(
                this@Edit,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@Edit,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                pickImage
            )
        } else {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            val imageStream: InputStream? = contentResolver.openInputStream(imageUri!!)
            val selectedImage = BitmapFactory.decodeStream(imageStream)
            val encodedImage: String = encodeImage(selectedImage)
            Log.e("ImageString", encodedImage)
            StringImage = encodedImage
            decodeImage(encodedImage)
//            dPimageView.setImageURI(imageUri)
        }else{
            if (data.toString() != "Intent {  }"){
                val imageBitmap = data?.extras?.get("data") as Bitmap
                Log.e("Bitmap", imageBitmap.toString())
                val ImageString: String = encodeImage(imageBitmap)

                StringImage = ImageString
                decodeImage(ImageString)
                Log.e("ImageString2", ImageString)
            }
        }
    }

    private fun encodeImage(bm: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b: ByteArray = baos.toByteArray()
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Base64.getEncoder().withoutPadding().encodeToString(b)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
//        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    private fun decodeImage(imageString: String){
//        val decodedString: ByteArray = Base64.decode(encodedImage, Base64.DEFAULT)
        val decodedString: ByteArray = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Base64.getDecoder().decode(imageString)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        dPimageView.setImageBitmap(decodedByte)
    }

    fun removePicture(){
        dPimageView.setImageResource(R.drawable.user3)
    }

    fun onbackPressed(view: View){
        finish()
    }
}