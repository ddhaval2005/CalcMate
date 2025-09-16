package com.example.calcmate_new

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class UnitConverterActivity : AppCompatActivity() {

    // Using the same SharedPreferences file as the Dashboard for consistency
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unit_converter)

        sharedPreferences = getSharedPreferences("favorites_prefs", Context.MODE_PRIVATE)

        // Set up the main converter buttons
        findViewById<ImageButton>(R.id.btnLengthConverter).setOnClickListener {
            startActivity(Intent(this, LengthConverterActivity::class.java))
        }
        findViewById<ImageButton>(R.id.btnWeightConverter).setOnClickListener {
            startActivity(Intent(this, WeightConverterActivity::class.java))
        }
        findViewById<ImageButton>(R.id.btnTemperatureConverter).setOnClickListener {
            startActivity(Intent(this, TemperatureConverterActivity::class.java))
        }
        findViewById<ImageButton>(R.id.btnVolumeConverter).setOnClickListener {
            startActivity(Intent(this, VolumeConverterActivity::class.java))
        }
        findViewById<ImageButton>(R.id.btnAreaConverter).setOnClickListener {
            startActivity(Intent(this, AreaConverterActivity::class.java))
        }
        findViewById<ImageButton>(R.id.btnDataTransferRateConverter).setOnClickListener {
            startActivity(Intent(this, DataTransferRateConverterActivity::class.java))
        }

        // Set up the favorite star icon listeners
        findViewById<ImageView>(R.id.starLength).setOnClickListener {
            toggleFavorite("Length Converter", R.id.starLength)
        }
        findViewById<ImageView>(R.id.starWeight).setOnClickListener {
            toggleFavorite("Weight Converter", R.id.starWeight)
        }
        findViewById<ImageView>(R.id.starTemperature).setOnClickListener {
            toggleFavorite("Temperature Converter", R.id.starTemperature)
        }
        findViewById<ImageView>(R.id.starVolume).setOnClickListener {
            toggleFavorite("Volume Converter", R.id.starVolume)
        }
        findViewById<ImageView>(R.id.starArea).setOnClickListener {
            toggleFavorite("Area Converter", R.id.starArea)
        }
        findViewById<ImageView>(R.id.starDataTransferRate).setOnClickListener {
            toggleFavorite("Data Transfer Rate Converter", R.id.starDataTransferRate)
        }

        // Set up the footer logic
        setupFooter()

        // Update star icons based on current favorites when the activity starts
        updateStarIcons()
    }

    /**
     * Toggles the favorite state for a given converter and saves it in SharedPreferences.
     * This logic is identical to the one in DashboardActivity.
     *
     * @param converterName The string key for the converter (e.g., "Length Converter").
     * @param starId The resource ID of the star icon ImageView.
     */
    private fun toggleFavorite(converterName: String, starId: Int) {
        val editor = sharedPreferences.edit()
        val isFavorite = sharedPreferences.getBoolean(converterName, false)

        if (isFavorite) {
            editor.remove(converterName)
        } else {
            editor.putBoolean(converterName, true)
        }
        editor.apply()
        updateStarIcon(findViewById(starId), sharedPreferences.getBoolean(converterName, false))
    }

    /**
     * Updates the star icon for a single converter based on its favorite state.
     *
     * @param starImageView The ImageView for the star icon.
     * @param isFavorite The favorite state (true if favorited, false otherwise).
     */
    private fun updateStarIcon(starImageView: ImageView, isFavorite: Boolean) {
        if (isFavorite) {
            starImageView.setImageResource(R.drawable.ic_star_filled)
        } else {
            starImageView.setImageResource(R.drawable.ic_star_outline)
        }
    }

    /**
     * Iterates through all star icons and sets their initial state based on SharedPreferences.
     * This function ensures the correct state is displayed when the activity is loaded.
     */
    private fun updateStarIcons() {
        updateStarIcon(findViewById(R.id.starLength), sharedPreferences.getBoolean("Length Converter", false))
        updateStarIcon(findViewById(R.id.starWeight), sharedPreferences.getBoolean("Weight Converter", false))
        updateStarIcon(findViewById(R.id.starTemperature), sharedPreferences.getBoolean("Temperature Converter", false))
        updateStarIcon(findViewById(R.id.starVolume), sharedPreferences.getBoolean("Volume Converter", false))
        updateStarIcon(findViewById(R.id.starArea), sharedPreferences.getBoolean("Area Converter", false))
        updateStarIcon(findViewById(R.id.starDataTransferRate), sharedPreferences.getBoolean("Data Transfer Rate Converter", false))
    }

    /**
     * This method handles the logic for the bottom navigation bar (footer).
     */
    private fun setupFooter() {
        val btnHome: ImageButton = findViewById(R.id.btnHome)
        val btnFavorites: ImageButton = findViewById(R.id.btnFavorites)
        val btnSettings: ImageButton = findViewById(R.id.btnSettings)

        // Here you would add the navigation logic for the footer buttons.
        btnHome.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }

        btnFavorites.setOnClickListener {
            startActivity(Intent(this, FavoritesActivity::class.java))
        }

        btnSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }
}
