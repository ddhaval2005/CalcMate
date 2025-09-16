package com.example.calcmate_new

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class HealthCalcActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health_calculator)

        sharedPreferences = getSharedPreferences("favorites_prefs", Context.MODE_PRIVATE)

        // Set up click listeners for the main tool buttons.
        findViewById<ImageButton>(R.id.btnBmiCalc).setOnClickListener {
            startActivity(Intent(this, BmiCalculatorActivity::class.java))
        }

        findViewById<ImageButton>(R.id.btnBmrCalc).setOnClickListener {
            startActivity(Intent(this, BmrCalculatorActivity::class.java))
        }

        findViewById<ImageButton>(R.id.btnIdealWeight).setOnClickListener {
            startActivity(Intent(this, IdealWeightActivity::class.java))
        }

        findViewById<ImageButton>(R.id.btnCalorieIntake).setOnClickListener {
            startActivity(Intent(this, CalorieTrackerActivity::class.java))
        }

        // Set up the favorite star icon listeners, just like in the other activities.
        findViewById<ImageView>(R.id.starBmi).setOnClickListener {
            toggleFavorite("BMI Calculator", R.id.starBmi)
        }

        findViewById<ImageView>(R.id.starBmr).setOnClickListener {
            toggleFavorite("BMR Calculator", R.id.starBmr)
        }

        findViewById<ImageView>(R.id.starIdealWeight).setOnClickListener {
            toggleFavorite("Ideal Weight Calculator", R.id.starIdealWeight)
        }

        findViewById<ImageView>(R.id.starCalorieIntake).setOnClickListener {
            toggleFavorite("Calorie Intake Calculator", R.id.starCalorieIntake)
        }

        // Set up the footer logic.
        setupFooter()

        // Update star icons based on current favorites when the activity starts.
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
        updateStarIcon(findViewById(R.id.starBmi), sharedPreferences.getBoolean("BMI Calculator", false))
        updateStarIcon(findViewById(R.id.starBmr), sharedPreferences.getBoolean("BMR Calculator", false))
        updateStarIcon(findViewById(R.id.starIdealWeight), sharedPreferences.getBoolean("Ideal Weight Calculator", false))
        updateStarIcon(findViewById(R.id.starCalorieIntake), sharedPreferences.getBoolean("Calorie Intake Calculator", false))
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
