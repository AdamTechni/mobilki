package com.example.mobilki

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "screen1") {
        composable("screen1") { UserFormScreen(navController) }
        composable("screen2") { UserInfoScreen(navController) }
        composable("screen3") { BMIScreen(navController) }
    }
}