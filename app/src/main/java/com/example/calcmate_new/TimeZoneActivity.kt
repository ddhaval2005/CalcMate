package com.example.calcmate_new

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

class TimeZoneActivity : AppCompatActivity() {

    private lateinit var spinnerFromTimeZone: Spinner
    private lateinit var spinnerToTimeZone: Spinner
    private lateinit var btnConvert: Button
    private lateinit var tvResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Corrected layout file name to match the XML file
        setContentView(R.layout.activity_time_zone)

        // Find views from the layout
        spinnerFromTimeZone = findViewById(R.id.spinnerFromTimeZone)
        spinnerToTimeZone = findViewById(R.id.spinnerToTimeZone)
        btnConvert = findViewById(R.id.btnConvert)
        tvResult = findViewById(R.id.tvResult)

        // Populate the spinners with available time zones
        val timeZoneIds = ZoneId.getAvailableZoneIds().sorted()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, timeZoneIds.toTypedArray())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFromTimeZone.adapter = adapter
        spinnerToTimeZone.adapter = adapter

        // Set a default selection for better user experience
        val defaultFromIndex = timeZoneIds.indexOf(ZoneId.systemDefault().id)
        if (defaultFromIndex != -1) {
            spinnerFromTimeZone.setSelection(defaultFromIndex)
        }
        val defaultToIndex = timeZoneIds.indexOf("America/New_York")
        if (defaultToIndex != -1) {
            spinnerToTimeZone.setSelection(defaultToIndex)
        }

        // Set click listener for the convert button
        btnConvert.setOnClickListener {
            calculateTimeDifference()
        }
    }

    private fun calculateTimeDifference() {
        // Get the selected time zones
        val fromZoneId = ZoneId.of(spinnerFromTimeZone.selectedItem.toString())
        val toZoneId = ZoneId.of(spinnerToTimeZone.selectedItem.toString())

        // Get the current instant in time
        val now = Instant.now()

        // Convert the current instant to zoned times for both selected time zones
        val fromZonedTime = now.atZone(fromZoneId)
        val toZonedTime = now.atZone(toZoneId)

        // Calculate the difference in minutes
        val differenceInMinutes = ChronoUnit.MINUTES.between(fromZonedTime.toLocalDateTime(), toZonedTime.toLocalDateTime())
        val differenceInHours = differenceInMinutes.toDouble() / 60.0

        // Display the result with a more informative message
        val resultText = "Current time in ${fromZoneId.id}: ${fromZonedTime.toLocalTime()}\n" +
                "Current time in ${toZoneId.id}: ${toZonedTime.toLocalTime()}\n\n" +
                "Difference: ${String.format("%.2f", differenceInHours)} hours"
        tvResult.text = resultText
    }
}
