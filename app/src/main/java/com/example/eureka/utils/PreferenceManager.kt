package com.example.eureka.utils

import android.content.Context


/*this will store the logic to temporarily store data*/
class PreferenceManager(context: Context) {

    private val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    // Function to save a string value
    fun saveStringValue(key: String, value: String) {
        with(sharedPreferences.edit()) {
            putString(key, value)
            apply() // Apply changes immediately
        }
    }

    // Function to retrieve a string value
    fun getStringValue(key: String, value: String = ""): String {
        return sharedPreferences.getString(key, value) ?: value
    }
}