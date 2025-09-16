package com.example.calcmate_new

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast

class VolumeConverterActivity : AppCompatActivity() {

    // Declare UI components
    private lateinit var inputValue: EditText
    private lateinit var fromUnitSpinner: Spinner
    private lateinit var toUnitSpinner: Spinner
    private lateinit var convertButton: Button
    private lateinit var resultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the content view to the volume converter layout
        setContentView(R.layout.activity_volume_convert)

        // Initialize UI components with their IDs from the XML layout
        inputValue = findViewById(R.id.inputValue)
        fromUnitSpinner = findViewById(R.id.fromUnitSpinner)
        toUnitSpinner = findViewById(R.id.toUnitSpinner)
        convertButton = findViewById(R.id.convertButton)
        resultTextView = findViewById(R.id.resultTextView)

        // Define the volume units available for conversion
        val volumeUnits = arrayOf(
            "Liters", "Milliliters", "Gallons", "Cubic Meters",
            "Cubic Centimeters", "Cups", "Ounces"
        )

        // Create an ArrayAdapter to populate the spinners
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            volumeUnits
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
            val result = convertVolume(value, fromUnit, toUnit)

            // Display the result in the TextView, formatted to two decimal places
            resultTextView.text = String.format("%.2f", result)
        }
    }

    /**
     * Performs the volume conversion.
     * @param value The input volume value.
     * @param fromUnit The unit to convert from.
     * @param toUnit The unit to convert to.
     * @return The converted volume value.
     */
    private fun convertVolume(value: Double, fromUnit: String, toUnit: String): Double {
        // First, convert the input value to a standard base unit (Liters)
        val litersValue = when (fromUnit) {
            "Liters" -> value
            "Milliliters" -> value / 1000.0
            "Gallons" -> value * 3.78541
            "Cubic Meters" -> value * 1000.0
            "Cubic Centimeters" -> value / 1000.0
            "Cups" -> value * 0.236588
            "Ounces" -> value * 0.0295735
            else -> 0.0
        }

        // Then, convert the Liters value to the target unit
        return when (toUnit) {
            "Liters" -> litersValue
            "Milliliters" -> litersValue * 1000.0
            "Gallons" -> litersValue / 3.78541
            "Cubic Meters" -> litersValue / 1000.0
            "Cubic Centimeters" -> litersValue * 1000.0
            "Cups" -> litersValue / 0.236588
            "Ounces" -> litersValue / 0.0295735
            else -> 0.0
        }
    }
}
