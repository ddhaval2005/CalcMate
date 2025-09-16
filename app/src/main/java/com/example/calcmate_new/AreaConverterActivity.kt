package com.example.calcmate_new

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast

class AreaConverterActivity : AppCompatActivity() {

    // Declare UI components
    private lateinit var inputValue: EditText
    private lateinit var fromUnitSpinner: Spinner
    private lateinit var toUnitSpinner: Spinner
    private lateinit var convertButton: Button
    private lateinit var resultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the content view to the area converter layout
        setContentView(R.layout.activity_area_convert)

        // Initialize UI components with their IDs from the XML layout
        inputValue = findViewById(R.id.inputValue)
        fromUnitSpinner = findViewById(R.id.fromUnitSpinner)
        toUnitSpinner = findViewById(R.id.toUnitSpinner)
        convertButton = findViewById(R.id.convertButton)
        resultTextView = findViewById(R.id.resultTextView)

        // Define the area units available for conversion
        val areaUnits = arrayOf(
            "Square Meters", "Square Kilometers", "Square Feet",
            "Square Yards", "Acres", "Hectares"
        )

        // Create an ArrayAdapter to populate the spinners
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            areaUnits
        )
        // Set the dropdown style
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Assign the adapter to both 'from' and 'to' spinners
        fromUnitSpinner.adapter = adapter
        toUnitSpinner.adapter = adapter

        // Set an OnClickListener for the convert button
        convertButton.setOnClickListener {
            // Check if the input value is empty
            if (inputValue.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter a value", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // Exit the function if input is empty
            }

            // Get the user's input value
            val value = inputValue.text.toString().toDouble()
            // Get the selected units
            val fromUnit = fromUnitSpinner.selectedItem.toString()
            val toUnit = toUnitSpinner.selectedItem.toString()

            // Perform the conversion based on the selected units
            val result = convertArea(value, fromUnit, toUnit)

            // Display the result in the TextView, formatted to two decimal places
            resultTextView.text = String.format("%.2f", result)
        }
    }

    /**
     * Performs the area conversion.
     * @param value The input area value.
     * @param fromUnit The unit to convert from.
     * @param toUnit The unit to convert to.
     * @return The converted area value.
     */
    private fun convertArea(value: Double, fromUnit: String, toUnit: String): Double {
        // First, convert the input value to a standard base unit (Square Meters)
        val squareMetersValue = when (fromUnit) {
            "Square Meters" -> value
            "Square Kilometers" -> value * 1_000_000.0
            "Square Feet" -> value * 0.092903
            "Square Yards" -> value * 0.836127
            "Acres" -> value * 4046.86
            "Hectares" -> value * 10_000.0
            else -> 0.0
        }

        // Then, convert the Square Meters value to the target unit
        return when (toUnit) {
            "Square Meters" -> squareMetersValue
            "Square Kilometers" -> squareMetersValue / 1_000_000.0
            "Square Feet" -> squareMetersValue / 0.092903
            "Square Yards" -> squareMetersValue / 0.836127
            "Acres" -> squareMetersValue / 4046.86
            "Hectares" -> squareMetersValue / 10_000.0
            else -> 0.0
        }
    }
}
