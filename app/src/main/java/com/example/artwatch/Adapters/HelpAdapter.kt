package com.example.artwatch.Adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.artwatch.Data.FAQsModel
import com.example.artwatch.R

class HelpAdapter(val activity: Activity): RecyclerView.Adapter<HelpAdapter.ViewHolder>() {

    private var FAQsList: List<FAQsModel>? = null

    fun setFAQsList(countryList: List<FAQsModel>?) {
        this.FAQsList = countryList
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.help_results, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(FAQsList?.get(position)!!, activity)
    }

    override fun getItemCount(): Int {
        if(FAQsList == null)return 0
        else return FAQsList?.size!!
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val title: TextView? = view.findViewById(R.id.FAQTitle)
        private val content: TextView? = view.findViewById(R.id.FAQContent)

        fun bind(get: FAQsModel, activity: Activity) {
            title?.text = get.title
            content?.text = get.content
        }

    }
}