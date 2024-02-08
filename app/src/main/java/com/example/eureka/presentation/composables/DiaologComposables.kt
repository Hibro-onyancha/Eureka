package com.example.eureka.presentation.composables

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.eureka.presentation.theme.mediumShape
import com.example.eureka.presentation.theme.smallShape

@Composable
fun DialogWindow(
    isVisible: Boolean,
    positiveText: String,
    negativeText: String,
    onPositiveClick: () -> Unit,
    onNegativeClick: () -> Unit,
    onDismiss: () -> Unit,
    title: String,
    desc: String
) {
    AnimatedVisibility(visible = isVisible) {
        Dialog(
            onDismissRequest = { onDismiss() },
            properties = DialogProperties(usePlatformDefaultWidth = true)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(mediumShape)
                    .background(MaterialTheme.colorScheme.background)
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CommonText(
                    text = title,
                    isBold = true,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = 2,
                    textSize = 4
                )
                Spacer(modifier = Modifier.height(10.dp))
                CommonText(
                    text = desc,
                    isBold = false,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = 2,
                    textSize = 3,
                    color=MaterialTheme.colorScheme.outline
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {

                    CommonText(
                        text = negativeText,
                        modifier = Modifier
                            .height(30.dp)
                            .width(60.dp)
                            .clip(smallShape)
                            .clickable {
                                onNegativeClick()
                            },
                        textSize = 3,
                        textAlign = 2,
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        isBold = false
                    )
                    CommonText(
                        text = positiveText,
                        modifier = Modifier
                            .height(30.dp)
                            .width(60.dp)
                            .clip(smallShape)
                            .clickable {
                                onPositiveClick()
                            },
                        textSize = 3,
                        textAlign = 2,
                        color = MaterialTheme.colorScheme.primary,
                        isBold = false

                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    Box(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
    ) {
        DialogWindow(
            isVisible = true,
            positiveText = "retry",
            negativeText = "exit",
            onPositiveClick = { /*TODO*/ },
            onNegativeClick = { /*TODO*/ },
            onDismiss = { /*TODO*/ },
            title = "Game Over!",
            desc = "200"
        )

    }
}