package com.example.calcmate_new

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class FeedbackActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        val feedbackEditText: EditText = findViewById(R.id.editText_feedback)
        val ratingBar: RatingBar = findViewById(R.id.ratingBar_app_rating)
        val sendButton: Button = findViewById(R.id.button_send_feedback)

        sendButton.setOnClickListener {
            val feedbackText = feedbackEditText.text.toString()
            val rating = ratingBar.rating

            if (feedbackText.isBlank() || rating == 0f) {
                Toast.makeText(this, "Please provide your feedback and a rating.", Toast.LENGTH_SHORT).show()
            } else {
                // Here, you would implement the logic to send the feedback to a backend service.
                // For this example, we'll just show a confirmation message.

                // You can get the feedback text and rating like this:
                // val feedbackData = mapOf("text" to feedbackText, "rating" to rating)
                // In a real app, you would send this to a Firestore database or a server.

                Toast.makeText(this, "Thank you for your feedback!", Toast.LENGTH_SHORT).show()
                finish() // Close the feedback activity
            }
        }
    }
}
