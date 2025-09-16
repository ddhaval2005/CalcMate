package com.example.calcmate_new

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.pow

class LoanCalculatorActivity : AppCompatActivity() {

    // Declare UI components
    private lateinit var etLoanAmount: EditText
    private lateinit var etInterestRate: EditText
    private lateinit var etLoanTerm: EditText
    private lateinit var btnCalculate: Button
    private lateinit var tvMonthlyPayment: TextView
    private lateinit var tvTotalInterest: TextView
    private lateinit var tvTotalPayment: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loan_calculator)

        // Initialize UI components by finding them from the XML layout
        etLoanAmount = findViewById(R.id.etLoanAmount)
        etInterestRate = findViewById(R.id.etInterestRate)
        etLoanTerm = findViewById(R.id.etLoanTerm)
        btnCalculate = findViewById(R.id.btnCalculate)
        tvMonthlyPayment = findViewById(R.id.tvMonthlyPayment)
        tvTotalInterest = findViewById(R.id.tvTotalInterest)
        tvTotalPayment = findViewById(R.id.tvTotalPayment)

        // Set up the click listener for the calculate button
        btnCalculate.setOnClickListener {
            calculateLoan()
        }
    }

    private fun calculateLoan() {
        // Get the input values from the EditText fields, safely handling potential nulls
        val loanAmount = etLoanAmount.text.toString().toDoubleOrNull()
        val interestRate = etInterestRate.text.toString().toDoubleOrNull()
        val loanTerm = etLoanTerm.text.toString().toDoubleOrNull()

        // Validate the input values
        if (loanAmount == null || interestRate == null || loanTerm == null ||
            loanAmount <= 0 || interestRate < 0 || loanTerm <= 0) {
            Toast.makeText(this, "Please enter valid, positive numbers.", Toast.LENGTH_SHORT).show()
            return
        }

        // Convert the annual interest rate to a monthly rate
        val monthlyInterestRate = interestRate / 100.0 / 12.0

        // Convert the loan term from years to months
        val numberOfMonths = loanTerm * 12.0

        // Calculate the monthly payment using the loan formula:
        // M = P [r(1+r)^n] / [(1+r)^n - 1]
        // where P = principal, r = monthly interest rate, n = number of months
        val monthlyPayment: Double
        if (monthlyInterestRate > 0) {
            monthlyPayment = loanAmount * (monthlyInterestRate * (1 + monthlyInterestRate).pow(numberOfMonths)) / ((1 + monthlyInterestRate).pow(numberOfMonths) - 1)
        } else {
            // Special case for 0% interest rate
            monthlyPayment = loanAmount / numberOfMonths
        }

        // Calculate the total payment and total interest paid
        val totalPayment = monthlyPayment * numberOfMonths
        val totalInterest = totalPayment - loanAmount

        // Display the results, formatted to two decimal places
        tvMonthlyPayment.text = "Monthly Payment: ₹${String.format("%.2f", monthlyPayment)}"
        tvTotalInterest.text = "Total Interest: ₹${String.format("%.2f", totalInterest)}"
        tvTotalPayment.text = "Total Payment: ₹${String.format("%.2f", totalPayment)}"
    }
}
