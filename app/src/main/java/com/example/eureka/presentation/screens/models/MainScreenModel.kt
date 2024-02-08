package com.example.eureka.presentation.screens.models

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.CountDownTimer
import android.os.VibrationEffect
import android.os.VibratorManager
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eureka.presentation.screens.data.EntryScreenData
import com.example.eureka.presentation.screens.states.MainScreenState
import com.example.eureka.utils.PreferenceManager
import com.example.eureka.utils.getHighScore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Random

class MainScreenModel : ViewModel() {

    private val _uiState = MutableStateFlow(MainScreenState())
    val uiState = _uiState.asStateFlow()

    fun updateEndGame(){
        _uiState.update {
            it.copy(
                shouldEndGame = true
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    fun endGame(context: Context) {
        if (_uiState.value.shouldEndGame) {
            initiateShortVibration(context)
            if (_uiState.value.currentScore > getHighScore(context)) {
                val prefs = PreferenceManager(context)
                prefs.saveStringValue(
                    key = EntryScreenData.HIGH_SCORE,
                    value = _uiState.value.currentScore.toString()
                )
            }
            _uiState.update {
                it.copy(
                    isWindowOpen = true
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    fun onAnswerClick(answer: Int, context: Context) {
        getQuery()
        if (_uiState.value.currentScore < 0) {
            endGame(context)
        }
        _uiState.update {
            it.copy(
                selectedAnswer = answer.toString()
            )
        }
        if (_uiState.value.selectedAnswer != "") {

            if (_uiState.value.selectedAnswer == _uiState.value.correctAnswer.toString()) {
                _uiState.update {
                    it.copy(
                        currentScore = _uiState.value.currentScore + 10,
                        animTime = _uiState.value.animTime + 3//add three seconds for every correct answer
                    )
                }
            } else {
                initiateShortVibration(context)
                _uiState.update {
                    it.copy(
                        currentScore = _uiState.value.currentScore - 5
                    )
                }
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun initiateShortVibration(context: Context) {
        // Check for VIBRATE permission
        if (context.checkSelfPermission(Manifest.permission.VIBRATE) != PackageManager.PERMISSION_GRANTED) {
            // Request permission if not granted
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.VIBRATE),
                1
            )
            return
        }

        // Get the system vibrator service
        val vibratorService =
            context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        val vibrator = vibratorService.defaultVibrator

        // Define a short vibration pattern
        val vibrationEffect = VibrationEffect.createOneShot(
            // Adjust duration for desired shortness (e.g., 50-100ms)
            50,
            // Adjust amplitude for desired intensity (e.g., 50-100)
            VibrationEffect.DEFAULT_AMPLITUDE
        )

        // Vibrate the device
        vibrator.vibrate(vibrationEffect)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun initiateLongVibration(context: Context) {
        // Check for VIBRATE permission
        if (context.checkSelfPermission(Manifest.permission.VIBRATE) != PackageManager.PERMISSION_GRANTED) {
            // Request permission if not granted
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.VIBRATE),
                1
            )
            return
        }

        // Get the system vibrator service
        val vibratorService =
            context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        val vibrator = vibratorService.defaultVibrator

        // Define vibration pattern (adjust amplitude and duration as needed)
        val vibrationEffect = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            VibrationEffect.createWaveform(longArrayOf(0, 2000, 500, 2000), -1)
        } else {
            // Use an alternative approach for API levels below 26
            VibrationEffect.createOneShot(5000, VibrationEffect.DEFAULT_AMPLITUDE)
        }

        // Vibrate the device
        vibrator.vibrate(vibrationEffect)
    }

    fun answerList(): List<Int> {
        val number = _uiState.value.correctAnswer
        if (number == 0) {
            return listOf(0, 0, 0)
        }

        val parity = number % 2
        val randomNumbers = (0..9).shuffled().take(2) // Shuffle once here
        val generatedNumbers = listOf(number + randomNumbers[0], number + randomNumbers[1])
            .map { if (parity == 0) it - (it % 2) else it + (it % 2) }

        // Combine and shuffle only once:
        val combinedList = generatedNumbers.toMutableList().apply { add(number) }
        return combinedList.shuffled() // Shuffle once here
    }


    private fun getQuery() {
        val operators: List<String> = listOf(
            "+",
            "-",
            "/",
            "*"
        )
        // Use random to get an index and access the operator at that index
        val randomIndex = Random().nextInt(operators.size)
        when (operators[randomIndex]) {
            "+" -> {
                additionFunction()
            }

            "-" -> {
                subtractionFunction()
            }

            "*" -> {
                multiplicationFunction()
            }

            "/" -> {
                divisionFunction()
            }
        }
    }

    private fun additionFunction() {
        val digits = (0..9).toList()

        // Generate three random digits for num1
        val num1Digits = digits.shuffled().take(3)
        val num1 = num1Digits.joinToString("").toInt()

        // Generate three random digits for num2 (excluding used digits from num1)
        val remainingDigits = digits.minus(num1Digits.toSet())
        val num2Digits = remainingDigits.shuffled().take(3)
        val num2 = num2Digits.joinToString("").toInt()

        // Add num1 and num2
        val sum = num1 + num2
        viewModelScope.launch {
            delay(200)
            _uiState.update {
                it.copy(
                    correctAnswer = sum,
                    challenge = "$num1 + $num2"
                )
            }
        }
    }

    private fun subtractionFunction() {
        val digits = (0..9).toList()

        // Generate two random digits for num1 (handle leading zero)
        val num1Digits = digits.shuffled().take(2)
        val num1 = if (num1Digits[0] == 0 && num1Digits[1] != 0) {
            num1Digits[1].toString().toInt()
        } else {
            num1Digits.joinToString("").toInt()
        }

        // Generate a random digit for num2 (handle leading zero)
        val num2 = digits.shuffled().first()

        // Calculate the difference (handle negative result)
        val difference = num1 - num2
        val value = if (difference >= 0) difference else -difference
        viewModelScope.launch {
            delay(200)
            _uiState.update {
                it.copy(
                    correctAnswer = value,
                    challenge = "$num1 - $num2"
                )
            }
        }
    }

    private fun multiplicationFunction() {
        val digits = (0..9).toList()
        val randomNumbers = digits.shuffled().take(2)
        val num1 = randomNumbers[0]
        val num2 = randomNumbers[1]
        viewModelScope.launch {
            delay(200)
            _uiState.update {
                it.copy(
                    correctAnswer = num1 * num2,
                    challenge = "$num1 × $num2"
                )
            }
        }

    }

    private fun divisionFunction() {
        val digits = (1..9).toList()

        // Generate two random digits for num1 (excluding 0)
        val num1Digits = digits.shuffled().take(2)
        val num1 = num1Digits.joinToString("").toInt()

        // Generate odd or even num2 based on num1's parity
        val num2 = if (num1 % 2 == 0) {
            // Choose a random even number from 2 to 4
            (2..4 step 2).shuffled().first()
        } else {
            // Choose a random odd number from 1 to 5
            (1..5 step 2).shuffled().first()
        }
        viewModelScope.launch {
            delay(200)
            // Perform division (handle potential division by zero)
            _uiState.update {
                it.copy(
                    correctAnswer = if (num2 == 0) {
                        // Handle division by zero
                        0
                    } else {
                        num1 / num2
                    }, challenge = "$num1 ÷ $num2"
                )
            }
        }

    }

    fun startCountdown(callback: (Long) -> Unit) {
        object : CountDownTimer((_uiState.value.animTime * 1000).toLong(), 170) {
            override fun onTick(millisUntilFinished: Long) {
                callback(millisUntilFinished) // Pass remaining time to the callback
            }

            override fun onFinish() {
                callback(0) // Signal completion with 0 remaining time
            }
        }.start()
    }

}