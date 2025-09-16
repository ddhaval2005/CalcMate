package com.example.calcmate_new

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE_HISTORY = 1
    private val REQUEST_CODE_SETTINGS = 2

    // Define a constant for the SharedPreferences file name, consistent with SettingsActivity
    private val PREFS_NAME = "ThemePrefs"
    private val THEME_KEY = "theme_mode"

    override fun onCreate(savedInstanceState: Bundle?) {
        // Apply the saved theme preference before calling super.onCreate()
        applySavedTheme()

        super.onCreate(savedInstanceState)

        // This code makes the app draw behind the status and navigation bars.
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        window.navigationBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        setContentView(R.layout.activity_dashboard)

        // Set up the footer functionality
        setupFooter()

        // --- ADDED CODE FOR INTENTS ---

        // Find the buttons for the different calculators
        // The fix is here: we're now correctly casting them to ImageButton
        val financialToolsButton: ImageButton = findViewById(R.id.btnFinancialTools)
        val scientificCalcButton: ImageButton = findViewById(R.id.btnScientificCalc)
        val unitConverterButton: ImageButton = findViewById(R.id.btnUnitConverter)

        // Set up OnClickListeners to start the new activities using Intents
        financialToolsButton.setOnClickListener {
            val intent = Intent(this, FinancialToolsActivity::class.java)
            startActivity(intent)
        }

        scientificCalcButton.setOnClickListener {
            val intent = Intent(this, ScientificCalcActivity::class.java)
            startActivity(intent)
        }

        unitConverterButton.setOnClickListener {
            val intent = Intent(this, UnitConverterActivity::class.java)
            startActivity(intent)
        }


    }

    /**
     * Reads the saved theme preference and applies it to the application.
     * This must be called before super.onCreate().
     */


    private fun applySavedTheme() {
        val sharedPrefs: SharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val isNightMode = sharedPrefs.getBoolean(THEME_KEY, false)

        if (isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun setupFooter() {
        val btnHome: ImageButton = findViewById(R.id.btnHome)
        val btnHistory: ImageButton = findViewById(R.id.btnHistory)
        val btnSettings: ImageButton = findViewById(R.id.btnSettings)

        // Set up the listeners for the footer buttons
        btnHome.setOnClickListener {
            // No need to start a new activity or change colors, we are already on Home.
            updateFooterIcons("home")
        }

        btnHistory.setOnClickListener {
            // Update the icons and then navigate to the History screen
            updateFooterIcons("history")
            val intent = Intent(this@MainActivity, FavoritesActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_HISTORY)
        }

        btnSettings.setOnClickListener {
            // Update the icons and then navigate to the Settings screen
            updateFooterIcons("settings")
            val intent = Intent(this@MainActivity, SettingsActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_SETTINGS)
        }

        // Set initial state of footer icons (Home is selected)
        updateFooterIcons("home")
    }

    // This method is called when an activity started with startActivityForResult() finishes.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Check which activity is returning and update the footer icons.
        when (requestCode) {
            REQUEST_CODE_HISTORY -> updateFooterIcons("home")
            REQUEST_CODE_SETTINGS -> updateFooterIcons("home")
        }
    }

    // A helper method to handle the icon color changes
    private fun updateFooterIcons(activeTab: String) {
        val btnHome: ImageButton = findViewById(R.id.btnHome)
        val btnHistory: ImageButton = findViewById(R.id.btnHistory)
        val btnSettings: ImageButton = findViewById(R.id.btnSettings)

        // Get the colors from resources
        val selectedColor = ContextCompat.getColor(this, R.color.tab_selected_color)
        val unselectedColor = ContextCompat.getColor(this, R.color.tab_icon_color)

        // Reset all buttons to the unselected color
        btnHome.setColorFilter(unselectedColor)
        btnHistory.setColorFilter(unselectedColor)
        btnSettings.setColorFilter(unselectedColor)

        // Set the active button to the selected color
        when (activeTab) {
            "home" -> btnHome.setColorFilter(selectedColor)
            "history" -> btnHistory.setColorFilter(selectedColor)
            "settings" -> btnSettings.setColorFilter(selectedColor)
        }
    }
}
