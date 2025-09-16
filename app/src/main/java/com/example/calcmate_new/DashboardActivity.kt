package com.example.calcmate_new

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        sharedPreferences = getSharedPreferences("favorites_prefs", Context.MODE_PRIVATE)

        // Set up the main calculator buttons
        findViewById<ImageButton>(R.id.btnBasicCalc).setOnClickListener {
            startActivity(Intent(this, BasicCalActivity::class.java))
        }
        findViewById<ImageButton>(R.id.btnScientificCalc).setOnClickListener {
            startActivity(Intent(this, ScientificCalcActivity::class.java))
        }
        findViewById<ImageButton>(R.id.btnUnitConverter).setOnClickListener {
            startActivity(Intent(this, UnitConverterActivity::class.java))
        }
        findViewById<ImageButton>(R.id.btnFinancialTools).setOnClickListener {
            startActivity(Intent(this, FinancialToolsActivity::class.java))
        }
        findViewById<ImageButton>(R.id.btnHealthCalc).setOnClickListener {
            startActivity(Intent(this, HealthCalcActivity::class.java))
        }
        findViewById<ImageButton>(R.id.btnDateTimeTools).setOnClickListener {
            startActivity(Intent(this, DateTimeToolsActivity::class.java))
        }
        findViewById<ImageButton>(R.id.btnProgrammerCalc).setOnClickListener {
            startActivity(Intent(this, ProgrammerCalculatorActivity::class.java))
        }

        // Set up the favorite star icon listeners
        findViewById<ImageView>(R.id.starBasic).setOnClickListener {
            toggleFavorite("Basic Calculator", R.id.starBasic)
        }
        findViewById<ImageView>(R.id.starScientific).setOnClickListener {
            toggleFavorite("Scientific Calculator", R.id.starScientific)
        }
        findViewById<ImageView>(R.id.starUnitConverter).setOnClickListener {
            toggleFavorite("Unit Converter", R.id.starUnitConverter)
        }
        findViewById<ImageView>(R.id.starFinancial).setOnClickListener {
            toggleFavorite("Financial Tools", R.id.starFinancial)
        }
        findViewById<ImageView>(R.id.starHealth).setOnClickListener {
            toggleFavorite("Health Calculator", R.id.starHealth)
        }
        findViewById<ImageView>(R.id.starDateTime).setOnClickListener {
            toggleFavorite("Date and Time Tools", R.id.starDateTime)
        }
        findViewById<ImageView>(R.id.starProgrammer).setOnClickListener {
            toggleFavorite("Programmer Calculator", R.id.starProgrammer)
        }

        // Set up the footer logic
        setupFooter()

        // Update star icons based on current favorites
        updateStarIcons()
    }

    private fun toggleFavorite(calculatorName: String, starId: Int) {
        val editor = sharedPreferences.edit()
        val isFavorite = sharedPreferences.getBoolean(calculatorName, false)

        if (isFavorite) {
            editor.remove(calculatorName)
        } else {
            editor.putBoolean(calculatorName, true)
        }
        editor.apply()
        updateStarIcon(findViewById(starId), sharedPreferences.getBoolean(calculatorName, false))
    }

    private fun updateStarIcons() {
        updateStarIcon(findViewById(R.id.starBasic), sharedPreferences.getBoolean("Basic Calculator", false))
        updateStarIcon(findViewById(R.id.starScientific), sharedPreferences.getBoolean("Scientific Calculator", false))
        updateStarIcon(findViewById(R.id.starUnitConverter), sharedPreferences.getBoolean("Unit Converter", false))
        updateStarIcon(findViewById(R.id.starFinancial), sharedPreferences.getBoolean("Financial Tools", false))
        updateStarIcon(findViewById(R.id.starHealth), sharedPreferences.getBoolean("Health Calculator", false))
        updateStarIcon(findViewById(R.id.starDateTime), sharedPreferences.getBoolean("Date and Time Tools", false))
        updateStarIcon(findViewById(R.id.starProgrammer), sharedPreferences.getBoolean("Programmer Calculator", false))
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
