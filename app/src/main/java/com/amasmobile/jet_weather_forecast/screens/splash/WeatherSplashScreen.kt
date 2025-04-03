package com.amasmobile.jet_weather_forecast.screens.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.amasmobile.jet_weather_forecast.R
import com.amasmobile.jet_weather_forecast.navigation.WeatherScreens
import kotlinx.coroutines.delay


@Composable
fun WeatherSplashScreen(navController: NavController){

    val defaultCity = "Seattle"
    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }

    LaunchedEffect(key1 = true, block = {
        scale.animateTo(targetValue = 0.9f,
            animationSpec = tween(
                durationMillis = 1200,
                easing = {
                    OvershootInterpolator(5f)
                        .getInterpolation(it)
                }))
        delay(1000).apply {
            navController.navigate(WeatherScreens.MainScreen.name + "/$defaultCity"){
                popUpTo(0) { inclusive = true }
            }
        }
    })



    Box (
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Surface(
            modifier = Modifier.size(330.dp).scale(scale.value),
            color = Color.White,
            shape = CircleShape,
            border = BorderStroke(width = 1.dp, color = Color.LightGray),
        ) {

            Column(
                modifier = Modifier.padding(1.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(modifier = Modifier.size(95.dp), painter = painterResource(R.drawable.sun), contentDescription = "Sunny Icon")
                Text("Find the Sun?",
                    style = TextStyle(color = Color.LightGray,
                        fontSize = 20.sp)
                )
            }
        }
    }
}