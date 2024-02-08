package com.example.eureka.presentation.screens.states

data class MainScreenState(
    val isWindowOpen: Boolean = false,
    val correctAnswer: Int = 0,
    val currentScore: Int = 0,
    val animTime: Int = 7,
    val shouldEndGame:Boolean=false,
    val challenge: String = "",
    val selectedAnswer: String = ""//this must be a string so that we can know when the user has actually input the answer
)