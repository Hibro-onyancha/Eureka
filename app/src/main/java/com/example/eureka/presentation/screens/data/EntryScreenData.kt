package com.example.eureka.presentation.screens.data

import android.content.Context
import com.example.eureka.R

class EntryScreenData(context: Context) {
    companion object {
        const val HIGH_SCORE = "high score"
    }
    val basicArithmetics = listOf(
        context.getString(R.string.answer_questions_under_mathematics),
        context.getString(R.string.questions_contain_basic_and_operations),
        context.getString(R.string._10_pts_per_correct_answer_given),
        context.getString(R.string._5pts_per_every_wrong_answer)

    )
}