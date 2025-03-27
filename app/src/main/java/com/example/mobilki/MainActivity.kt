package com.example.mobilki

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobilki.ui.theme.MobilkiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobilkiTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "userFormScreen",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("userFormScreen") { UserFormScreen(navController) }
                        composable("userInfoScreen") { UserInfoScreen(navController) }
                        composable("bmiScreen") { BMIScreen(navController) }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MobilkiTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = "userFormScreen"
        ) {
            composable("userFormScreen") { UserFormScreen(navController) }
            composable("userInfoScreen") { UserInfoScreen(navController) }
            composable("bmiScreen") { BMIScreen(navController) }
        }
    }
}