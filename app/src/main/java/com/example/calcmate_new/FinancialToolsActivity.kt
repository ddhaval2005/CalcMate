package com.example.calcmate_new

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class FinancialToolsActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_financial_tools)

        sharedPreferences = getSharedPreferences("favorites_prefs", Context.MODE_PRIVATE)

        // Set up click listeners for the main tool buttons.
        findViewById<ImageButton>(R.id.btnLoanCalculator).setOnClickListener {
            startActivity(Intent(this, LoanCalculatorActivity::class.java))
        }
        findViewById<ImageButton>(R.id.btnCompoundInterestCalculator).setOnClickListener {
            startActivity(Intent(this, CompoundInterestCalculatorActivity::class.java))
        }
        findViewById<ImageButton>(R.id.btnSimpleInterestCalculator).setOnClickListener {
            startActivity(Intent(this, SimpleInterestCalculatorActivity::class.java))
        }
        findViewById<ImageButton>(R.id.btnTipCalculator).setOnClickListener {
            startActivity(Intent(this, TipCalculatorActivity::class.java))
        }

        // Set up the favorite star icon listeners, just like in DashboardActivity.
        findViewById<ImageView>(R.id.starLoan).setOnClickListener {
            toggleFavorite("Loan Calculator", R.id.starLoan)
        }
        findViewById<ImageView>(R.id.starCompoundInterest).setOnClickListener {
            toggleFavorite("Compound Interest Calculator", R.id.starCompoundInterest)
        }
        findViewById<ImageView>(R.id.starSimpleInterest).setOnClickListener {
            toggleFavorite("Simple Interest Calculator", R.id.starSimpleInterest)
        }
        findViewById<ImageView>(R.id.starTip).setOnClickListener {
            toggleFavorite("Tip Calculator", R.id.starTip)
        }

        // Set up the footer logic.
        setupFooter()

        // Update star icons based on current favorites when the activity starts.
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
        updateStarIcon(findViewById(R.id.starLoan), sharedPreferences.getBoolean("Loan Calculator", false))
        updateStarIcon(findViewById(R.id.starCompoundInterest), sharedPreferences.getBoolean("Compound Interest Calculator", false))
        updateStarIcon(findViewById(R.id.starSimpleInterest), sharedPreferences.getBoolean("Simple Interest Calculator", false))
        updateStarIcon(findViewById(R.id.starTip), sharedPreferences.getBoolean("Tip Calculator", false))
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
