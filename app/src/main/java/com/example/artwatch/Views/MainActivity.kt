package com.example.artwatch.Views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.artwatch.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onRightsClick(view: View){
        val intent = Intent(this@MainActivity, Rights::class.java)
        startActivity(intent)
    }

    fun onReportClick(view: View){
        val intent = Intent(this@MainActivity, Incident::class.java)
        startActivity(intent)
    }

    fun onArtistsClick(view: View){
        val intent = Intent(this@MainActivity, Artists::class.java)
        startActivity(intent)
    }

    fun onOrganisationClick(view: View){
        val intent = Intent(this@MainActivity, Organisations::class.java)
        startActivity(intent)
    }

    fun onHelpClick(view: View){
        val intent = Intent(this@MainActivity, Help::class.java)
        startActivity(intent)
    }

    fun onPrivacyClick(view: View){
        val intent = Intent(this@MainActivity, Help::class.java)
        intent.putExtra("Module", "Privacy")
        startActivity(intent)
    }

    fun onLogoutClick(view: View){
        val intent = Intent(this@MainActivity, LoginAndRegistration::class.java)
        intent.putExtra("Type", "Login")
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
    }
}