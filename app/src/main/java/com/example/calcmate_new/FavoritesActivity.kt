package com.example.calcmate_new

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class FavoritesActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        sharedPreferences = getSharedPreferences("favorites_prefs", Context.MODE_PRIVATE)

        // Set up the footer navigation
        setupFooter()

        // Display the favorited calculators
        displayFavorites()
    }

    private fun displayFavorites() {
        val favoritesListLayout: LinearLayout = findViewById(R.id.favoritesListLayout)
        favoritesListLayout.removeAllViews()

        val allFavorites = sharedPreferences.all

        if (allFavorites.isEmpty()) {
            val noFavoritesText = TextView(this).apply {
                text = "You have no favorites yet!"
                textSize = 18f
                setTextColor(resources.getColor(R.color.Blue_berry, theme))
                textAlignment = View.TEXT_ALIGNMENT_CENTER
                setPadding(0, 100, 0, 0)
            }
            favoritesListLayout.addView(noFavoritesText)
        } else {
            for ((key, value) in allFavorites) {
                if (value == true) { // Check if the value is explicitly true
                    val favoriteItem = TextView(this).apply {
                        text = key
                        textSize = 20f
                        setTextColor(resources.getColor(R.color.Blue_berry, theme))
                        setPadding(16, 24, 16, 24)
                        setBackgroundResource(R.drawable.button_card_bg) // Use the same background as cards
                        elevation = 4f
                        val params = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        params.setMargins(0, 8, 0, 8)
                        layoutParams = params
                    }
                    favoriteItem.setOnClickListener {
                        // Launch the corresponding activity
                        when (key) {
                            "Basic Calculator" -> startActivity(Intent(this, BasicCalActivity::class.java))
                            "Scientific Calculator" -> startActivity(Intent(this, ScientificCalcActivity::class.java))
                            "Unit Converter" -> startActivity(Intent(this, UnitConverterActivity::class.java))
                            "Length Converter" -> startActivity(Intent(this, LengthConverterActivity::class.java))
                            "weight Converter" -> startActivity(Intent(this, WeightConverterActivity::class.java))
                            "Temperature Converter" -> startActivity(Intent(this, TemperatureConverterActivity::class.java))
                            "Volume Converter" -> startActivity(Intent(this, VolumeConverterActivity::class.java))
                            "Area Converter" -> startActivity(Intent(this, AreaConverterActivity::class.java))
                            "Data Transfer Rate Converter" -> startActivity(Intent(this, DataTransferRateConverterActivity::class.java))
                            "Financial Tools" -> startActivity(Intent(this, FinancialToolsActivity::class.java))
                            "Health Calculator" -> startActivity(Intent(this, HealthCalcActivity::class.java))
                            "Date and Time Tools" -> startActivity(Intent(this, DateTimeToolsActivity::class.java))
                            "Date Difference" -> startActivity(Intent(this, DateDifferenceActivity::class.java))
                            "Time Zone Converter" -> startActivity(Intent(this, TimeZoneActivity::class.java))
                            "Timestamp Converter" -> startActivity(Intent(this, TimestampActivity::class.java))
                            "Programmer Calculator" -> startActivity(Intent(this, ProgrammerCalculatorActivity::class.java))
                            "Loan Calculator" -> startActivity(Intent(this, LoanCalculatorActivity::class.java))
                            "Compound Interest Calculator" -> startActivity(Intent(this, CompoundInterestCalculatorActivity::class.java))
                            "Simple Interest Calculator" -> startActivity(Intent(this, SimpleInterestCalculatorActivity::class.java))
                            "Tip Calculator" -> startActivity(Intent(this, TipCalculatorActivity::class.java))
                            "BMI Calculator" -> startActivity(Intent(this, BmiCalculatorActivity::class.java))
                            "BMR Calculator" -> startActivity(Intent(this, BmrCalculatorActivity::class.java))
                            "Ideal Weight Calculator" -> startActivity(Intent(this, IdealWeightActivity::class.java))
                            "Calorie Intake Calculator" -> startActivity(Intent(this, CalorieTrackerActivity::class.java))

                        }
                    }
                    favoritesListLayout.addView(favoriteItem)
                }
            }
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
            // Already on Favorites, do nothing or re-display
            displayFavorites()
        }

        btnSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }
}
