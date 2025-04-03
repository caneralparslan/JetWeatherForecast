package com.amasmobile.jet_weather_forecast.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.amasmobile.jet_weather_forecast.screens.about.WeatherAboutScreen
import com.amasmobile.jet_weather_forecast.screens.favorite.WeatherFavoriteScreen
import com.amasmobile.jet_weather_forecast.screens.main.MainViewModel
import com.amasmobile.jet_weather_forecast.screens.main.WeatherMainScreen
import com.amasmobile.jet_weather_forecast.screens.search.WeatherSearchScreen
import com.amasmobile.jet_weather_forecast.screens.settings.WeatherSettingsScreen
import com.amasmobile.jet_weather_forecast.screens.splash.WeatherSplashScreen


@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = WeatherScreens.SplashScreen.name) {
        composable(route = WeatherScreens.SplashScreen.name){
            WeatherSplashScreen(navController = navController)
        }

        val mainRoute = WeatherScreens.MainScreen.name
        composable(route = "$mainRoute/{city}",
            arguments = listOf(navArgument(name = "city"){
                type = NavType.StringType
            })){
            navBack ->
            navBack.arguments?.getString("city").let {
                city ->
                val mainViewModel = hiltViewModel<MainViewModel>()
                WeatherMainScreen(navController = navController, mainViewModel, city = city)
            }
        }
        composable(route = WeatherScreens.SearchScreen.name){
            WeatherSearchScreen(navController = navController)
        }
        composable(route = WeatherScreens.SettingScreen.name){
            WeatherSettingsScreen(navController = navController)
        }
        composable(route = WeatherScreens.AboutScreen.name){
            WeatherAboutScreen(navController = navController)
        }
        composable(route = WeatherScreens.FavoriteScreen.name){
            WeatherFavoriteScreen(navController = navController)
        }
    }
}