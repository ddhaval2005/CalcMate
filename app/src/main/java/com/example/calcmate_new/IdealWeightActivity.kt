package com.example.calcmate_new

import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import kotlin.math.roundToInt

class IdealWeightActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ideal_weight)

        // Get references to views
        val etHeight: TextInputEditText = findViewById(R.id.etHeight)
        val rgGender: RadioGroup = findViewById(R.id.rgGender)
        val btnCalculate: Button = findViewById(R.id.btnCalculate)
        val tvIdealWeightResult: TextView = findViewById(R.id.tvIdealWeightResult)

        // Set the click listener for the calculate button
        btnCalculate.setOnClickListener {
            val heightStr = etHeight.text.toString()
            val selectedGenderId = rgGender.checkedRadioButtonId

            if (heightStr.isEmpty()) {
                Toast.makeText(this, "Please enter your height.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Convert height from cm to inches (1 inch = 2.54 cm)
            val heightInCm = heightStr.toFloat()
            val heightInInches = heightInCm / 2.54f

            val idealWeight: Float

            // Calculate ideal weight based on gender
            if (selectedGenderId == R.id.rbMale) {
                // Formula for men: 50 kg + 2.3 kg for each inch over 5 feet (60 inches)
                if (heightInInches <= 60) {
                    idealWeight = 50f
                } else {
                    idealWeight = 50f + (heightInInches - 60) * 2.3f
                }
            } else {
                // Formula for women: 45.5 kg + 2.3 kg for each inch over 5 feet (60 inches)
                if (heightInInches <= 60) {
                    idealWeight = 45.5f
                } else {
                    idealWeight = 45.5f + (heightInInches - 60) * 2.3f
                }
            }

            // Display the result with one decimal place
            tvIdealWeightResult.text = String.format("Ideal Weight: %.1f kg", idealWeight)
        }
    }
}