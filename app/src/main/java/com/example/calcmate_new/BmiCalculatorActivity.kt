package com.example.calcmate_new

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import kotlin.math.pow

class BmiCalculatorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi_calculator)

        // Get references to views
        val etWeight: TextInputEditText = findViewById(R.id.etWeight)
        val etHeight: TextInputEditText = findViewById(R.id.etHeight)
        val btnCalculate: Button = findViewById(R.id.btnCalculate)
        val tvBmiResult: TextView = findViewById(R.id.tvBmiResult)
        val tvBmiStatus: TextView = findViewById(R.id.tvBmiStatus)

        // Set the click listener for the calculate button
        btnCalculate.setOnClickListener {
            val weightStr = etWeight.text.toString()
            val heightStr = etHeight.text.toString()

            if (weightStr.isEmpty() || heightStr.isEmpty()) {
                Toast.makeText(this, "Please enter both weight and height.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Convert input to numbers
            val weight = weightStr.toFloat()
            val height = heightStr.toFloat()

            // Calculate BMI: weight (kg) / height (m)^2
            val bmi = weight / height.pow(2)

            // Display the result with two decimal places
            tvBmiResult.text = String.format("BMI: %.2f", bmi)

            // Determine the BMI status and display it
            val status = when {
                bmi < 18.5 -> "Underweight"
                bmi >= 18.5 && bmi < 25 -> "Normal Weight"
                bmi >= 25 && bmi < 30 -> "Overweight"
                else -> "Obese"
            }
            tvBmiStatus.text = "Status: $status"
        }
    }
}