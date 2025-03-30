package com.amasmobile.jet_weather_forecast.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.amasmobile.jet_weather_forecast.R
import com.amasmobile.jet_weather_forecast.components.WeatherAppBar
import com.amasmobile.jet_weather_forecast.data.DataOrException
import com.amasmobile.jet_weather_forecast.models.Weather
import com.amasmobile.jet_weather_forecast.models.WeatherItem
import com.amasmobile.jet_weather_forecast.util.formatDate
import com.amasmobile.jet_weather_forecast.util.formatDateTime


@Composable
fun WeatherMainScreen(navController: NavController,
                      mainViewModel: MainViewModel = hiltViewModel()){

    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>> (
        initialValue = DataOrException(loading = true)){
        value = mainViewModel.getWeatherData("izmir")
    }.value

    if (weatherData.loading == true){
        CircularProgressIndicator()
    }
    else if (weatherData.data != null){
        MainScaffold(weatherData.data, navController)
    }
}


@Composable
fun MainScaffold(weather: Weather, navController: NavController){
    Scaffold(
        topBar = {
            Column {
                WeatherAppBar(
                    title = weather.city.name + ", ${weather.city.country}",
                    navController = navController
                )
                HorizontalDivider()
            }
        }
    ) {
        padding ->
        MainContent(padding, weather = weather)
    }
}

@Composable
fun MainContent(padding: PaddingValues, weather: Weather){

    val todayWeather = weather.list[0]

    Column(
        modifier = Modifier.padding(padding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        //Today's Date
        Text(
            formatDate(todayWeather.dt),
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
            ),
            modifier = Modifier.padding(10.dp)
        )
        //Today's Weather
        TodayCircle(todayWeather)

        //Today's Details
        HumidityPressureWind(todayWeather)

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 5.dp, vertical = 10.dp),
            thickness = 1.dp,
            color = Color.LightGray)

        SunriseSunset(todayWeather)

        Text(
            modifier = Modifier.padding(vertical = 10.dp),
            text = "This Week",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp))

        WeeklyWeather(weather)
    }
}

@Composable
fun WeeklyWeather(weather: Weather){
    LazyColumn {
        items(items = weather.list.subList(1, weather.list.size)){
            item ->
            val imageUrl = "https://openweathermap.org/img/wn/${item.weather[0].icon}.png"
            Card(
                modifier = Modifier.padding(5.dp).fillMaxWidth().height(70.dp),
                shape = RoundedCornerShape(24.dp),
            ) {
                Row (
                    modifier = Modifier.fillMaxWidth().padding(5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = formatDate(item.dt).split(",")[0],
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium))
                    WeatherImage(imageUrl)
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFAAE43))
                    ) {
                        Text(
                            modifier = Modifier.padding(5.dp),
                            text = item.weather[0].description)
                    }
                    Row {
                        Text("${item.temp.day.toInt()}°",
                            style = TextStyle(
                                color = Color.Blue,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold))
                        Text("${item.temp.night.toInt()}°",
                            style = TextStyle(
                                color = Color.LightGray,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold))
                    }
                }
            }
        }
    }
}

@Composable
fun TodayCircle(todayWeather: WeatherItem){
    val imageUrl = "https://openweathermap.org/img/wn/${todayWeather.weather[0].icon}.png"

    Surface(
        modifier = Modifier.padding(5.dp).size(200.dp),
        shape = CircleShape,
        color = Color(0xFFFAAE43)
    ) {
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            WeatherImage(imageUrl)
            Text(" ${todayWeather.temp.day.toInt()}°",
                style = TextStyle(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Text(
                todayWeather.weather[0].main,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    fontStyle = FontStyle.Italic
                ))
        }
    }
}

@Composable
fun HumidityPressureWind(todayWeather: WeatherItem){
    Row (
        modifier = Modifier.padding(10.dp).height(20.dp).fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Row( verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(R.drawable.humidity), contentDescription = "Humidity Icon")
            Text(" ${todayWeather.humidity}%",
                style = TextStyle(fontSize = 14.sp))
        }

        Row( verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(R.drawable.pressure), contentDescription = "Humidity Icon")
            Text(" ${todayWeather.pressure} psi",
                style = TextStyle(fontSize = 14.sp))
        }

        Row( verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(R.drawable.wind), contentDescription = "Humidity Icon")
            Text(" ${todayWeather.humidity} mph",
                style = TextStyle(fontSize = 14.sp))
        }
    }
}

@Composable
fun SunriseSunset(todayWeather: WeatherItem){
    Row (
        modifier = Modifier.padding(10.dp).height(20.dp).fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Row( verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(R.drawable.sunrise), contentDescription = "Humidity Icon")
            Text(formatDateTime(todayWeather.sunrise),
                style = TextStyle(fontSize = 14.sp))
        }

        Row( verticalAlignment = Alignment.CenterVertically) {
            Text(formatDateTime(todayWeather.sunset),
                style = TextStyle(fontSize = 14.sp))
            Icon(painter = painterResource(R.drawable.sunset), contentDescription = "Humidity Icon")
        }
    }

}

@Composable
fun WeatherImage(imageUrl: String){
    Image(
        modifier = Modifier.size(90.dp).padding(bottom = 10.dp),
        painter = rememberAsyncImagePainter(imageUrl),
        contentDescription = "Weather Image"
    )
}