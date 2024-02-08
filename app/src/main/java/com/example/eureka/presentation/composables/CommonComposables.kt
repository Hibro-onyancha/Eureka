package com.example.eureka.presentation.composables

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.Lottie
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.eureka.presentation.theme.extraLargeText
import com.example.eureka.presentation.theme.largeText
import com.example.eureka.presentation.theme.mediumLargeText
import com.example.eureka.presentation.theme.mediumText
import com.example.eureka.presentation.theme.smallShape
import com.example.eureka.presentation.theme.smallText


@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun AnimatedCountingText(
    timeInMilliseconds: Int,
    targetLong: Long,
    textStyle: TextStyle = TextStyle()
) {
    val currentValue by remember { mutableLongStateOf(0L) }
    val animatedValue = remember { Animatable(0f) }

    LaunchedEffect(key1 = currentValue) {
        animatedValue.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = timeInMilliseconds,
                easing = FastOutSlowInEasing
            )
        )
    }

    val transition = updateTransition(targetState = currentValue, label = "counting")
    val animatedProgress by transition.animateFloat(
        transitionSpec = { spring(stiffness = Spring.StiffnessLow) },
        targetValueByState = { _ -> animatedValue.value }, label = ""
    )

    val displayedValue = ((targetLong - 0) * animatedProgress).toLong() + 0

    Text(
        text = displayedValue.toString(),
        style = textStyle
    )
}

@Composable
fun RadioButtonComp(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    onClick: () -> Unit,
    options: List<String>,
    title: String,
    spacer: Int = 10
) {
    Column(
        modifier = modifier
            .fillMaxWidth(0.9f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .clip(smallShape)
                .background(if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent)
                .padding(3.dp)
                .clip(smallShape)
                .background(if (isSelected) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.surfaceVariant)
                .padding(6.dp)
                .clip(smallShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .clickable { onClick() }
                .padding(6.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
                    fontSize = extraLargeText,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                RadioButton(selected = isSelected, onClick = { onClick() })
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                LazyColumn {
                    items(items = options) {
                        Text(
                            text = it,
                            color = if (isSelected) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.outline,
                            fontSize = smallText,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(spacer.dp))
    }

}

@Composable
fun CommonText(
    modifier: Modifier = Modifier,
    textSize: Int = 1,
    textAlign: Int = 1,
    color: Color = MaterialTheme.colorScheme.onBackground,
    isBold: Boolean = true,
    text: String,
    maxLines: Int = 1
) {
    Text(
        text = text,
        color = color,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier,
        fontWeight = if (isBold) FontWeight.Bold else FontWeight.Light,
        fontSize = when (textSize) {
            1 -> smallText
            2 -> mediumText
            3 -> mediumLargeText
            4 -> largeText
            else -> extraLargeText
        },
        textAlign = when (textAlign) {
            1 -> TextAlign.Start
            2 -> TextAlign.Center
            else -> TextAlign.End
        }
    )
}

@Composable
fun Lottie(
    modifier: Modifier=Modifier,
    anim:Int
) {

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(anim))
    Box (
        modifier=modifier.fillMaxWidth()
    ){
        LottieAnimation(
            modifier = Modifier.fillMaxSize(),
            composition = composition,
            iterations = LottieConstants.IterateForever,
        )

    }
}