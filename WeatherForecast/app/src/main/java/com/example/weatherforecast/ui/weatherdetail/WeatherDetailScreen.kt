package com.example.weatherforecast.ui.weatherdetail

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherforecast.ui.theme.Cyan
import com.example.weatherforecast.ui.weather.RetrySection
import com.example.weatherforecast.utils.getImageFromUrl

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherDetailScreen(viewModel: WeatherDetailViewModel = hiltViewModel()) {
    val isLoading = remember { viewModel.isLoading }
    val loadError = remember { viewModel.loadError }

    if (!isLoading.value && loadError.value.isEmpty()) {
        Column {
            TomorrowWeatherBox()
            DailyWeatherList()
        }
    } else if (loadError.value.isNotEmpty()) {
        RetrySection(error = loadError.value) {

        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TomorrowWeatherBox(viewModel: WeatherDetailViewModel = hiltViewModel()) {
    val tomorrowWeatherType = remember { viewModel.tomorrowWeatherType }
    val tomorrowImgUrl = remember { viewModel.tomorrowImgUrl }
    val tomorrowTempHigh = remember { viewModel.tomorrowTempHigh }
    val tomorrowTempLow = remember { viewModel.tomorrowTempLow }
    val isLoading = remember { viewModel.isLoading }

    if (!isLoading.value) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Cyan)
                    .padding(4.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = getImageFromUrl(tomorrowImgUrl.value)),
                        contentDescription = "Localized Description",
                        modifier = Modifier.size(144.dp)
                    )
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Tomorrow",
                            color = Color.White,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(6.dp)
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = tomorrowTempHigh.value,
                                color = Color.White,
                                fontSize = 72.sp,
                                modifier = Modifier.padding(1.dp),
                            )
                            Text(
                                text = "/",
                                color = Color.White,
                                fontSize = 48.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(1.dp),
                            )
                            Text(
                                text = tomorrowTempLow.value,
                                color = Color.White,
                                fontSize = 48.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(1.dp),
                            )
                        }
                        Text(
                            text = tomorrowWeatherType.value,
                            color = Color.White,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(PaddingValues(0.dp, 2.dp, 0.dp, 8.dp))
                        )
                    }
                }
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(color = Color.Yellow)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DailyWeatherList(viewModel: WeatherDetailViewModel = hiltViewModel()) {
    val dailyWeatherList = remember { viewModel.dailyWeatherList }
    val isLoading = remember { viewModel.isLoading }

    LazyColumn {
        items(dailyWeatherList.value) {
            if (!isLoading.value) {
                DailyWeatherListItem(
                    day = it.day,
                    imgUrl = it.img,
                    weatherType = it.weatherType,
                    highTemp = it.maxTemp,
                    lowTemp = it.minTemp
                )
            }
        }
    }
}

@Composable
fun DailyWeatherListItem(
    day: String,
    imgUrl: String,
    weatherType: String,
    highTemp: String,
    lowTemp: String,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 4.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .padding(12.dp, 4.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = day,
                    color = Color.Gray,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(getImageFromUrl(imgUrl)),
                        contentDescription = "Localized Description",
                        modifier = Modifier.size(56.dp)
                    )
                    Text(
                        text = weatherType,
                        color = Color.Gray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(4.dp, 0.dp)
                    )
                }
                Row {
                    Text(
                        text = highTemp,
                        color = Color.Gray,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "/",
                        color = Color.Gray,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = lowTemp,
                        color = Color.Gray,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}