package com.example.artwatch.Views

import android.Manifest
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
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
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.transition.Slide
import androidx.transition.TransitionManager
import com.example.artwatch.Data.ArtistsModel
import com.example.artwatch.Data.CustomProgressDialogue
import com.example.artwatch.Data.LawyersModel
import com.example.artwatch.Data.RequestModel
import com.example.artwatch.R
import com.example.artwatch.Viewmodels.CreateArtistViewModel
import com.example.artwatch.Viewmodels.LawyersViewModel
import com.example.artwatch.Views.ui.profile.ProfileViewModel
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*


class LoginAndRegistration : AppCompatActivity() {
    lateinit var welcomeText: TextView
    lateinit var firstName: EditText
    lateinit var middleName: EditText
    lateinit var lastName: EditText
    lateinit var emailAddress: EditText
    lateinit var address: EditText
    lateinit var phoneNumber: EditText
    lateinit var password: EditText
    lateinit var nextOfKin: EditText
    lateinit var nextOfKinPhone: EditText
    lateinit var confirmPassword: EditText
    lateinit var dateOfBirth: EditText
    lateinit var socialNo: EditText
    lateinit var description: EditText
    lateinit var firstName1: EditText
    lateinit var middleName1: EditText
    lateinit var lastName1: EditText
    lateinit var contact: EditText
    lateinit var companyName: EditText
    lateinit var countries: AutoCompleteTextView
    lateinit var countries1: AutoCompleteTextView
    lateinit var countries2: AutoCompleteTextView

    lateinit var forgotPassword: TextView
    lateinit var view1: View
    lateinit var view2: View
    lateinit var datePicker: LinearLayout
    lateinit var selectCategory: LinearLayout
    lateinit var signin: LinearLayout
    lateinit var signup: LinearLayout
    lateinit var lawyerSignUp: LinearLayout
    val myCalendar = Calendar.getInstance()

    private val pickImage = 100
    private var imageUri: Uri? = null
    lateinit var dPimageView: ImageView
    val REQUEST_IMAGE_CAPTURE = 1

    var StringImage: String = ""
    lateinit var spinner: Spinner

    lateinit var viewModel: CreateArtistViewModel
    lateinit var lawyersViewModel: LawyersViewModel
    private val progressDialog = CustomProgressDialogue()

    val mSlideRight = Slide()

    lateinit var profileViewModel: ProfileViewModel
    private val sharedPrefFile = "kotlinsharedpreference"

    var PermCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_and_registration)


        initiateViews()

        val date =
            OnDateSetListener { view, year, month, day ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, day)
                updateLabel()
            }
        datePicker.setOnClickListener(View.OnClickListener {
            DatePickerDialog(
                this@LoginAndRegistration,
                date,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH),
            ).show()
        })

        mSlideRight.slideEdge = Gravity.END

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line, COUNTRIES
        )
        val textView = findViewById<View>(R.id.countries) as AutoCompleteTextView
        val textView1 = findViewById<View>(R.id.countries1) as AutoCompleteTextView
        val textView2 = findViewById<View>(R.id.countries2) as AutoCompleteTextView
        textView.setAdapter(adapter)
        textView1.setAdapter(adapter)
        textView2.setAdapter(adapter)

        artistSpinner()
        initViewModel()
        initLawyersViewModel()
        initProfileViewModel()
    }

    fun initiateViews(){
        welcomeText = findViewById(R.id.welcomeText)
        confirmPassword = findViewById(R.id.confirmPassword)
        forgotPassword = findViewById(R.id.forgotPassword)
        view1 = findViewById(R.id.view1)
        view2 = findViewById(R.id.view2)
        dateOfBirth = findViewById(R.id.dateOfBirth)
        datePicker = findViewById(R.id.datePicker)
        selectCategory = findViewById(R.id.selectCategory)
        signin = findViewById(R.id.signin)
        signup = findViewById(R.id.signup)
        dPimageView = findViewById(R.id.dpImageView)
        lawyerSignUp = findViewById(R.id.lawyerSignUp)
        firstName = findViewById(R.id.firstName)
        middleName = findViewById(R.id.middleName)
        lastName = findViewById(R.id.lastName)
        emailAddress = findViewById(R.id.emailAddress)
        phoneNumber = findViewById(R.id.phoneNumber)
        firstName1 = findViewById(R.id.firstName1)
        middleName1 = findViewById(R.id.middleName1)
        lastName1 = findViewById(R.id.lastName1)
        contact = findViewById(R.id.contact)
        companyName = findViewById(R.id.companyName)
        address = findViewById(R.id.address)
        nextOfKin = findViewById(R.id.nextOfKin)
        nextOfKinPhone = findViewById(R.id.nextOfKinPhoneNumber)
        password = findViewById(R.id.password)
        socialNo = findViewById(R.id.socialSecNo)
        description = findViewById(R.id.descriptionOfWork)
        spinner = findViewById<View>(R.id.artistrySpinner) as Spinner
        countries = findViewById(R.id.countries)
        countries1 = findViewById(R.id.countries1)
        countries2 = findViewById(R.id.countries2)
    }

    private val COUNTRIES = arrayOf(
        "Albania",
        "Afghanistan",
        "Algeria",
        "American Samoa",
        "Andorra",
        "Angola",
        "Anguilla",
        "Antarctica",
        "Antigua and Barbuda",
        "Argentina",
        "Armenia",
        "Aruba",
        "Australia",
        "Austria",
        "Azerbaijan",
        "Bahrain",
        "Bangladesh",
        "Barbados",
        "Belarus",
        "Belgium",
        "Belize",
        "Benin",
        "Bermuda",
        "Bhutan",
        "Bolivia",
        "Bosnia and Herzegovina",
        "Botswana",
        "Bouvet Island",
        "Brazil",
        "British Indian Ocean Territory",
        "British Virgin Islands",
        "Brunei",
        "Bulgaria",
        "Burkina Faso",
        "Burundi",
        "Cambodia",
        "Cameroon",
        "Canada",
        "Cape Verde",
        "Cayman Islands",
        "Central African Republic",
        "Chad",
        "Chile",
        "China",
        "Christmas Island",
        "Cocos (Keeling) Islands",
        "Colombia",
        "Comoros",
        "Congo",
        "Cook Islands",
        "Costa Rica",
        "Cote d\'Ivoire",
        "Croatia",
        "Cuba",
        "Cyprus",
        "Czech Republic",
        "Democratic Republic of the Congo",
        "Denmark",
        "Djibouti",
        "Dominica",
        "Dominican Republic",
        "East Timor",
        "Ecuador",
        "Egypt",
        "El Salvador",
        "Equatorial Guinea",
        "Eritrea",
        "Estonia",
        "Ethiopia",
        "Faeroe Islands",
        "Falkland Islands",
        "Fiji",
        "Finland",
        "Former Yugoslav Republic of Macedonia",
        "France",
        "French Guiana",
        "French Polynesia",
        "French Southern Territories",
        "Gabon",
        "Georgia",
        "Germany",
        "Ghana",
        "Gibraltar",
        "Greece",
        "Greenland",
        "Grenada",
        "Guadeloupe",
        "Guam",
        "Guatemala",
        "Guinea",
        "Guinea-Bissau",
        "Guyana",
        "Haiti",
        "Heard Island and McDonald Islands",
        "Honduras",
        "Hong Kong",
        "Hungary",
        "Iceland",
        "India",
        "Indonesia",
        "Iran",
        "Iraq",
        "Ireland",
        "Israel",
        "Italy",
        "Jamaica",
        "Japan",
        "Jordan",
        "Kazakhstan",
        "Kenya",
        "Kiribati",
        "Kuwait",
        "Kyrgyzstan",
        "Laos",
        "Latvia",
        "Lebanon",
        "Lesotho",
        "Liberia",
        "Libya",
        "Liechtenstein",
        "Lithuania",
        "Luxembourg",
        "Macau",
        "Madagascar",
        "Malawi",
        "Malaysia",
        "Maldives",
        "Mali",
        "Malta",
        "Marshall Islands",
        "Martinique",
        "Mauritania",
        "Mauritius",
        "Mayotte",
        "Mexico",
        "Micronesia",
        "Moldova",
        "Monaco",
        "Mongolia",
        "Montenegro",
        "Montserrat",
        "Morocco",
        "Mozambique",
        "Myanmar",
        "Namibia",
        "Nauru",
        "Nepal",
        "Netherlands",
        "Netherlands Antilles",
        "New Caledonia",
        "New Zealand",
        "Nicaragua",
        "Niger",
        "Nigeria",
        "Niue",
        "Norfolk Island",
        "North Korea",
        "Northern Marianas",
        "Norway",
        "Oman",
        "Pakistan",
        "Palau",
        "Panama",
        "Papua New Guinea",
        "Paraguay",
        "Peru",
        "Philippines",
        "Pitcairn Islands",
        "Poland",
        "Portugal",
        "Puerto Rico",
        "Qatar",
        "Reunion",
        "Romania",
        "Russia",
        "Rwanda",
        "Sqo Tome and Principe",
        "Saint Helena",
        "Saint Kitts and Nevis",
        "Saint Lucia",
        "Saint Pierre and Miquelon",
        "Saint Vincent and the Grenadines",
        "Samoa",
        "San Marino",
        "Saudi Arabia",
        "Senegal",
        "Serbia",
        "Seychelles",
        "Sierra Leone",
        "Singapore",
        "Slovakia",
        "Slovenia",
        "Solomon Islands",
        "Somalia",
        "South Africa",
        "South Georgia and the South Sandwich Islands",
        "South Korea",
        "South Sudan",
        "Spain",
        "Sri Lanka",
        "Sudan",
        "Suriname",
        "Svalbard and Jan Mayen",
        "Swaziland",
        "Sweden",
        "Switzerland",
        "Syria",
        "Taiwan",
        "Tajikistan",
        "Tanzania",
        "Thailand",
        "The Bahamas",
        "The Gambia",
        "Togo",
        "Tokelau",
        "Tonga",
        "Trinidad and Tobago",
        "Tunisia",
        "Turkey",
        "Turkmenistan",
        "Turks and Caicos Islands",
        "Tuvalu",
        "Virgin Islands",
        "Uganda",
        "Ukraine",
        "United Arab Emirates",
        "United Kingdom",
        "United States",
        "United States Minor Outlying Islands",
        "Uruguay",
        "Uzbekistan",
        "Vanuatu",
        "Vatican City",
        "Venezuela",
        "Vietnam",
        "Wallis and Futuna",
        "Western Sahara",
        "Yemen",
        "Yugoslavia",
        "Zambia",
        "Zimbabwe"
    )

    fun artistSpinner(){
        val adapter = ArrayAdapter(
            this@LoginAndRegistration,
            R.layout.login_drop_downs,
            resources.getStringArray(R.array.categories)
        )
        adapter.setDropDownViewResource(R.layout.login_drop_downs)
        spinner.adapter = adapter

        spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {

                when(position){
                    1 -> onCategorySelected("Artist")
                    2 -> onCategorySelected("Lawyer")
//                    3 -> onCountrySelected(Manager)
//                    4 -> onCountrySelected(Manager)
                }

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        })
    }

    fun onSignUpPressed(view: View){
        TransitionManager.beginDelayedTransition(selectCategory, mSlideRight)
        selectCategory.visibility = View.VISIBLE
        forgotPassword.visibility = View.GONE
        view1.visibility = View.GONE
        view2.visibility = View.VISIBLE
        signin.visibility = View.GONE
        lawyerSignUp.visibility = View.GONE
        signup.visibility = View.GONE
    }

    fun onCategorySelected(category: String){
        if (category == "Artist"){
            forgotPassword.visibility = View.GONE
            view1.visibility = View.GONE
            selectCategory.visibility = View.GONE

            TransitionManager.beginDelayedTransition(signup, mSlideRight)
            signup.visibility = View.VISIBLE
        }else{
            forgotPassword.visibility = View.GONE
            view1.visibility = View.GONE
            selectCategory.visibility = View.GONE

            TransitionManager.beginDelayedTransition(lawyerSignUp, mSlideRight)
            lawyerSignUp.visibility = View.VISIBLE
        }
    }

    fun onSignInPressed(view: View){
        view2.visibility = View.GONE
        signup.visibility = View.GONE
        signin.visibility = View.VISIBLE
        forgotPassword.visibility = View.VISIBLE
        view1.visibility = View.VISIBLE
        selectCategory.visibility = View.GONE
        lawyerSignUp.visibility = View.GONE
    }

    fun onCancelBtn(view: View){
        signin.visibility = View.GONE
        TransitionManager.beginDelayedTransition(selectCategory, mSlideRight)
        selectCategory.visibility = View.VISIBLE
        lawyerSignUp.visibility = View.GONE
        signup.visibility = View.GONE
    }

    fun login(view: View){
//        TODO: PATCH
//        getArtistDetails("3")

        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,
            Context.MODE_PRIVATE)
        val name = sharedPreferences.getString("name","")

        if (name.isNullOrEmpty()){
            Toast.makeText(this@LoginAndRegistration, "Sorry, details not found. Please Register", Toast.LENGTH_SHORT).show()
        }else{
            val intent = Intent(this@LoginAndRegistration, Home::class.java)
            startActivity(intent)
        }

    }

    private fun updateLabel() {
        val myFormat = "MM/dd/yy"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        dateOfBirth.setText(dateFormat.format(myCalendar.time))
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
                this@LoginAndRegistration,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@LoginAndRegistration,
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
                this@LoginAndRegistration,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@LoginAndRegistration,
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

    override fun onBackPressed() {

    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(CreateArtistViewModel::class.java)

        viewModel.getCreateNewUserObserver().observe(this, androidx.lifecycle.Observer {
            if (it != null){
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                progressDialog.dialog.dismiss()

                val builder = AlertDialog.Builder(this,R.style.AlertDialogCustom)
                    .create()
                val view = layoutInflater.inflate(R.layout.custom_alert_dialog,null)
                val  button = view.findViewById<Button>(R.id.dialogDismiss_button)
                val  message = view.findViewById<TextView>(R.id.contentText)
                message.setText(R.string.successfulReg)
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

    private fun initLawyersViewModel() {
        lawyersViewModel = ViewModelProvider(this).get(LawyersViewModel::class.java)

        lawyersViewModel.getCreateNewLawyerObserver().observe(this, androidx.lifecycle.Observer {
            if (it != null){
                progressDialog.dialog.dismiss()

                val builder = AlertDialog.Builder(this,R.style.AlertDialogCustom)
                    .create()
                val view = layoutInflater.inflate(R.layout.custom_alert_dialog,null)
                val  button = view.findViewById<Button>(R.id.dialogDismiss_button)
                val  message = view.findViewById<TextView>(R.id.contentText)
                message.setText(R.string.successfulReg)
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

    private fun initProfileViewModel() {
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        profileViewModel.getCreateNewUserObserver().observe(this, androidx.lifecycle.Observer {
            if (it != null){
                progressDialog.dialog.dismiss()
                Log.e("ResponseMain", it.fullNames.toString())

                val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,
                    Context.MODE_PRIVATE)
                val editor: SharedPreferences.Editor =  sharedPreferences.edit()

                editor.putString("name",it.fullNames.toString())
                editor.putString("DOB",it.dateOfBirth.toString())
                editor.putString("countryOfOrigin",it.countryOfOrigin.toString())
                editor.putString("address",it.address.toString())
                editor.putString("countryOfResidence",it.countryOfResidence.toString())
                editor.putString("email",it.emailAddress.toString())
                editor.putString("socialID",it.socialId.toString())
                editor.putString("dp",it.passportPhotoPath.toString())
                editor.putString("NOK",it.nextOfKinNames.toString())
                editor.putString("NOKPhone",it.nextOfKinContact.toString())
                editor.putString("details",it.descriptionOfWork.toString())
                editor.putString("phone",it.phoneNumber.toString())
                editor.putString("ID",it.id.toString())
                editor.putString("loggedIN", "1")
                editor.apply()
                editor.commit()
                val intent = Intent(this@LoginAndRegistration, Home::class.java)
                startActivity(intent)
            }else{

            }
        })
    }


    fun createArtist(view: View){
        val firstName = firstName.text.toString()
        val middleName = middleName.text.toString()
        val lastName = lastName.text.toString()
        val email = emailAddress.text.toString()
        val phone = phoneNumber.text.toString()
        val address = address.text.toString()
        val NOK = nextOfKin.text.toString()
        val NOKPhone = nextOfKinPhone.text.toString()
        val countryOfOrigin = countries.text.toString()
        val countryOfResidence = countries2.text.toString()
        val socialNo = socialNo.text.toString()
//        val dateOfBirth = dateOfBirth.text.toString()
        // TODO convert date to required format
        val dateOfBirth = "2022-10-05T16:04:34.593Z"
        val desc = description.text.toString()
        val name = "$firstName $middleName $lastName"


        if (firstName.isNullOrBlank()){
            Toast.makeText(this@LoginAndRegistration, "Please fill in your first name", Toast.LENGTH_SHORT).show()
        }else if (lastName.isNullOrBlank()){
            Toast.makeText(this@LoginAndRegistration, "Please fill in your last name", Toast.LENGTH_SHORT).show()
        }else if (email.isNullOrBlank()){
            Toast.makeText(this@LoginAndRegistration, "Please fill in your email address", Toast.LENGTH_SHORT).show()
        }else if (phone.isNullOrBlank()){
            Toast.makeText(this@LoginAndRegistration, "Please fill in your phone number", Toast.LENGTH_SHORT).show()
        }else if (address.isNullOrBlank()){
            Toast.makeText(this@LoginAndRegistration, "Please fill in your address", Toast.LENGTH_SHORT).show()
        }else if (NOK.isNullOrBlank()){
            Toast.makeText(this@LoginAndRegistration, "Please fill in your next of Kin's name", Toast.LENGTH_SHORT).show()
        }else if (NOKPhone.isNullOrBlank()){
            Toast.makeText(this@LoginAndRegistration, "Please fill in your next of kin's phone number", Toast.LENGTH_SHORT).show()
        }else if (countryOfOrigin.isNullOrBlank()){
            Toast.makeText(this@LoginAndRegistration, "Please fill in your country of origin", Toast.LENGTH_SHORT).show()
        }else if (countryOfResidence.isNullOrBlank()){
            Toast.makeText(this@LoginAndRegistration, "Please fill in your country of residence", Toast.LENGTH_SHORT).show()
        }else if (socialNo.isNullOrBlank()){
            Toast.makeText(this@LoginAndRegistration, "Please fill in your social number", Toast.LENGTH_SHORT).show()
        }else if (dateOfBirth.isNullOrBlank()){
            Toast.makeText(this@LoginAndRegistration, "Please fill in your date of birth", Toast.LENGTH_SHORT).show()
        } else if (desc.isNullOrBlank()){
            Toast.makeText(this@LoginAndRegistration, "Please fill in your work description", Toast.LENGTH_SHORT).show()
        }else if (StringImage == ""){
            Toast.makeText(this@LoginAndRegistration, "Please add your profile picture", Toast.LENGTH_SHORT).show()
        }else{

//            TODO: Patch
//            val artistDetails  = ArtistsModel(name, dateOfBirth, countryOfOrigin, address,countryOfResidence,email,phone,socialNo,StringImage,desc,NOK,NOKPhone)
//            viewModel.createNewUser(artistDetails)
//            progressDialog.show(this, "Registering...")

            val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,
                Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor =  sharedPreferences.edit()

            editor.putString("name",name)
            editor.putString("desc",desc)
            editor.putString("DOB",dateOfBirth)
            editor.putString("countryOfOrigin",countryOfOrigin)
            editor.putString("address",address)
            editor.putString("countryOfResidence",countryOfResidence)
            editor.putString("email",email)
            editor.putString("socialID",socialNo)
            editor.putString("dp",StringImage)
            editor.putString("NOK",NOK)
            editor.putString("NOKPhone",NOKPhone)
            editor.putString("details",desc)
            editor.putString("phone",phone)
            editor.putString("ID","3")
            editor.putString("loggedIN", "1")
            editor.apply()
            editor.commit()
            progressDialog.show(this, "Registering...")
            Handler().postDelayed({
               progressDialog.dialog.dismiss()

                val intent = Intent(this@LoginAndRegistration, Success::class.java)
                intent.putExtra("msg", "Congratulations. You have been registered successfully")
                intent.putExtra("loginStatus", "0")
                startActivity(intent)
            }, 2000)
        }

    }

    fun createLawyer(view: View){
        val firstName = firstName1.text.toString()
        val middleName = middleName1.text.toString()
        val lastName = lastName1.text.toString()
        val contact = contact.text.toString()
        val companyName = companyName.text.toString()
        val country = countries1.text.toString()

        val name = "$firstName $middleName $lastName"


        if (firstName.isNullOrBlank() || lastName.isNullOrBlank() ||
            contact.isNullOrBlank() || companyName.isNullOrBlank() ||
            country.isNullOrBlank()){
            Toast.makeText(this@LoginAndRegistration, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }


        if (firstName.isNullOrBlank()){
            Toast.makeText(this@LoginAndRegistration, "Please fill in your first name", Toast.LENGTH_SHORT).show()
        }else if (lastName.isNullOrBlank()){
            Toast.makeText(this@LoginAndRegistration, "Please fill in your last name", Toast.LENGTH_SHORT).show()
        }else if (contact.isNullOrBlank()){
            Toast.makeText(this@LoginAndRegistration, "Please fill in your phone number", Toast.LENGTH_SHORT).show()
        }else if (companyName.isNullOrBlank()){
            Toast.makeText(this@LoginAndRegistration, "Please fill in your company name", Toast.LENGTH_SHORT).show()
        }else if (country.isNullOrBlank()){
            Toast.makeText(this@LoginAndRegistration, "Please fill in your country", Toast.LENGTH_SHORT).show()
        }else{

            val lawyersDetails  = LawyersModel(name, companyName, country, contact)
            lawyersViewModel.createNewLawyer(lawyersDetails)
            progressDialog.show(this, "Registering...")
        }

    }

    fun getArtistDetails(id: String){
        val artistID  = RequestModel(id)
        profileViewModel.getArtist(artistID)
        progressDialog.show(this, "Logging in...")
    }

}