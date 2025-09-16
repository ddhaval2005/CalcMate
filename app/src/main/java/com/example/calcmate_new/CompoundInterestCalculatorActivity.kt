package com.example.calcmate_new

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import kotlin.math.pow

class CompoundInterestCalculatorActivity : AppCompatActivity() {

    // Declare UI elements
    private lateinit var etPrincipal: TextInputEditText
    private lateinit var etRate: TextInputEditText
    private lateinit var etCompoundingFrequency: TextInputEditText
    private lateinit var etYears: TextInputEditText
    private lateinit var btnCalculate: Button
    private lateinit var tvTotalAmount: TextView
    private lateinit var tvTotalInterest: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compound_interest)

        // Initialize UI elements by finding their IDs
        etPrincipal = findViewById(R.id.etPrincipal)
        etRate = findViewById(R.id.etRate)
        etCompoundingFrequency = findViewById(R.id.etCompoundingFrequency)
        etYears = findViewById(R.id.etYears)
        btnCalculate = findViewById(R.id.btnCalculate)
        tvTotalAmount = findViewById(R.id.tvTotalAmount)
        tvTotalInterest = findViewById(R.id.tvTotalInterest)

        // Set a click listener for the calculate button
        btnCalculate.setOnClickListener {
            calculateCompoundInterest()
        }
    }

    private fun calculateCompoundInterest() {
        // Get user input and convert to Double or Int
        val principalStr = etPrincipal.text.toString()
        val rateStr = etRate.text.toString()
        val compoundingStr = etCompoundingFrequency.text.toString()
        val yearsStr = etYears.text.toString()

        // Validate input fields are not empty
        if (principalStr.isEmpty() || rateStr.isEmpty() || compoundingStr.isEmpty() || yearsStr.isEmpty()) {
            tvTotalAmount.text = "Please fill all fields"
            tvTotalInterest.text = "" // Clear the total interest text
            return
        }

        try {
            // Convert strings to numbers
            val principal = principalStr.toDouble()
            val rate = rateStr.toDouble() / 100.0 // Convert percentage to a decimal
            val compoundingFrequency = compoundingStr.toInt()
            val years = yearsStr.toInt()

            // Calculate the total amount using the compound interest formula
            // A = P(1 + r/n)^(nt)
            val totalAmount = principal * (1 + rate / compoundingFrequency).pow(compoundingFrequency * years)

            // Calculate the total interest earned
            val totalInterest = totalAmount - principal

            // Format the results to two decimal places
            val formattedTotalAmount = String.format("₹%.2f", totalAmount)
            val formattedTotalInterest = String.format("₹%.2f", totalInterest)

            // Update the TextViews with the formatted results
            tvTotalAmount.text = "Total Amount: $formattedTotalAmount"
            tvTotalInterest.text = "Total Interest: $formattedTotalInterest"

        } catch (e: NumberFormatException) {
            // Handle cases where the input is not a valid number
            tvTotalAmount.text = "Invalid number entered"
            tvTotalInterest.text = "" // Clear the total interest text
        }
    }
}
