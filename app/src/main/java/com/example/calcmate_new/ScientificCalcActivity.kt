package com.example.calcmate_new

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.*

class ScientificCalcActivity : AppCompatActivity() {

    private lateinit var displayInput: TextView
    private lateinit var displayResult: TextView

    private var currentInput = StringBuilder("0")
    private var lastOperation = ""
    private var lastNumber = ""
    private var isNewOperation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scientific_calc)

        displayInput = findViewById(R.id.display_input)
        displayResult = findViewById(R.id.display_result)

        // Find all ImageButtons and set up click listeners
        setupNumberButtons()
        setupOperatorButtons()
        setupFunctionButtons()
        setupScientificButtons()
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
        findViewById<ImageButton>(R.id.btn_pow).setOnClickListener { onOperatorClick("^") }
        findViewById<ImageButton>(R.id.btn_mod).setOnClickListener { onOperatorClick("%") }
    }

    private fun setupFunctionButtons() {
        findViewById<ImageButton>(R.id.btn_clear).setOnClickListener { onClearClick() }
        findViewById<ImageButton>(R.id.btn_plus_minus).setOnClickListener { onPlusMinusClick() }
        findViewById<ImageButton>(R.id.btn_percent).setOnClickListener { onPercentClick() }
    }

    //sin and all funs in degrees

    private fun setupScientificButtons() {
        findViewById<ImageButton>(R.id.btn_sin).setOnClickListener { onScientificFunctionClick("sin") }
        findViewById<ImageButton>(R.id.btn_cos).setOnClickListener { onScientificFunctionClick("cos") }
        findViewById<ImageButton>(R.id.btn_tan).setOnClickListener { onScientificFunctionClick("tan") }
        findViewById<ImageButton>(R.id.btn_log).setOnClickListener { onScientificFunctionClick("log") }
        findViewById<ImageButton>(R.id.btn_ln).setOnClickListener { onScientificFunctionClick("ln") }
        findViewById<ImageButton>(R.id.btn_sqrt).setOnClickListener { onScientificFunctionClick("sqrt") }
        findViewById<ImageButton>(R.id.btn_pi).setOnClickListener { onPiClick() }
        findViewById<ImageButton>(R.id.btn_fact).setOnClickListener { onFactorialClick() }
        findViewById<ImageButton>(R.id.btn_exp).setOnClickListener { onExpClick() }
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

    private fun onScientificFunctionClick(function: String) {
        val value = currentInput.toString().toDoubleOrNull()
        if (value != null) {
            val result = when (function) {
                "sin" -> sin(Math.toRadians(value))
                "cos" -> cos(Math.toRadians(value))
                "tan" -> tan(Math.toRadians(value))
                "log" -> log10(value)
                "ln" -> ln(value)
                "sqrt" -> sqrt(value)
                else -> 0.0
            }
            val formattedResult = String.format("%.5f", result)
            currentInput.clear().append(formattedResult.trimEnd('0').trimEnd('.'))
            displayResult.text = "$function($value) ="
            updateDisplay()
        }
    }

    private fun onPiClick() {
        currentInput.clear().append(String.format("%.5f", PI).trimEnd('0').trimEnd('.'))
        updateDisplay()
    }

    private fun onFactorialClick() {
        val value = currentInput.toString().toDoubleOrNull()
        if (value != null && value >= 0 && value % 1.0 == 0.0) {
            var result = 1.0
            for (i in 1..value.toInt()) {
                result *= i
            }
            currentInput.clear().append(result.toInt().toString())
            displayResult.text = "${value.toInt()}! ="
            updateDisplay()
        } else {
            currentInput.clear().append("Error")
            updateDisplay()
        }
    }

    private fun onExpClick() {
        val value = currentInput.toString().toDoubleOrNull()
        if (value != null) {
            val result = exp(value)
            val formattedResult = String.format("%.5f", result)
            currentInput.clear().append(formattedResult.trimEnd('0').trimEnd('.'))
            displayResult.text = "e^$value ="
            updateDisplay()
        }
    }

    private fun performCalculation(first: Double, second: Double, operation: String): Double {
        return when (operation) {
            "+" -> first + second
            "-" -> first - second
            "*" -> first * second
            "/" -> first / second
            "^" -> first.pow(second)
            "%" -> first % second
            else -> 0.0
        }
    }

    private fun updateDisplay() {
        displayInput.text = currentInput.toString()
    }
}
