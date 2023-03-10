package com.example.artwatch.Views

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.artwatch.R


class Rights : AppCompatActivity() {
    lateinit var generalButton: CardView
    lateinit var countrySpinnerCard: CardView
    lateinit var generalText: TextView
    lateinit var countryText: TextView
    lateinit var countryCard: CardView
    lateinit var rightsTitle: TextView
    lateinit var rightsContent: TextView
    val uganda = "Uganda"
    val kenya = "Kenya"
    val tanzania = "Tanzania"
    val rwanda = "Rwanda"
    val burundi = "Burundi"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rights)


        generalButton = findViewById(R.id.generalButton)
        countrySpinnerCard = findViewById(R.id.countrySpinnerCard)
        generalText = findViewById(R.id.generalText)
        countryText = findViewById(R.id.countryText)
        countryCard = findViewById(R.id.countryCard)
        rightsTitle = findViewById(R.id.rightsTitle)
        rightsContent = findViewById(R.id.rightsContent)

        val spinner = findViewById<View>(R.id.countriesSpinner) as Spinner
        val adapter = ArrayAdapter(
            this@Rights,
            R.layout.rights_spinner_items,
            resources.getStringArray(R.array.countries)
        )
        adapter.setDropDownViewResource(R.layout.rights_spinner_items)
        spinner.adapter = adapter

        spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {

                when(position){
                    1 -> onCountrySelected(uganda)
                    2 -> onCountrySelected(kenya)
                    3 -> onCountrySelected(tanzania)
                    4 -> onCountrySelected(rwanda)
                    5 -> onCountrySelected(burundi)

                }

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        })
    }

    fun onGeneralButtonClick(view: View){
        countrySpinnerCard.visibility = View.GONE
        generalText.visibility = View.GONE
        countryCard.visibility = View.GONE
        generalButton.visibility = View.VISIBLE
        countryText.visibility = View.VISIBLE
    }

    fun onCountryButtonClick(view: View){
        countrySpinnerCard.visibility = View.VISIBLE
        generalText.visibility = View.VISIBLE
        countryCard.visibility = View.VISIBLE
        generalButton.visibility = View.GONE
        countryText.visibility = View.GONE
    }

    fun onCountrySelected(country: String){
        val text = getString(R.string.rightsInUganda, country)
        rightsTitle.text = text
        rightsContent.setText(R.string.rightsInUg)
    }

    fun onbackPressed(view: View){
        finish()
    }
}