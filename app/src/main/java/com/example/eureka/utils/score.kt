package com.example.eureka.utils

import android.content.Context
import com.example.eureka.presentation.screens.data.EntryScreenData

fun getHighScore(context: Context): Int {
    return PreferenceManager(context).getStringValue(key = EntryScreenData.HIGH_SCORE, value = "0")
        .toInt()
}