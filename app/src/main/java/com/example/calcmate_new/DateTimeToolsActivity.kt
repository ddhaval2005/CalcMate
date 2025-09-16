package com.example.calcmate_new

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class DateTimeToolsActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date_time_tools)

        sharedPreferences = getSharedPreferences("favorites_prefs", Context.MODE_PRIVATE)

        // Initialize and set click listeners for the main tool buttons
        findViewById<ImageButton>(R.id.btnDateDifference).setOnClickListener {
            startActivity(Intent(this, DateDifferenceActivity::class.java))
        }

        findViewById<ImageButton>(R.id.btnTimeZoneConverter).setOnClickListener {
            startActivity(Intent(this, TimeZoneActivity::class.java))
        }

        findViewById<ImageButton>(R.id.btnTimestampConverter).setOnClickListener {
            startActivity(Intent(this, TimestampActivity::class.java))
        }

        // Set up favorite star icon listeners
        findViewById<ImageView>(R.id.starDateDifference).setOnClickListener {
            toggleFavorite("Date Difference", R.id.starDateDifference)
        }
        findViewById<ImageView>(R.id.starTimeZone).setOnClickListener {
            toggleFavorite("Time Zone Converter", R.id.starTimeZone)
        }
        findViewById<ImageView>(R.id.starTimeStamp).setOnClickListener {
            toggleFavorite("Timestamp Converter", R.id.starTimeStamp)
        }

        // Set up footer and initial star icons
        setupFooter()
        updateStarIcons()
    }

    private fun toggleFavorite(toolName: String, starId: Int) {
        val editor = sharedPreferences.edit()
        val isFavorite = sharedPreferences.getBoolean(toolName, false)

        if (isFavorite) {
            editor.remove(toolName)
        } else {
            editor.putBoolean(toolName, true)
        }
        editor.apply()
        updateStarIcon(findViewById(starId), sharedPreferences.getBoolean(toolName, false))
    }

    private fun updateStarIcons() {
        updateStarIcon(findViewById(R.id.starDateDifference), sharedPreferences.getBoolean("Date Difference", false))
        updateStarIcon(findViewById(R.id.starTimeZone), sharedPreferences.getBoolean("Time Zone Converter", false))
        updateStarIcon(findViewById(R.id.starTimeStamp), sharedPreferences.getBoolean("Timestamp Converter", false))
    }

    private fun updateStarIcon(starImageView: ImageView, isFavorite: Boolean) {
        if (isFavorite) {
            starImageView.setImageResource(R.drawable.ic_star_filled)
        } else {
            starImageView.setImageResource(R.drawable.ic_star_outline)
        }
    }

    private fun setupFooter() {
        val btnHome: ImageButton = findViewById(R.id.btnHome)
        val btnFavorites: ImageButton = findViewById(R.id.btnFavorites)
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
