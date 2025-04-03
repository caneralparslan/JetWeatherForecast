package com.amasmobile.jet_weather_forecast.screens.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.amasmobile.jet_weather_forecast.R
import com.amasmobile.jet_weather_forecast.components.WeatherAppBar


@Composable
fun WeatherAboutScreen(navController: NavController){

    Scaffold(
        topBar = {
            WeatherAppBar(
                title = "About",
                isMainScreen = false,
                navController = navController
            )
        }
    ) {
            padding ->
        AboutContent(paddingValues = padding)
    }
    
}

@Composable
fun AboutContent(paddingValues: PaddingValues) {

    Column (
        modifier = Modifier.fillMaxSize().padding(paddingValues),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            textAlign = TextAlign.Center,
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )){
                    append(stringResource(R.string.app_version)+"\n")
                }
                withStyle(style = SpanStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )){
                    append(stringResource(R.string.api_by))
                }

            }
        )
    }
}
