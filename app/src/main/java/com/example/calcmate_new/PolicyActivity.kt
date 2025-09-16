package com.example.calcmate_new

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PolicyActivity : AppCompatActivity() {
    private lateinit var policyContentTextView: TextView
    private lateinit var lastUpdatedTextView: TextView
    private lateinit var statusTextView: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_policy)

        // Initialize views
        policyContentTextView = findViewById(R.id.policy_content)
        lastUpdatedTextView = findViewById(R.id.last_updated_text)
        statusTextView = findViewById(R.id.status_text)

        // Let's simulate fetching the policy from a remote source to show "real-time" behavior
        fetchPolicyContent()
    }

    private fun fetchPolicyContent() {
        // Here you would typically make an API call or check a database for the latest policy.
        // For this example, we'll simulate a network delay.

        // Show a loading indicator (if you have one in your layout)
        // findViewById<ProgressBar>(R.id.progress_bar)?.visibility = View.VISIBLE

        // Simulate a 2-second delay
        Handler(Looper.getMainLooper()).postDelayed({
            // Assuming we've successfully "fetched" the content.
            val fetchedPolicyText = getString(R.string.privacy_policy_text) // This would be the data from your API
            val lastUpdatedDate = "2024-05-20"
            val statusMessage = "Your policy is up-to-date."

            // Update the UI with the fetched data
            policyContentTextView.text = fetchedPolicyText
            lastUpdatedTextView.text = "Last updated: $lastUpdatedDate"
            statusTextView.text = statusMessage

            // Hide the loading indicator
            // findViewById<ProgressBar>(R.id.progress_bar)?.visibility = View.GONE

            // Now, we simulate checking for an update
            checkForPolicyUpdate(lastUpdatedDate)

        }, 2000)
    }

    private fun checkForPolicyUpdate(currentDate: String) {
        // Here you would compare the fetched date with the latest date from your server.
        // For this example, we'll just show the status text.
        // If an update was available, you could change the status text color to red
        // and show a different message, e.g., "A new version of the policy is available."
        statusTextView.visibility = View.VISIBLE
    }
}
