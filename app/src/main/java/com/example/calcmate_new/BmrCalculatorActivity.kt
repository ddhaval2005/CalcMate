package com.example.calcmate_new

import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class BmrCalculatorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmr_calculator)

        // Get references to views
        val etWeight: TextInputEditText = findViewById(R.id.etWeight)
        val etHeight: TextInputEditText = findViewById(R.id.etHeight)
        val etAge: TextInputEditText = findViewById(R.id.etAge)
        val rgGender: RadioGroup = findViewById(R.id.rgGender)
        val btnCalculate: Button = findViewById(R.id.btnCalculate)
        val tvBmrResult: TextView = findViewById(R.id.tvBmrResult)

        // Set the click listener for the calculate button
        btnCalculate.setOnClickListener {
            val weightStr = etWeight.text.toString()
            val heightStr = etHeight.text.toString()
            val ageStr = etAge.text.toString()
            val selectedGenderId = rgGender.checkedRadioButtonId

            if (weightStr.isEmpty() || heightStr.isEmpty() || ageStr.isEmpty()) {
                Toast.makeText(this, "Please fill in all the details.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Convert input to numbers
            val weight = weightStr.toFloat()
            val height = heightStr.toFloat()
            val age = ageStr.toInt()

            // BMR calculation based on gender
            val bmr = if (selectedGenderId == R.id.rbMale) {
                // Mifflin-St Jeor formula for men
                (10 * weight) + (6.25 * height) - (5 * age) + 5
            } else {
                // Mifflin-St Jeor formula for women
                (10 * weight) + (6.25 * height) - (5 * age) - 161
            }

            // Display the result with two decimal places
            tvBmrResult.text = String.format("BMR: %.2f kcal/day", bmr)
        }
    }
}