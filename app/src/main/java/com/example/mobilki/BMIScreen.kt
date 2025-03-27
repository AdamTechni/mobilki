package com.example.mobilki

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.navigation.NavHostController

@Composable
fun BMIScreen(navController: NavHostController) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "BMI Calculator")
        TextField(
            value = "",
            onValueChange = {},
            label = { Text("Height (cm)") }
        )
        TextField(
            value = "",
            onValueChange = {},
            label = { Text("Weight (kg)") }
        )
        Button(onClick = { /* Calculate BMI */ }) {
            Text("Calculate")
        }
    }
}