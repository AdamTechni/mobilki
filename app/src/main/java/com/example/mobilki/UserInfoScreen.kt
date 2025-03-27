package com.example.mobilki

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.navigation.NavHostController

@Composable
fun UserInfoScreen(navController: NavHostController) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "User Information")
        // Add more UI elements as needed
        Button(onClick = { navController.navigate("screen3") }) {
            Text("Go to BMI Screen")
        }
    }
}