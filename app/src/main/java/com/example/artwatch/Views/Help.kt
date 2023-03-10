package com.example.artwatch.Views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.artwatch.Adapters.HelpAdapter
import com.example.artwatch.Data.CustomProgressDialogue
import com.example.artwatch.R
import com.example.artwatch.Viewmodels.FAQsViewModel

class Help : AppCompatActivity() {
    lateinit var incidentElaborateCard: CardView
    lateinit var incidentCard1: CardView
    lateinit var incidentCard2: CardView
    lateinit var incidentCard3: CardView
    lateinit var resultsCard: CardView
    private val progressDialog = CustomProgressDialogue()
    lateinit var helpRecyclerAdapter: HelpAdapter
    lateinit var searchBar: CardView
    lateinit var helpTitle: TextView
    lateinit var pageTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

//        incidentElaborateCard = findViewById(R.id.incidentElaborateCard)
//        resultsCard = findViewById(R.id.resultsCard)
        searchBar = findViewById(R.id.search_bar)
        pageTitle = findViewById(R.id.Pagetitle)

        val Module = intent.getStringExtra("Module")

        if (Module.equals("Privacy")){
            pageTitle.setText(R.string.legalAids)
            searchBar.visibility = View.GONE
//            initPrivacyViewModel()
        }

        initGroupsRecycler()
//        initViewModel()
        progressDialog.show(this)
    }

    fun onForwardClick(view: View){
        incidentElaborateCard.visibility = View.VISIBLE
        incidentCard1.visibility = View.GONE
        incidentCard2.visibility = View.GONE
        incidentCard3.visibility = View.GONE
        resultsCard.visibility = View.GONE
    }

    private fun initGroupsRecycler() {
        val recycler =  findViewById<RecyclerView>(R.id.helpRecycler)
        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        helpRecyclerAdapter = HelpAdapter(this)
        recycler.adapter = helpRecyclerAdapter
    }

    /*
    private fun initViewModel() {
        val viewModel:FAQsViewModel = ViewModelProvider(this).get(FAQsViewModel::class.java)
        viewModel.getLiveDataObserver().observe(this, Observer {
            if(it != null) {
                helpRecyclerAdapter.setFAQsList(it)
                helpRecyclerAdapter.notifyDataSetChanged()
                progressDialog.dialog.dismiss()
            } else {
                Toast.makeText(this, "Error in getting list", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.makeAPICall()
    }

    private fun initPrivacyViewModel() {
        val viewModel:FAQsViewModel = ViewModelProvider(this).get(FAQsViewModel::class.java)
        viewModel.getLiveDataObserver().observe(this, Observer {
            if(it != null) {
                helpRecyclerAdapter.setFAQsList(it)
                helpRecyclerAdapter.notifyDataSetChanged()
                progressDialog.dialog.dismiss()
            } else {
                Toast.makeText(this, "Error in getting list", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.makePrivacyAPICall()
    }*/


    fun onbackPressed(view: View){
        finish()
    }
}