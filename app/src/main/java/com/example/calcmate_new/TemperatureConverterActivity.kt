package com.example.calcmate_new

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast

class TemperatureConverterActivity : AppCompatActivity() {

    // Declare UI components
    private lateinit var inputValue: EditText
    private lateinit var fromUnitSpinner: Spinner
    private lateinit var toUnitSpinner: Spinner
    private lateinit var convertButton: Button
    private lateinit var resultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the content view to the temperature converter layout
        setContentView(R.layout.activity_temperature_convert)

        // Initialize UI components with their IDs from the XML layout
        inputValue = findViewById(R.id.inputValue)
        fromUnitSpinner = findViewById(R.id.fromUnitSpinner)
        toUnitSpinner = findViewById(R.id.toUnitSpinner)
        convertButton = findViewById(R.id.convertButton)
        resultTextView = findViewById(R.id.resultTextView)

        // Define the temperature units available for conversion
        val temperatureUnits = arrayOf("Celsius", "Fahrenheit", "Kelvin")

        // Create an ArrayAdapter to populate the spinners
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            temperatureUnits
        )
        // Set the dropdown style
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Assign the adapter to both 'from' and 'to' spinners
        fromUnitSpinner.adapter = adapter
        toUnitSpinner.adapter = adapter

        // Set the default selection for the spinners (optional)
        fromUnitSpinner.setSelection(0) // Celsius
        toUnitSpinner.setSelection(1) // Fahrenheit

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
            val result = convertTemperature(value, fromUnit, toUnit)

            // Display the result in the TextView, formatted to two decimal places
            resultTextView.text = String.format("%.2f", result)
        }
    }

    /**
     * Performs the temperature conversion.
     * @param value The input temperature value.
     * @param fromUnit The unit to convert from.
     * @param toUnit The unit to convert to.
     * @return The converted temperature value.
     */
    private fun convertTemperature(value: Double, fromUnit: String, toUnit: String): Double {
        // First, convert the input value to a standard base unit (Celsius)
        val celsiusValue = when (fromUnit) {
            "Celsius" -> value
            "Fahrenheit" -> (value - 32) * 5.0 / 9.0
            "Kelvin" -> value - 273.15
            else -> 0.0 // Default case, though spinners prevent this
        }

        // Then, convert the Celsius value to the target unit
        return when (toUnit) {
            "Celsius" -> celsiusValue
            "Fahrenheit" -> (celsiusValue * 9.0 / 5.0) + 32
            "Kelvin" -> celsiusValue + 273.15
            else -> 0.0 // Default case, though spinners prevent this
        }
    }
}
