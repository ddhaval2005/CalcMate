package com.example.calcmate_new

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.calcmate_new.R

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)



        // Set up click listeners for each settings option
        val optionAbout: LinearLayout = findViewById(R.id.optionAbout)
        optionAbout.setOnClickListener {
            val intent = Intent(this, AboutusActivity::class.java)
            startActivity(intent)
        }

        val optionFeedback: LinearLayout = findViewById(R.id.optionFeedback)
        optionFeedback.setOnClickListener {
            val intent = Intent(this, FeedbackActivity::class.java)
            startActivity(intent)
        }

        val optionContactUs: LinearLayout = findViewById(R.id.optionContactUs)
        optionContactUs.setOnClickListener {
            val intent = Intent(this, ContactActivity::class.java)
            startActivity(intent)
        }

        val optionPolicy: LinearLayout = findViewById(R.id.optionPolicy)
        optionPolicy.setOnClickListener {
            val intent = Intent(this, PolicyActivity::class.java)
            startActivity(intent)
        }

        val optionRateUs: LinearLayout = findViewById(R.id.optionRateUs)
        fun openPrivacyPolicy() {
            // You will need to host your privacy policy on a website and put the URL here.
            // For example: "https://www.your-app-website.com/privacy"
            val url = "https://www.google.com" // Replace with your actual privacy policy URL
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }





        // Set up the footer navigation

    }

    private fun setupFooter() {
        val btnHome: ImageButton = findViewById(R.id.btnHome)
        val btnFavorites: ImageButton = findViewById(R.id.btnFavorites) // Renamed from btnHistory for clarity
        val btnSettings: ImageButton = findViewById(R.id.btnSettings)

        // Set up listeners for the footer buttons
        btnHome.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }

        btnFavorites.setOnClickListener {
            val intent = Intent(this, FavoritesActivity::class.java)
            startActivity(intent)
        }

        btnSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }
}
