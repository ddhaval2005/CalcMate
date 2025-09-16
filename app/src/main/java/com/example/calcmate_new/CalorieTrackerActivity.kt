package com.example.calcmate_new

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CalorieTrackerActivity : AppCompatActivity() {

    // Data class to represent a single food item
    data class FoodItem(val name: String, val calories: Int)

    private val foodList = mutableListOf<FoodItem>()
    private var totalCalories = 0

    private lateinit var foodNameEditText: EditText
    private lateinit var caloriesEditText: EditText
    private lateinit var addFoodButton: Button
    private lateinit var totalCaloriesTextView: TextView
    private lateinit var foodListLinearLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calorietrack)

        // Initialize UI elements
        foodNameEditText = findViewById(R.id.editText_foodName)
        caloriesEditText = findViewById(R.id.editText_calories)
        addFoodButton = findViewById(R.id.button_addFood)
        totalCaloriesTextView = findViewById(R.id.textView_totalCalories)
        foodListLinearLayout = findViewById(R.id.linearLayout_foodList)

        addFoodButton.setOnClickListener {
            addFoodItem()
        }
    }

    private fun addFoodItem() {
        val foodName = foodNameEditText.text.toString().trim()
        val caloriesText = caloriesEditText.text.toString().trim()

        if (foodName.isEmpty() || caloriesText.isEmpty()) {
            Toast.makeText(this, "Please enter both food name and calories", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val calories = caloriesText.toInt()
            val newItem = FoodItem(foodName, calories)
            foodList.add(newItem)
            updateUI()
            clearInputs()
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Invalid calorie value. Please enter a number.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI() {
        // Clear existing views to avoid duplicates
        foodListLinearLayout.removeAllViews()

        // Recalculate total calories
        totalCalories = foodList.sumOf { it.calories }
        totalCaloriesTextView.text = totalCalories.toString()

        // Add each food item to the list UI
        val inflater = LayoutInflater.from(this)
        for (item in foodList.reversed()) { // Show most recent items at the top
            val foodItemView = inflater.inflate(R.layout.item_food, foodListLinearLayout, false)
            val foodNameView = foodItemView.findViewById<TextView>(R.id.textView_foodItemName)
            val foodCaloriesView = foodItemView.findViewById<TextView>(R.id.textView_foodItemCalories)

            // Find the delete button in the inflated view
            val deleteButton = foodItemView.findViewById<ImageButton>(R.id.deleteButton)

            foodNameView.text = item.name
            foodCaloriesView.text = "${item.calories} cal"

            // Set a click listener for the delete button
            deleteButton.setOnClickListener {
                // Remove the item from the data list
                foodList.remove(item)
                // Rebuild the UI to reflect the change
                updateUI()
            }

            foodListLinearLayout.addView(foodItemView)
        }
    }

    private fun clearInputs() {
        foodNameEditText.text.clear()
        caloriesEditText.text.clear()
        foodNameEditText.requestFocus()
    }
}
