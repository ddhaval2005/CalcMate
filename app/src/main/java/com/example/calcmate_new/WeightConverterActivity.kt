package com.example.calcmate_new

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class WeightConverterActivity : AppCompatActivity() {

    // Define the view components we'll use in the code
    private lateinit var inputValue: EditText
    private lateinit var fromUnitSpinner: Spinner
    private lateinit var toUnitSpinner: Spinner
    private lateinit var convertButton: Button
    private lateinit var resultTextView: TextView

    // Define the conversion factors for each unit, all relative to kilograms (kg)
    private val conversionFactors = mapOf(
        "Kilograms (kg)" to 1.0,
        "Grams (g)" to 0.001,
        "Milligrams (mg)" to 1e-6,
        "Micrograms (µg)" to 1e-9,
        "Tons (t)" to 1000.0,
        "Pounds (lb)" to 0.453592,
        "Ounces (oz)" to 0.0283495,
        "Stones (st)" to 6.35029
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weight_converter)

        // Initialize the views by finding them by their ID
        inputValue = findViewById(R.id.inputValue)
        fromUnitSpinner = findViewById(R.id.fromUnitSpinner)
        toUnitSpinner = findViewById(R.id.toUnitSpinner)
        convertButton = findViewById(R.id.convertButton)
        resultTextView = findViewById(R.id.resultTextView)

        // Create an ArrayAdapter for the spinners using the keys from the conversionFactors map
        val units = conversionFactors.keys.toTypedArray()

        // Create an ArrayAdapter using the string array and a default spinner layout
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, units)

        // Specify the dropdown layout style
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Set the adapter for both "from" and "to" spinners
        fromUnitSpinner.adapter = adapter
        toUnitSpinner.adapter = adapter

        // Set an onClickListener for the convert button
        convertButton.setOnClickListener {
            convertWeight()
        }
    }

    private fun convertWeight() {
        // Get the value entered by the user
        val valueStr = inputValue.text.toString()
        if (valueStr.isEmpty()) {
            Toast.makeText(this, "Please enter a value to convert.", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val value = valueStr.toDouble()

            // Get the selected units from the spinners
            val fromUnit = fromUnitSpinner.selectedItem.toString()
            val toUnit = toUnitSpinner.selectedItem.toString()

            // Get the conversion factors from the map
            val fromFactor = conversionFactors[fromUnit] ?: 0.0
            val toFactor = conversionFactors[toUnit] ?: 0.0

            // Perform the conversion:
            // 1. Convert the input value to the base unit (kilograms)
            val valueInKilograms = value * fromFactor
            // 2. Convert from kilograms to the target unit
            val result = valueInKilograms / toFactor

            // Display the result with a toast and in the result TextView
            val resultText = String.format("%.4f %s", result, toUnit)
            resultTextView.text = resultText
            Toast.makeText(this, "Conversion successful!", Toast.LENGTH_SHORT).show()

        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Invalid number format. Please enter a valid number.", Toast.LENGTH_SHORT).show()
        }
    }
}
