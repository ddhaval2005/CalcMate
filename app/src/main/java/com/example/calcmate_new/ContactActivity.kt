package com.example.calcmate_new

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ContactActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        // Find the email and website layouts to make them clickable
        val emailLayout: LinearLayout = findViewById(R.id.email_layout)
        val websiteLayout: LinearLayout = findViewById(R.id.website_layout)

        // Set a click listener for the email layout
        emailLayout.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                // Correct way to set the URI within the 'apply' scope.
                // You just need to use 'data = ...' to access the property directly.
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf("team.j.d@marwadiuniversity.ac.in")) // Assuming this email format based on your input
                putExtra(Intent.EXTRA_SUBJECT, "App Support Inquiry")
            }
            try {
                startActivity(emailIntent)
            } catch (e: Exception) {
                // Handle cases where no email app is installed
                Toast.makeText(this, "No email client found.", Toast.LENGTH_SHORT).show()
            }
        }

        // Set a click listener for the website layout
        websiteLayout.setOnClickListener {
            val websiteUri = Uri.parse("http://www.marwadiuniversity.ac.in")
            val websiteIntent = Intent(Intent.ACTION_VIEW, websiteUri)
            try {
                startActivity(websiteIntent)
            } catch (e: Exception) {
                // Handle cases where no browser app is installed
                Toast.makeText(this, "No browser found.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
