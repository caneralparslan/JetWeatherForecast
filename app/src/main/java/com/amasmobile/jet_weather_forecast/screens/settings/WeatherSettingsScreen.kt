package com.amasmobile.jet_weather_forecast.screens.settings

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.amasmobile.jet_weather_forecast.components.WeatherAppBar
import com.amasmobile.jet_weather_forecast.models.Unit


@Composable
fun WeatherSettingsScreen(navController: NavController,
                          settingsViewModel: SettingsViewModel = hiltViewModel()){

    Scaffold(
        topBar = {
            WeatherAppBar(
                title = "Settings",
                isMainScreen = false,
                navController = navController
            )
        }
    ) {
            padding ->
        SettingsContent(paddingValues = padding,
            settingsViewModel = settingsViewModel)
    }

}

@Composable
fun SettingsContent(paddingValues: PaddingValues,
                    settingsViewModel: SettingsViewModel) {

    val context = LocalContext.current

    val measurementUnits = listOf("Imperial °F", "Metric °C")

    val unitFromDb = settingsViewModel.unitList.collectAsState().value
    val defaultUnit = if (unitFromDb.isEmpty()) measurementUnits[1] else unitFromDb[0].unit
    var choiceState by remember(unitFromDb) { mutableStateOf(defaultUnit) }

    var unitToggleState by remember(unitFromDb) {
        mutableStateOf(defaultUnit == measurementUnits[0])
    }


    Surface (
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ){
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text("Change Units of Measurement",
                modifier = Modifier.padding(bottom = 10.dp))

            IconToggleButton(
                modifier = Modifier.fillMaxWidth(),
                checked = !unitToggleState,
                onCheckedChange = {
                    unitToggleState = !it

                    choiceState = if(unitToggleState){
                        measurementUnits[0]
                    } else {
                        measurementUnits[1]
                    }
                }
            ) {
                Surface (
                    modifier = Modifier
                        .fillMaxWidth(.5f)
                        .padding(top = 10.dp),
                    shape = RectangleShape,
                    color = Color.Magenta.copy(alpha = 0.6f)
                ){
                    Text(choiceState,
                        textAlign = TextAlign.Center)
                }
            }

            ElevatedButton(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF894A)
                ),
                onClick = {
                    settingsViewModel.deleteAllUnits()
                    settingsViewModel.addUnit(Unit(unit = choiceState))
                    Toast.makeText(context, "$choiceState Saved", Toast.LENGTH_SHORT).show()
                }
            ) {
                Text("Save",
                    color = Color.White)
            }
        }
    }
}