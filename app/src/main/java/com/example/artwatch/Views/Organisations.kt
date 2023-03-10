package com.example.artwatch.Views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.artwatch.Adapters.GroupsAdapter
import com.example.artwatch.R

class Organisations : AppCompatActivity() {
    lateinit var groupRecyclerAdapter: GroupsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_organisations)
        initGroupsRecycler()

        val spinner = findViewById<View>(R.id.countriesSpinner) as Spinner
        val adapter = ArrayAdapter(
            this@Organisations,
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
    }

    private fun initGroupsRecycler() {
        val recycler =  findViewById<RecyclerView>(R.id.groupsRecycler)
        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        groupRecyclerAdapter = GroupsAdapter(this)
        recycler.adapter = groupRecyclerAdapter
    }

    fun onbackPressed(view: View){
        finish()
    }
}