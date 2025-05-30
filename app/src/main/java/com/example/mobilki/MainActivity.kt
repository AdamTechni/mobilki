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
import androidx.lifecycle.viewmodel.compose.viewModel
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
                val userViewModel: UserViewModel = viewModel()
                
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "userFormScreen",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("userFormScreen") { 
                            UserFormScreen(navController, userViewModel) 
                        }
                        composable("userInfoScreen") { 
                            UserInfoScreen(navController, userViewModel) 
                        }
                        composable("bmiScreen") { 
                            BMIScreen(navController, userViewModel) 
                        }
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
        val userViewModel: UserViewModel = viewModel()
        
        NavHost(
            navController = navController,
            startDestination = "userFormScreen"
        ) {
            composable("userFormScreen") { 
                UserFormScreen(navController, userViewModel) 
            }
            composable("userInfoScreen") { 
                UserInfoScreen(navController, userViewModel) 
            }
            composable("bmiScreen") { 
                BMIScreen(navController, userViewModel) 
            }
        }
    }
}