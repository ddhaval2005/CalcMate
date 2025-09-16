package com.example.calcmate_new

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast

class DataTransferRateConverterActivity : AppCompatActivity() {

    // Declare UI components
    private lateinit var inputValue: EditText
    private lateinit var fromUnitSpinner: Spinner
    private lateinit var toUnitSpinner: Spinner
    private lateinit var convertButton: Button
    private lateinit var resultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the content view to the data transfer converter layout
        setContentView(R.layout.activity_data_transfer_convert)

        // Initialize UI components with their IDs from the XML layout
        inputValue = findViewById(R.id.inputValue)
        fromUnitSpinner = findViewById(R.id.fromUnitSpinner)
        toUnitSpinner = findViewById(R.id.toUnitSpinner)
        convertButton = findViewById(R.id.convertButton)
        resultTextView = findViewById(R.id.resultTextView)

        // Define the data transfer units available for conversion
        val dataUnits = arrayOf(
            "Bits per second (bps)", "Kilobits per second (Kbps)", "Megabits per second (Mbps)",
            "Gigabits per second (Gbps)", "Kilobytes per second (KB/s)", "Megabytes per second (MB/s)",
            "Gigabytes per second (GB/s)"
        )

        // Create an ArrayAdapter to populate the spinners
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            dataUnits
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
            val result = convertDataRate(value, fromUnit, toUnit)

            // Display the result in the TextView, formatted to two decimal places
            resultTextView.text = String.format("%.2f", result)
        }
    }

    /**
     * Performs the data transfer rate conversion.
     * @param value The input data rate value.
     * @param fromUnit The unit to convert from.
     * @param toUnit The unit to convert to.
     * @return The converted data rate value.
     */
    private fun convertDataRate(value: Double, fromUnit: String, toUnit: String): Double {
        // First, convert the input value to a standard base unit (Bits per second)
        val bitsPerSecondValue = when (fromUnit) {
            "Bits per second (bps)" -> value
            "Kilobits per second (Kbps)" -> value * 1_000.0
            "Megabits per second (Mbps)" -> value * 1_000_000.0
            "Gigabits per second (Gbps)" -> value * 1_000_000_000.0
            "Kilobytes per second (KB/s)" -> value * 1024.0 * 8.0
            "Megabytes per second (MB/s)" -> value * 1024.0 * 1024.0 * 8.0
            "Gigabytes per second (GB/s)" -> value * 1024.0 * 1024.0 * 1024.0 * 8.0
            else -> 0.0
        }

        // Then, convert the Bits per second value to the target unit
        return when (toUnit) {
            "Bits per second (bps)" -> bitsPerSecondValue
            "Kilobits per second (Kbps)" -> bitsPerSecondValue / 1_000.0
            "Megabits per second (Mbps)" -> bitsPerSecondValue / 1_000_000.0
            "Gigabits per second (Gbps)" -> bitsPerSecondValue / 1_000_000_000.0
            "Kilobytes per second (KB/s)" -> bitsPerSecondValue / (1024.0 * 8.0)
            "Megabytes per second (MB/s)" -> bitsPerSecondValue / (1024.0 * 1024.0 * 8.0)
            "Gigabytes per second (GB/s)" -> bitsPerSecondValue / (1024.0 * 1024.0 * 1024.0 * 8.0)
            else -> 0.0
        }
    }
}
