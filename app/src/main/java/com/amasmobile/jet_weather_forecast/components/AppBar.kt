package com.amasmobile.jet_weather_forecast.components

import android.icu.text.CaseMap.Title
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.amasmobile.jet_weather_forecast.navigation.WeatherScreens


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAppBar(
    title: String,
    isMainScreen: Boolean = true,
    navController: NavController,
    onSearchClicked: () -> Unit = {},
){

    val isMoreDialogShown = remember {
        mutableStateOf(false)
    }

    if(isMoreDialogShown.value){
        ShowSettingDropDownMenu(
            navController = navController,
            isMoreDialogShown = isMoreDialogShown)
    }

    TopAppBar(
        scrollBehavior = null,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(title,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
        actions = {
            if (isMainScreen) {
                IconButton(
                    onClick = {
                        onSearchClicked.invoke()
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon"
                    )
                }

                IconButton(
                    onClick = {
                        Log.d("isShown", "WeatherAppBar: ${isMoreDialogShown.value}")
                        isMoreDialogShown.value = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.MoreVert,
                        contentDescription = "More Icon"
                    )
                }
            }
            else{
                Box{}
            }
        },
        navigationIcon = {
            if(!isMainScreen){
                Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = "",
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    })
            }
        }
    )
}

@Composable
fun ShowSettingDropDownMenu(navController: NavController,
                            isMoreDialogShown: MutableState<Boolean>) {

    var expanded by remember { mutableStateOf(true) }
    val items = listOf("Favorites", "About", "Settings")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .padding(horizontal = 10.dp),
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
                isMoreDialogShown.value = false
            }
        ) {
            items.forEachIndexed { _, text ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        isMoreDialogShown.value = false
                    },
                    text =
                    {
                        val menuItems = mapOf(
                            "Favorites" to (Icons.Default.FavoriteBorder to WeatherScreens.FavoriteScreen.name),
                            "About" to (Icons.Default.Info to WeatherScreens.AboutScreen.name),
                            "Settings" to (Icons.Default.Settings to WeatherScreens.SettingScreen.name)
                        )

                        menuItems[text]?.let { (icon, screenName) ->
                            Row(
                                modifier = Modifier.clickable {
                                    navController.navigate(screenName)
                                    isMoreDialogShown.value = false
                                }
                            ) {
                                Icon(imageVector = icon, contentDescription = text)
                                Text(" $text")
                            }
                        }
                    }
                )
            }
        }
    }

}
