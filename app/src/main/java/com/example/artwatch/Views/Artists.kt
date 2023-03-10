package com.example.artwatch.Views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.artwatch.Adapters.ArtistsAdapter
import com.example.artwatch.Data.CustomProgressDialogue
import com.example.artwatch.R
import com.example.artwatch.Viewmodels.FAQsViewModel

class Artists : AppCompatActivity() {
    lateinit var artistsRecyclerAdapter: ArtistsAdapter
    private val progressDialog = CustomProgressDialogue()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artists)
        initArtistsRecycler()
        initViewModel()

        val spinner = findViewById<View>(R.id.countriesSpinner) as Spinner
        val adapter = ArrayAdapter(
            this@Artists,
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

//                when(position){
//                    1 -> onCountrySelected(uganda)
//                    2 -> onCountrySelected(kenya)
//                    3 -> onCountrySelected(tanzania)
//                    4 -> onCountrySelected(rwanda)
//                    5 -> onCountrySelected(burundi)
//
//                }

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        })

        progressDialog.show(this, "Fetching members...")
    }

    private fun initViewModel() {
        val viewModel:FAQsViewModel = ViewModelProvider(this).get(FAQsViewModel::class.java)
        viewModel.getLiveDataObserver().observe(this, Observer {
            if(it != null) {
                artistsRecyclerAdapter.setArtistsList(it)
                artistsRecyclerAdapter.notifyDataSetChanged()
                progressDialog.dialog.dismiss()
            } else {
                Toast.makeText(this, "Error in getting list", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.makeAPICall()
    }


    private fun initArtistsRecycler() {
        val recycler =  findViewById<RecyclerView>(R.id.artistsRecycler)
        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        artistsRecyclerAdapter = ArtistsAdapter(this)
        recycler.adapter = artistsRecyclerAdapter
    }

    fun onbackPressed(view: View){
        finish()
    }
}