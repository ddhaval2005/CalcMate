package com.example.calcmate_new

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.Instant.ofEpochSecond

/**
 * Activity for the Timestamp Converter tool.
 *
 * This activity allows users to:
 * 1. View the current Unix timestamp and its human-readable date.
 * 2. Convert a Unix timestamp (seconds) to a human-readable date and time.
 * 3. Convert a human-readable date and time string to a Unix timestamp.
 *
 * The code uses the modern Java Time API (`java.time`) for robust date and time
 * handling.
 */
class TimestampActivity : AppCompatActivity() {

    // Declare all UI elements as member variables
    private lateinit var unixTimestampTextView: TextView
    private lateinit var humanDateTextView: TextView
    private lateinit var inputTimestampEditText: EditText
    private lateinit var convertToDateButton: Button
    private lateinit var inputDateEditText: EditText
    private lateinit var convertToTimestampButton: Button
    private lateinit var resultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timestamp)

        // Initialize UI elements from the XML layout
        unixTimestampTextView = findViewById(R.id.unixTimestampTextView)
        humanDateTextView = findViewById(R.id.humanDateTextView)
        inputTimestampEditText = findViewById(R.id.inputTimestampEditText)
        convertToDateButton = findViewById(R.id.convertToDateButton)
        inputDateEditText = findViewById(R.id.inputDateEditText)
        convertToTimestampButton = findViewById(R.id.convertToTimestampButton)
        resultTextView = findViewById(R.id.resultTextView)

        // Set up initial display with the current timestamp
        displayCurrentTimestamp()

        // Set up button listeners for conversions
        convertToDateButton.setOnClickListener {
            convertToDate()
        }

        convertToTimestampButton.setOnClickListener {
            convertToTimestamp()
        }
    }

    /**
     * Updates the UI with the current Unix timestamp and human-readable date.
     */
    private fun displayCurrentTimestamp() {
        // Get the current instant and convert it to a timestamp in seconds
        val currentInstant = Instant.now()
        val currentUnixTimestamp = currentInstant.epochSecond

        // Format the instant into a human-readable date and time string
        val currentZonedDateTime = currentInstant.atZone(ZoneId.systemDefault())
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val humanDate = currentZonedDateTime.format(formatter)

        // Update the TextViews on the screen
        unixTimestampTextView.text = "Current Unix Timestamp: $currentUnixTimestamp"
        humanDateTextView.text = "Current Date: $humanDate"
    }

    /**
     * Converts a Unix timestamp from the input field to a human-readable date.
     */
    private fun convertToDate() {
        val timestampStr = inputTimestampEditText.text.toString()
        if (timestampStr.isBlank()) {
            resultTextView.text = "Please enter a timestamp."
            return
        }

        try {
            val timestamp = timestampStr.toLong()
            val instant = ofEpochSecond(timestamp)
            val zonedDateTime = instant.atZone(ZoneId.systemDefault())
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val humanDate = zonedDateTime.format(formatter)

            resultTextView.text = "Converted Date: $humanDate"
        } catch (e: NumberFormatException) {
            resultTextView.text = "Invalid timestamp. Please enter a valid number."
        }
    }

    /**
     * Converts a human-readable date from the input field to a Unix timestamp.
     * The expected format is "yyyy-MM-dd HH:mm:ss".
     */
    private fun convertToTimestamp() {
        val dateStr = inputDateEditText.text.toString()
        if (dateStr.isBlank()) {
            resultTextView.text = "Please enter a date."
            return
        }

        try {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val zonedDateTime = ZonedDateTime.parse(dateStr, formatter.withZone(ZoneId.systemDefault()))
            val timestamp = zonedDateTime.toEpochSecond()

            resultTextView.text = "Converted Timestamp: $timestamp"
        } catch (e: DateTimeParseException) {
            resultTextView.text = "Invalid date format. Please use 'YYYY-MM-DD HH:MM:SS'."
        }
    }
}
