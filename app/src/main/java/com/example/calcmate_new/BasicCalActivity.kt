
package com.example.calcmate_new

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BasicCalActivity : AppCompatActivity() {

    private lateinit var displayInput: TextView
    private lateinit var displayResult: TextView

    private var currentInput = StringBuilder("0")
    private var lastOperation = ""
    private var lastNumber = ""
    private var isNewOperation = true

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic_cal)

        displayInput = findViewById(R.id.display_input)
        displayResult = findViewById(R.id.display_result)

        // Find all ImageButtons and set up click listeners
        setupNumberButtons()
        setupOperatorButtons()
        setupFunctionButtons()
    }

    private fun setupNumberButtons() {
        val numberButtons = listOf(
            R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4,
            R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9
        )
        for (id in numberButtons) {
            findViewById<ImageButton>(id).setOnClickListener {
                onNumberClick(id)
            }
        }
    }

    private fun setupOperatorButtons() {
        findViewById<ImageButton>(R.id.btn_add).setOnClickListener { onOperatorClick("+") }
        findViewById<ImageButton>(R.id.btn_minus).setOnClickListener { onOperatorClick("-") }
        findViewById<ImageButton>(R.id.btn_multiply).setOnClickListener { onOperatorClick("*") }
        findViewById<ImageButton>(R.id.btn_divide).setOnClickListener { onOperatorClick("/") }
        findViewById<ImageButton>(R.id.btn_equal).setOnClickListener { onEqualClick() }
        findViewById<ImageButton>(R.id.btn_dot).setOnClickListener { onDotClick() }
    }

    private fun setupFunctionButtons() {
        findViewById<ImageButton>(R.id.btn_clear).setOnClickListener { onClearClick() }
        findViewById<ImageButton>(R.id.btn_plus_minus).setOnClickListener { onPlusMinusClick() }
        findViewById<ImageButton>(R.id.btn_percent).setOnClickListener { onPercentClick() }
    }

    private fun onNumberClick(buttonId: Int) {
        if (isNewOperation) {
            currentInput.clear()
            isNewOperation = false
        }
        val number = when (buttonId) {
            R.id.btn_0 -> "0"
            R.id.btn_1 -> "1"
            R.id.btn_2 -> "2"
            R.id.btn_3 -> "3"
            R.id.btn_4 -> "4"
            R.id.btn_5 -> "5"
            R.id.btn_6 -> "6"
            R.id.btn_7 -> "7"
            R.id.btn_8 -> "8"
            R.id.btn_9 -> "9"
            else -> ""
        }
        if (currentInput.toString() == "0" && number != "0") {
            currentInput.clear()
        }
        currentInput.append(number)
        updateDisplay()
    }

    private fun onOperatorClick(operator: String) {
        if (!isNewOperation) {
            lastNumber = currentInput.toString()
            isNewOperation = true
            lastOperation = operator
            displayResult.text = lastNumber + " " + lastOperation
        }
    }

    private fun onEqualClick() {
        if (!isNewOperation && lastOperation.isNotEmpty()) {
            val secondNumber = currentInput.toString()
            val result = performCalculation(lastNumber.toDouble(), secondNumber.toDouble(), lastOperation)
            displayResult.text = lastNumber + " " + lastOperation + " " + secondNumber + " ="

            val formattedResult = String.format("%.5f", result)
            currentInput.clear().append(formattedResult.trimEnd('0').trimEnd('.'))

            updateDisplay()
            lastOperation = ""
            lastNumber = ""
            isNewOperation = true
        }
    }

    private fun onDotClick() {
        if (isNewOperation) {
            currentInput.clear().append("0")
            isNewOperation = false
        }
        if (!currentInput.contains(".")) {
            currentInput.append(".")
        }
        updateDisplay()
    }

    private fun onClearClick() {
        currentInput.clear().append("0")
        displayResult.text = ""
        lastOperation = ""
        lastNumber = ""
        isNewOperation = true
        updateDisplay()
    }

    private fun onPlusMinusClick() {
        val value = currentInput.toString().toDoubleOrNull()
        if (value != null && value != 0.0) {
            currentInput.clear().append((value * -1).toString())
            updateDisplay()
        }
    }

    private fun onPercentClick() {
        val value = currentInput.toString().toDoubleOrNull()
        if (value != null) {
            currentInput.clear().append((value / 100).toString())
            updateDisplay()
        }
    }

    private fun performCalculation(first: Double, second: Double, operation: String): Double {
        return when (operation) {
            "+" -> first + second
            "-" -> first - second
            "*" -> first * second
            "/" -> first / second
            else -> 0.0
        }
    }

    private fun updateDisplay() {
        displayInput.text = currentInput.toString()
    }
}
