package com.example.artwatch.Views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.example.artwatch.Data.CustomProgressDialogue
import com.example.artwatch.Data.IncidentsModel
import com.example.artwatch.R
import com.example.artwatch.Viewmodels.IncidentsViewModel

class Incident : AppCompatActivity() {
    lateinit var name: EditText
    lateinit var email: EditText
    lateinit var phone: EditText
    lateinit var location: EditText
    lateinit var otherPhone: EditText
    lateinit var details: EditText

    lateinit var contactSpinner: Spinner
    lateinit var riskSpinner: Spinner
    lateinit var viewModel: IncidentsViewModel

    private val progressDialog = CustomProgressDialogue()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_incident)


        name = findViewById(R.id.name)
        email = findViewById(R.id.email)
        phone = findViewById(R.id.phone)
        location = findViewById(R.id.location)
        otherPhone = findViewById(R.id.otherContact)
        details = findViewById(R.id.details)
        contactSpinner = findViewById(R.id.contactSpinner)
        riskSpinner = findViewById(R.id.riskSpinner)

        categoriesSpinner()
        contactSpinner()
        riskSpinner()
        initViewModel()
    }

    fun categoriesSpinner(){
        val spinner = findViewById<View>(R.id.accountsSpinner) as Spinner
        val adapter = ArrayAdapter(
            this@Incident,
            R.layout.spinner_custome_items,
            resources.getStringArray(R.array.categories)
        )
        adapter.setDropDownViewResource(R.layout.spinner_custome_items)
        spinner.adapter = adapter
    }

    fun contactSpinner(){
        val spinner = findViewById<View>(R.id.contactSpinner) as Spinner
        val adapter = ArrayAdapter(
            this@Incident,
            R.layout.login_drop_downs,
            resources.getStringArray(R.array.bestContact)
        )
        adapter.setDropDownViewResource(R.layout.login_drop_downs)
        spinner.adapter = adapter
    }

    fun riskSpinner(){
        val spinner = findViewById<View>(R.id.riskSpinner) as Spinner
        val adapter = ArrayAdapter(
            this@Incident,
            R.layout.login_drop_downs,
            resources.getStringArray(R.array.riskLevel)
        )
        adapter.setDropDownViewResource(R.layout.login_drop_downs)
        spinner.adapter = adapter
    }

    fun onHomeClick(view: View){
        val intent = Intent(this@Incident, MainActivity::class.java)
        startActivity(intent)
    }

    fun onbackPressed(view: View){
        finish()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(IncidentsViewModel::class.java)

        viewModel.getCreateNewUserObserver().observe(this, androidx.lifecycle.Observer {
            if (it != null){
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                progressDialog.dialog.dismiss()
            }else{

            }
        })
    }

    fun createIncident(view: View){
        val name = name.text.toString()
        val email = email.text.toString()
        val phone = phone.text.toString()
        val location = location.text.toString()
        val otherPhone = otherPhone.text.toString()
        val details = details.text.toString()
        val bestContact = contactSpinner.selectedItem.toString()
        val risk = riskSpinner.selectedItem.toString()

        if (name.isNullOrBlank() || email.isNullOrBlank() || phone.isNullOrBlank() ||
            location.isNullOrBlank() || otherPhone.isNullOrBlank() || details.isNullOrBlank() ||
            bestContact.isNullOrBlank() || risk.isNullOrBlank()){
            Toast.makeText(this@Incident, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }else{

//            val incidentDetails  = IncidentsModel(risk, name, email, phone,otherPhone,bestContact,details,location)
//            viewModel.createNewIncident(incidentDetails)
            progressDialog.show(this)
        }

    }
}