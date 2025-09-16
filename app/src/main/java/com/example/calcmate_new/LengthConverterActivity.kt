package com.example.calcmate_new

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class LengthConverterActivity : AppCompatActivity() {

    // Define the view components we'll use in the code
    private lateinit var inputValue: EditText
    private lateinit var fromUnitSpinner: Spinner
    private lateinit var toUnitSpinner: Spinner
    private lateinit var convertButton: Button
    private lateinit var resultTextView: TextView

    // Define the conversion factors for each unit, all relative to meters (m)
    private val conversionFactors = mapOf(
        "Meters (m)" to 1.0,
        "Kilometers (km)" to 1000.0,
        "Centimeters (cm)" to 0.01,
        "Millimeters (mm)" to 0.001,
        "Micrometers (µm)" to 0.000001,
        "Nanometers (nm)" to 1e-9,
        "Miles (mi)" to 1609.34,
        "Yards (yd)" to 0.9144,
        "Feet (ft)" to 0.3048,
        "Inches (in)" to 0.0254
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_length_converter)

        // Initialize the views by finding them by their ID
        inputValue = findViewById(R.id.inputValue)
        fromUnitSpinner = findViewById(R.id.fromUnitSpinner)
        toUnitSpinner = findViewById(R.id.toUnitSpinner)
        convertButton = findViewById(R.id.convertButton)
        resultTextView = findViewById(R.id.resultTextView)

        // Create an ArrayAdapter for the spinners using the keys from the conversionFactors map
        val units = conversionFactors.keys.toTypedArray()

        // CHANGE START: Use a custom layout for the spinner item
        // Make sure you have created the R.layout.spinner_item file as suggested previously
        val adapter = ArrayAdapter(this, R.layout.spinner_item, units)
        adapter.setDropDownViewResource(R.layout.spinner_item)
        // CHANGE END

        // Set the adapter for both "from" and "to" spinners
        fromUnitSpinner.adapter = adapter
        toUnitSpinner.adapter = adapter

        // Set an onClickListener for the convert button
        convertButton.setOnClickListener {
            convertLength()
        }
    }

    private fun convertLength() {
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
            // 1. Convert the input value to the base unit (meters)
            val valueInMeters = value * fromFactor
            // 2. Convert from meters to the target unit
            val result = valueInMeters / toFactor

            // Display the result with a toast and in the result TextView
            val resultText = String.format("%.4f %s", result, toUnit)
            resultTextView.text = resultText
            //Toast.makeText(this, "Conversion successful!", Toast.LENGTH_SHORT).show()

        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Invalid number format. Please enter a valid number.", Toast.LENGTH_SHORT).show()
        }
    }


    // This is the missing method you need to add to your class.
    // The 'View' parameter is required for the onClick attribute in XML.
    fun swapUnits(view: View) {
        val fromSelection = fromUnitSpinner.selectedItemPosition
        val toSelection = toUnitSpinner.selectedItemPosition

        fromUnitSpinner.setSelection(toSelection)
        toUnitSpinner.setSelection(fromSelection)
    }
}
