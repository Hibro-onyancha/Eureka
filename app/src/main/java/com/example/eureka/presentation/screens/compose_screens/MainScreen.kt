package com.example.eureka.presentation.screens.compose_screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eureka.R
import com.example.eureka.presentation.composables.CommonText
import com.example.eureka.presentation.composables.DialogWindow
import com.example.eureka.presentation.composables.Lottie
import com.example.eureka.presentation.screens.compose_screens.destinations.EntryScreenDestination
import com.example.eureka.presentation.screens.models.MainScreenModel
import com.example.eureka.presentation.theme.smallShape
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RequiresApi(Build.VERSION_CODES.S)
@Destination
@Composable
fun MainScreen(
    navigator: DestinationsNavigator,
    model: MainScreenModel = viewModel()
) {
    val context = LocalContext.current
    val uiState = model.uiState.collectAsState().value
    model.endGame(context)
    val animationState = remember { Animatable(initialValue = 0f) }
    model.startCountdown {
        if (it == 0L) {
            model.updateEndGame()
        }
    }
    LaunchedEffect(Unit) {
        animationState.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = uiState.animTime * 1000, // 2 seconds animation
                easing = LinearEasing // Adjust easing if needed
            )
        )
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 10.dp, top = 5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.65f)
                    .clip(smallShape)
                    .background(MaterialTheme.colorScheme.inverseOnSurface)
                    .padding(5.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                CommonText(text = "Score :${uiState.currentScore}", isBold = true, textSize = 3)

                Spacer(modifier = Modifier.height(10.dp))
            }
            CommonText(text = "ans :${uiState.correctAnswer}", isBold = true, textSize = 3)
            CommonText(text = "se :${uiState.selectedAnswer}", isBold = true, textSize = 3)

            Box(
                modifier = Modifier
                    .height(350.dp)
                    .fillMaxWidth(0.95f)
                    .border(
                        width = 2.dp,
                        shape = smallShape,
                        color = MaterialTheme.colorScheme.primary
                    )
                    .background(MaterialTheme.colorScheme.inverseOnSurface)
            ) {
                Lottie(
                    anim = R.raw.clouds,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .align(Alignment.TopCenter)
                )
                Lottie(
                    anim = R.raw.plane,
                    modifier = Modifier
                        .size(50.dp, 50.dp) // Adjust smaller box size as needed
                        .offset(
                            y = (animationState.value * 300).dp, // Adjust offsets for desired path
                            x = (animationState.value * 230).dp
                        )
                )
                Lottie(
                    anim = R.raw.ground,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .align(Alignment.BottomCenter)
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "what is ?",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.outline,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = uiState.challenge,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Spacer(modifier = Modifier.height(15.dp))
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 60.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    model.answerList().forEach {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .padding(end = 10.dp)
                                .clip(smallShape)
                                .clickable {
                                    model.onAnswerClick(it, context)
                                }
                                .background(MaterialTheme.colorScheme.inverseOnSurface)
                                .padding(5.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CommonText(
                                text = it.toString(),
                                textSize = 3,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                }
            }
        }
        DialogWindow(
            isVisible = uiState.isWindowOpen,
            positiveText = "retry",
            negativeText = "exit",
            onPositiveClick = { /*TODO*/ },
            onNegativeClick = {
                navigator.navigate(EntryScreenDestination) {
                    launchSingleTop = true
                    popUpTo(EntryScreenDestination.route)
                }
            },
            onDismiss = { /*TODO*/ },
            title = "Game over !",
            desc = "${uiState.currentScore}"
        )

    }
}
