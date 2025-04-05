package com.amasmobile.jet_weather_forecast.screens.favorite

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.amasmobile.jet_weather_forecast.components.WeatherAppBar
import com.amasmobile.jet_weather_forecast.models.Favorite
import com.amasmobile.jet_weather_forecast.navigation.WeatherScreens
import com.amasmobile.jet_weather_forecast.screens.about.AboutContent


@Composable
fun WeatherFavoriteScreen(navController: NavController,
                          favoriteViewModel: FavoriteViewModel = hiltViewModel()){

    Scaffold(
        topBar = {
            WeatherAppBar(
                title = "Favorites",
                isMainScreen = false,
                navController = navController
            )
        }
    ) {
            padding ->
        FavoriteContent(paddingValues = padding,
            favoriteViewModel, navController)
    }

}

@Composable
fun FavoriteContent(paddingValues: PaddingValues,
                    favoriteViewModel: FavoriteViewModel,
                    navController: NavController) {

    Surface(
        modifier = Modifier.fillMaxSize().padding(paddingValues)
    ) {
        val favList = favoriteViewModel.favList.collectAsState().value

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(favList){
                favItem ->
                    FavItemTile(favItem, favoriteViewModel, navController)
            }
        }
    }
}

@Composable
fun FavItemTile(favItem: Favorite,
                favoriteViewModel: FavoriteViewModel,
                navController: NavController){

    val context = LocalContext.current

    Surface(
        modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp)
            .clickable {
                navController.navigate(WeatherScreens.MainScreen.name + "/${favItem.city}"){
                    popUpTo(0) { inclusive = true }
                }
            },
        shape = RoundedCornerShape(25.dp),
        color = Color(0xCC7FC4C4)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 50.dp, vertical = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(favItem.city)
            Surface(
                shape = CircleShape,
                color = Color(0x99E3E3E3)
            ) {
                Text(favItem.country, modifier = Modifier.padding(5.dp))
            }
            IconButton(
                onClick = {
                    favoriteViewModel.removeFavorite(favItem)
                    showToast(context, favItem.city)
                }
            ) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Icon",
                    tint = Color.Red)
            }
        }
    }
}

fun showToast(context: Context, favCity: String){
    Toast.makeText(context, "$favCity Removed From Favorites", Toast.LENGTH_SHORT).show()
}