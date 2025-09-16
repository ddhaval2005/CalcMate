package com.example.calcmate_new

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.*

class DateDifferenceActivity : AppCompatActivity() {

    private lateinit var tvFromDate: TextView
    private lateinit var tvToDate: TextView
    private lateinit var btnCalculate: Button
    private lateinit var tvResult: TextView
    private lateinit var cardResult: CardView

    private var fromDate: Calendar = Calendar.getInstance()
    private var toDate: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date_difference)

        // Initialize views
        tvFromDate = findViewById(R.id.tvFromDate)
        tvToDate = findViewById(R.id.tvToDate)
        btnCalculate = findViewById(R.id.btnCalculate)
        tvResult = findViewById(R.id.tvResult)
        cardResult = findViewById(R.id.cardResult)

        // Set click listeners for date selection
        tvFromDate.setOnClickListener { showDatePickerDialog(tvFromDate, fromDate) }
        tvToDate.setOnClickListener { showDatePickerDialog(tvToDate, toDate) }
        btnCalculate.setOnClickListener { calculateDifference() }

        // Set initial dates to today's date
        updateDateTextView(tvFromDate, fromDate)
        updateDateTextView(tvToDate, toDate)
    }

    private fun showDatePickerDialog(textView: TextView, calendar: Calendar) {
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, monthOfYear, dayOfMonth ->
                calendar.set(year, monthOfYear, dayOfMonth)
                updateDateTextView(textView, calendar)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun updateDateTextView(textView: TextView, calendar: Calendar) {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        textView.text = dateFormat.format(calendar.time)
    }

    private fun calculateDifference() {
        if (toDate.before(fromDate)) {
            Toast.makeText(this, "The 'To' date must be after the 'From' date", Toast.LENGTH_SHORT).show()
            cardResult.visibility = View.GONE
            return
        }

        val fromLocalDate = LocalDate.of(fromDate.get(Calendar.YEAR), fromDate.get(Calendar.MONTH) + 1, fromDate.get(Calendar.DAY_OF_MONTH))
        val toLocalDate = LocalDate.of(toDate.get(Calendar.YEAR), toDate.get(Calendar.MONTH) + 1, toDate.get(Calendar.DAY_OF_MONTH))

        val years = ChronoUnit.YEARS.between(fromLocalDate, toLocalDate)
        val months = ChronoUnit.MONTHS.between(fromLocalDate, toLocalDate) % 12
        val days = ChronoUnit.DAYS.between(fromLocalDate.plusMonths(months), toLocalDate)

        val resultText = "Years: $years\nMonths: $months\nDays: $days"
        tvResult.text = resultText
        cardResult.visibility = View.VISIBLE
    }
}
