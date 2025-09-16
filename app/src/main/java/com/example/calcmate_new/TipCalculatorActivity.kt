package com.example.calcmate_new

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class TipCalculatorActivity : AppCompatActivity() {

    // Declare UI components
    private lateinit var etBillAmount: TextInputEditText
    private lateinit var etTipPercent: TextInputEditText
    private lateinit var etNumOfPeople: TextInputEditText
    private lateinit var btnCalculate: Button
    private lateinit var tvTipAmount: TextView
    private lateinit var tvTotalBill: TextView
    private lateinit var tvTotalPerPerson: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tip_calculator)

        // Initialize UI components
        etBillAmount = findViewById(R.id.etBillAmount)
        etTipPercent = findViewById(R.id.etTipPercent)
        etNumOfPeople = findViewById(R.id.etNumOfPeople)
        btnCalculate = findViewById(R.id.btnCalculate)
        tvTipAmount = findViewById(R.id.tvTipAmount)
        tvTotalBill = findViewById(R.id.tvTotalBill)
        tvTotalPerPerson = findViewById(R.id.tvTotalPerPerson)

        // Set up the click listener for the calculate button
        btnCalculate.setOnClickListener {
            calculateTip()
        }
    }

    private fun calculateTip() {
        // Get the input values from the EditText fields, safely handling potential nulls
        val billAmount = etBillAmount.text.toString().toDoubleOrNull()
        val tipPercent = etTipPercent.text.toString().toDoubleOrNull()
        val numOfPeople = etNumOfPeople.text.toString().toIntOrNull()

        // Validate that all inputs are valid numbers
        if (billAmount == null || tipPercent == null || numOfPeople == null ||
            billAmount <= 0 || tipPercent < 0 || numOfPeople <= 0) {
            Toast.makeText(this, "Please enter valid, positive numbers.", Toast.LENGTH_SHORT).show()
            return
        }

        // Calculate the tip amount
        val tipAmount = billAmount * (tipPercent / 100)

        // Calculate the total bill including the tip
        val totalBill = billAmount + tipAmount

        // Calculate the total per person
        val totalPerPerson = totalBill / numOfPeople

        // Display the results, formatted to two decimal places and with the Rupee symbol
        tvTipAmount.text = "Tip Amount: ₹${String.format("%.2f", tipAmount)}"
        tvTotalBill.text = "Total Bill: ₹${String.format("%.2f", totalBill)}"
        tvTotalPerPerson.text = "Total per Person: ₹${String.format("%.2f", totalPerPerson)}"
    }
}
