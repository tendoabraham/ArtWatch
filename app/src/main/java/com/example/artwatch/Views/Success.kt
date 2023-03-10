package com.example.artwatch.Views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.example.artwatch.R

class Success : AppCompatActivity() {
    private lateinit var messageTextView: TextView
    var loginStatus: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)


        val message = intent.getStringExtra("msg").toString()
        loginStatus = intent.getStringExtra("loginStatus").toString()

        Log.e("Message", message)
        messageTextView = findViewById(R.id.messageText)
        if (message.isNotEmpty()){
            messageTextView.setText(message)
        }else{
            messageTextView.setText("ERROR!!! Unclear response. Please try again later")
        }

    }
    fun onOkClick(view: View){
        if (loginStatus == "0"){
            val intent = Intent(this@Success, LoginAndRegistration::class.java)
            startActivity(intent)
        }else{
            val intent = Intent(this@Success, Home::class.java)
            startActivity(intent)
        }

    }
}