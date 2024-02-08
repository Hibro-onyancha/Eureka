package com.example.eureka.presentation.screens.compose_screens

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eureka.R
import com.example.eureka.presentation.composables.AnimatedCountingText
import com.example.eureka.presentation.composables.CommonText
import com.example.eureka.presentation.composables.RadioButtonComp
import com.example.eureka.presentation.screens.compose_screens.destinations.MainScreenDestination
import com.example.eureka.presentation.screens.data.EntryScreenData
import com.example.eureka.presentation.theme.largeText
import com.example.eureka.presentation.theme.smallShape
import com.example.eureka.utils.getHighScore
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph(start = true)
@Destination
@Composable
fun EntryScreen(
    navigator: DestinationsNavigator
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 30.dp, bottom = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LazyColumn(Modifier.fillMaxWidth(0.9f)) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = stringResource(R.string.eureka),
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    CommonText(
                        text = stringResource(R.string.rule_don_t_let_the_plane_fall_lest_you_loose_10_pts),
                        isBold = false,
                        color = MaterialTheme.colorScheme.outline,
                        textSize = 4,
                        maxLines = 5,
                        modifier = Modifier.fillMaxWidth(0.8f),
                        textAlign = 2
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                }
            }
            item {
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(smallShape)
                            .background(MaterialTheme.colorScheme.inverseOnSurface)
                            .padding(5.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CommonText(
                                text = stringResource(R.string.high_score),
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            AnimatedCountingText(
                                timeInMilliseconds = 1000,
                                targetLong = getHighScore(context).toLong(),
                                textStyle = TextStyle(
                                    fontSize = largeText,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    fontWeight = FontWeight.Bold,

                                    )
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(smallShape)
                            .clickable {
                                navigator.navigate(MainScreenDestination) {
                                    launchSingleTop = true
                                    popUpTo(MainScreenDestination.route)
                                }
                            }
                            .background(MaterialTheme.colorScheme.inverseOnSurface)
                            .padding(5.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            CommonText(
                                text = stringResource(R.string.play_game),
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Icon(
                                painter = painterResource(id = R.drawable.play),
                                contentDescription = stringResource(R.string.play_game),
                                tint = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.size(60.dp)
                            )
                        }
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(70.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CommonText(
                        text = stringResource(R.string.choose_subject),
                        textSize = 4,
                        color = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = 2
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    RadioButtonComp(
                        isSelected = true,
                        onClick = { /*TODO*/ },
                        options = EntryScreenData(context).basicArithmetics,
                        title = stringResource(R.string.basic_arithmetics)
                    )
                }
            }
        }
    }
}