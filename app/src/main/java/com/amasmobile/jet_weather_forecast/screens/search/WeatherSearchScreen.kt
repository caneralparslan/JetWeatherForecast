package com.amasmobile.jet_weather_forecast.screens.search

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.amasmobile.jet_weather_forecast.components.WeatherAppBar
import com.amasmobile.jet_weather_forecast.navigation.WeatherScreens

@Preview(showBackground = true)
@Composable
fun WeatherSearchScreen(navController: NavController = rememberNavController()){

    Scaffold(
        topBar = {
            WeatherAppBar(
                title = "Search",
                isMainScreen = false,
                navController = navController
                )
        }
    ) {
        padding ->
        SearchBar(paddingValues = padding, navController)
    }
}

@Composable
fun SearchBar(paddingValues: PaddingValues = PaddingValues(10.dp),
                  navController: NavController){

    val searchQueryState = rememberSaveable {
        mutableStateOf("")
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val isValid = remember (searchQueryState.value){
        searchQueryState.value.trim().isNotEmpty()
    }

    Column(
        horizontalAlignment = Alignment.End,
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxHeight()
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 5.dp),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(width = 1.dp, color = Color.LightGray)
        ){
            CommonTextField(valueState = searchQueryState,
                placeholder = "Seattle",
                onAction = KeyboardActions {
                    if(!isValid) return@KeyboardActions

                    navController.navigate(WeatherScreens.MainScreen.name + "/${searchQueryState.value}"){
                        popUpTo(0) { inclusive = true }
                    }
                    searchQueryState.value = ""
                    keyboardController?.hide()
                })
        }

        if(isValid){
            IconButton(
                onClick = {
                    navController.navigate(WeatherScreens.MainScreen.name + "/${searchQueryState.value}"){
                        popUpTo(0) { inclusive = true }
                    }
                    searchQueryState.value = ""
                    keyboardController?.hide()

                }
            ) {
                Row {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        imageVector = Icons.AutoMirrored.Default.ArrowForward,
                        contentDescription = "Forward Arrow")
                    Box(modifier = Modifier.width(15.dp))
                }
            }

        }
    }
}

@Composable
fun CommonTextField(valueState: MutableState<String>,
                    placeholder: String,
                    keyboardType: KeyboardType = KeyboardType.Text,
                    imeAction: ImeAction = ImeAction.Next,
                    onAction: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = {
            changedValue ->
            valueState.value = changedValue
        },
        placeholder = {
            Text(placeholder, style = TextStyle(color = Color.LightGray))
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType, imeAction = imeAction
        ),
        keyboardActions = onAction,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),

    )
}

