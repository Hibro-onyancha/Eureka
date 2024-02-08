package com.example.eureka.presentation.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eureka.presentation.constants.EXTRA_LARGE_TEXT
import com.example.eureka.presentation.constants.LARGE_SHAPE
import com.example.eureka.presentation.constants.LARGE_TEXT
import com.example.eureka.presentation.constants.MEDIUM_LARGE_TEXT
import com.example.eureka.presentation.constants.MEDIUM_SHAPE
import com.example.eureka.presentation.constants.MEDIUM_SMALL_TEXT
import com.example.eureka.presentation.constants.SMALL_SHAPE
import com.example.eureka.presentation.constants.SMALL_TEXT

val smallShape = RoundedCornerShape(SMALL_SHAPE.dp)
val largeShape = RoundedCornerShape(LARGE_SHAPE.dp)
val mediumShape = RoundedCornerShape(MEDIUM_SHAPE.dp)
val smallText = SMALL_TEXT.sp
val largeText = LARGE_TEXT.sp
val extraLargeText = EXTRA_LARGE_TEXT.sp
val mediumText = MEDIUM_SMALL_TEXT.sp
val mediumLargeText = MEDIUM_LARGE_TEXT.sp
// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)