package com.example.mobilki

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserFormScreen(
    navController: NavHostController,
    viewModel: UserViewModel = viewModel()
) {
    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var age by rememberSaveable { mutableStateOf("") }
    var height by rememberSaveable { mutableStateOf("") }
    var weight by rememberSaveable { mutableStateOf("") }
    var selectedGender by rememberSaveable { mutableStateOf("") }
    var showValidationErrors by rememberSaveable { mutableStateOf(false) }

    val scrollState = rememberScrollState()
    val genderOptions = listOf("Mężczyzna", "Kobieta", "Inne")

    // Funkcja walidacji
    fun isFormValid(): Boolean {
        return firstName.isNotBlank() && 
               lastName.isNotBlank() && 
               age.toIntOrNull() != null && age.toInt() > 0 &&
               height.toIntOrNull() != null && height.toInt() > 0 &&
               weight.toDoubleOrNull() != null && weight.toDouble() > 0 &&
               selectedGender.isNotBlank()
    }

    fun saveUserData() {
        val userData = UserData(
            firstName = firstName,
            lastName = lastName,
            age = age,
            height = height,
            weight = weight,
            gender = selectedGender
        )
        viewModel.updateUserData(userData)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        MaterialTheme.colorScheme.background
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Nagłówek
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Dane użytkownika",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "Wypełnij wszystkie pola aby kontynuować",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Formularz
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Imię
                    OutlinedTextField(
                        value = firstName,
                        onValueChange = { firstName = it },
                        label = { Text("Imię") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        isError = showValidationErrors && firstName.isBlank(),
                        supportingText = {
                            if (showValidationErrors && firstName.isBlank()) {
                                Text("Imię jest wymagane", color = MaterialTheme.colorScheme.error)
                            }
                        }
                    )

                    // Nazwisko
                    OutlinedTextField(
                        value = lastName,
                        onValueChange = { lastName = it },
                        label = { Text("Nazwisko") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        isError = showValidationErrors && lastName.isBlank(),
                        supportingText = {
                            if (showValidationErrors && lastName.isBlank()) {
                                Text("Nazwisko jest wymagane", color = MaterialTheme.colorScheme.error)
                            }
                        }
                    )

                    // Wiek
                    OutlinedTextField(
                        value = age,
                        onValueChange = { if (it.all { char -> char.isDigit() }) age = it },
                        label = { Text("Wiek") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = showValidationErrors && (age.toIntOrNull() == null || age.toInt() <= 0),
                        supportingText = {
                            if (showValidationErrors && (age.toIntOrNull() == null || age.toInt() <= 0)) {
                                Text("Podaj prawidłowy wiek", color = MaterialTheme.colorScheme.error)
                            }
                        }
                    )

                    // Wzrost
                    OutlinedTextField(
                        value = height,
                        onValueChange = { if (it.all { char -> char.isDigit() }) height = it },
                        label = { Text("Wzrost (cm)") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = showValidationErrors && (height.toIntOrNull() == null || height.toInt() <= 0),
                        supportingText = {
                            if (showValidationErrors && (height.toIntOrNull() == null || height.toInt() <= 0)) {
                                Text("Podaj prawidłowy wzrost", color = MaterialTheme.colorScheme.error)
                            }
                        }
                    )

                    // Waga
                    OutlinedTextField(
                        value = weight,
                        onValueChange = { 
                            if (it.isEmpty() || it.matches(Regex("^\\d+\\.?\\d*$"))) {
                                weight = it
                            }
                        },
                        label = { Text("Waga (kg)") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        isError = showValidationErrors && (weight.toDoubleOrNull() == null || weight.toDouble() <= 0),
                        supportingText = {
                            if (showValidationErrors && (weight.toDoubleOrNull() == null || weight.toDouble() <= 0)) {
                                Text("Podaj prawidłową wagę", color = MaterialTheme.colorScheme.error)
                            }
                        }
                    )

                    // Płeć
                    Text(
                        text = "Płeć",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                    
                    genderOptions.forEach { option ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .selectable(
                                    selected = selectedGender == option,
                                    onClick = { selectedGender = option }
                                )
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedGender == option,
                                onClick = { selectedGender = option }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = option)
                        }
                    }
                    
                    if (showValidationErrors && selectedGender.isBlank()) {
                        Text(
                            "Wybierz płeć",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            // Przyciski nawigacji
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = {
                            if (isFormValid()) {
                                saveUserData()
                                navController.navigate("userInfoScreen")
                            } else {
                                showValidationErrors = true
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        enabled = isFormValid()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Przejdź do ekranu 2", fontSize = 16.sp)
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    OutlinedButton(
                        onClick = {
                            if (isFormValid()) {
                                saveUserData()
                                navController.navigate("bmiScreen")
                            } else {
                                showValidationErrors = true
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        enabled = isFormValid()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Calculate,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Przejdź do ekranu 3", fontSize = 16.sp)
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}