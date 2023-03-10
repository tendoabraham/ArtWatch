package com.example.artwatch.Views.ui.feedback

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.artwatch.R
import com.example.artwatch.Views.ui.Icommunicator
import com.example.artwatch.databinding.FragmentIncidentBinding

class FeedbackFragment : Fragment() {

    private var _binding: FragmentIncidentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val feedbackViewModel =
            ViewModelProvider(this).get(FeedbackViewModel::class.java)

        _binding = FragmentIncidentBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val communicator: Icommunicator = activity as Icommunicator
//        val textView: TextView = binding.textDashboard
//        feedbackViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        // Inflate the layout for this fragment
        val feedbackView = inflater.inflate(R.layout.fragment_incident, container, false)
        val feedbackBtn: Button = feedbackView.findViewById(R.id.shareFeedback)
        val nameEditText: EditText = feedbackView.findViewById(R.id.name1)
        val emailEditText: EditText = feedbackView.findViewById(R.id.email1)
        val phoneEditText: EditText = feedbackView.findViewById(R.id.phone1)
        val locationEditText: EditText = feedbackView.findViewById(R.id.location1)
        val otherContactEditText: EditText = feedbackView.findViewById(R.id.otherContact1)
        val detailsEditText: EditText = feedbackView.findViewById(R.id.details1)
        val errorText: TextView = feedbackView.findViewById(R.id.errorWarning)

        val contactSpinner = feedbackView.findViewById<Spinner>(R.id.contactSpinner1)
        contactSpinner?.adapter = activity?.let {
            ArrayAdapter.createFromResource(it, R.array.bestContact, R.layout.home_spinner_items)
        } as SpinnerAdapter

        val categorySpinner = feedbackView.findViewById<Spinner>(R.id.accountsSpinner1)
        categorySpinner?.adapter = activity?.let {
            ArrayAdapter.createFromResource(it, R.array.categories, R.layout.home_spinner_items)
        } as SpinnerAdapter

        val riskSpinner = feedbackView.findViewById<Spinner>(R.id.riskSpinner1)
        riskSpinner?.adapter = activity?.let {
            ArrayAdapter.createFromResource(it, R.array.riskLevel, R.layout.home_spinner_items)
        } as SpinnerAdapter
//          TODO: Patch
//        feedbackBtn.setOnClickListener {
//
//            val name = nameEditText.text.toString()
//            val email = emailEditText.text.toString()
//            val phone = phoneEditText.text.toString()
//            val location = locationEditText.text.toString()
//            val otherContact = otherContactEditText.text.toString()
//            val details = detailsEditText.text.toString()
//            val risk = riskSpinner.selectedItem.toString()
//            val category = categorySpinner.selectedItem.toString()
//            val bestReach = contactSpinner.selectedItem.toString()
//
//            if (name.isNullOrBlank() || location.isNullOrBlank() ||
//                email.isNullOrBlank() || phone.isNullOrBlank() || otherContact.isNullOrBlank() ||
//                details.isNullOrBlank() || risk.isNullOrBlank() || bestReach.isNullOrBlank() ){
//
//                errorText.visibility = View.VISIBLE
//            }else{
//
//                communicator.getMsg(risk,name,email,phone,otherContact,bestReach,details,location)
//            }
//
//
//        }

        return feedbackView

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}