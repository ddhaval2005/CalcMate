package com.example.calcmate_new

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * An activity that provides a programmer's calculator.
 *
 * This calculator handles conversions between Decimal, Binary, Octal, and Hexadecimal
 * number systems in real-time on a single page.
 */
class ProgrammerCalculatorActivity : AppCompatActivity() {

    // UI components
    private lateinit var inputEditText: EditText
    private lateinit var decimalTextView: TextView
    private lateinit var binaryTextView: TextView
    private lateinit var octalTextView: TextView
    private lateinit var hexTextView: TextView
    private lateinit var clearButton: Button

    private var currentBase = 10 // Default base is Decimal (Base-10)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_programmer_calculator)

        // Initialize UI components
        inputEditText = findViewById(R.id.inputEditText)
        decimalTextView = findViewById(R.id.decimalValue)
        binaryTextView = findViewById(R.id.binaryValue)
        octalTextView = findViewById(R.id.octalValue)
        hexTextView = findViewById(R.id.hexValue)
        clearButton = findViewById(R.id.clearButton)

        // Set up the input text change listener
        inputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                // Perform conversion whenever the text changes
                convertNumber(s.toString())
            }
        })

        // Set up the clear button click listener
        clearButton.setOnClickListener {
            inputEditText.setText("")
        }

        // Set up listeners for base-selection buttons (not included in this code for simplicity)
        // You would typically have buttons for Dec, Bin, Oct, Hex that set the 'currentBase' variable.
        // For example:
        // findViewById<Button>(R.id.btnHex).setOnClickListener {
        //     currentBase = 16
        //     // Update keyboard layout or input filter here
        // }
    }

    /**
     * Converts a number from the current base to all other bases.
     *
     * @param input The string representation of the number to convert.
     */
    private fun convertNumber(input: String) {
        if (input.isBlank()) {
            // Clear all output views if the input is empty
            decimalTextView.text = ""
            binaryTextView.text = ""
            octalTextView.text = ""
            hexTextView.text = ""
            return
        }

        try {
            // Convert the input string to a Long
            val number = input.toLong(currentBase)

            // Update the TextViews with the converted values
            decimalTextView.text = "Decimal: $number"
            binaryTextView.text = "Binary: ${java.lang.Long.toBinaryString(number)}"
            octalTextView.text = "Octal: ${java.lang.Long.toOctalString(number)}"
            hexTextView.text = "Hex: ${java.lang.Long.toHexString(number).uppercase()}"

        } catch (e: NumberFormatException) {
            // Handle invalid input (e.g., non-numeric characters for the selected base)
            val errorMessage = "Invalid input for base $currentBase"
            decimalTextView.text = errorMessage
            binaryTextView.text = ""
            octalTextView.text = ""
            hexTextView.text = ""
        }
    }
}
