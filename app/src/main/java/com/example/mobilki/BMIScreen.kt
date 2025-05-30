package com.example.mobilki

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlin.math.roundToInt

@Composable
fun BMIScreen(
    navController: NavHostController,
    viewModel: UserViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    var showBMIResult by remember { mutableStateOf(false) }

    // Automatyczne obliczenie BMI gdy dane są dostępne
    LaunchedEffect(uiState.userData) {
        if (uiState.userData.isValid()) {
            showBMIResult = true
        }
    }

    val bmi = if (uiState.userData.isValid()) uiState.userData.calculateBMI() else 0.0
    val bmiCategory = if (uiState.userData.isValid()) uiState.userData.getBMICategory() else ""
    val bmiMessage = if (uiState.userData.isValid()) uiState.userData.getBMIMessage() else ""

    fun getBMIColor(bmi: Double): Color {
        return when {
            bmi < 18.5 -> Color(0xFF2196F3) // Niebieski - niedowaga
            bmi <= 24.9 -> Color(0xFF4CAF50) // Zielony - norma
            bmi <= 29.9 -> Color(0xFFFF9800) // Pomarańczowy - nadwaga
            else -> Color(0xFFE57373) // Czerwony - otyłość
        }
    }

    fun getBMIIcon(bmi: Double): ImageVector {
        return when {
            bmi < 18.5 -> Icons.Default.TrendingDown
            bmi <= 24.9 -> Icons.Default.Favorite
            else -> Icons.Default.TrendingUp
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        getBMIColor(bmi).copy(alpha = 0.1f),
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
                    containerColor = getBMIColor(bmi).copy(alpha = 0.2f)
                ),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Calculate,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = getBMIColor(bmi)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Kalkulator BMI",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = getBMIColor(bmi)
                    )
                    Text(
                        text = "Analiza wskaźnika masy ciała",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center
                    )
                }
            }

            if (uiState.userData.isValid()) {
                // Dane użytkownika
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Dane do obliczeń",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            BMIDataCard(
                                label = "Wzrost",
                                value = "${uiState.userData.height} cm",
                                color = MaterialTheme.colorScheme.primary
                            )
                            BMIDataCard(
                                label = "Waga",
                                value = "${uiState.userData.weight} kg",
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }
                }

                // Wynik BMI
                AnimatedVisibility(
                    visible = showBMIResult,
                    enter = fadeIn(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    ) + scaleIn(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = getBMIColor(bmi).copy(alpha = 0.1f)
                        ),
                        elevation = CardDefaults.cardElevation(8.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Ikona BMI w kółku
                            Box(
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CircleShape)
                                    .background(getBMIColor(bmi).copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = getBMIIcon(bmi),
                                    contentDescription = null,
                                    modifier = Modifier.size(40.dp),
                                    tint = getBMIColor(bmi)
                                )
                            }

                            // Wartość BMI
                            Text(
                                text = String.format("%.1f", bmi),
                                style = MaterialTheme.typography.displayLarge,
                                fontWeight = FontWeight.Bold,
                                color = getBMIColor(bmi),
                                fontSize = 48.sp
                            )

                            // Kategoria BMI
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = getBMIColor(bmi).copy(alpha = 0.2f)
                                )
                            ) {
                                Text(
                                    text = bmiCategory.uppercase(),
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = getBMIColor(bmi)
                                )
                            }
                        }
                    }
                }

                // Komunikat personalny
                AnimatedVisibility(
                    visible = showBMIResult,
                    enter = fadeIn(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        ),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Text(
                            text = bmiMessage,
                            modifier = Modifier.padding(20.dp),
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center,
                            lineHeight = 28.sp
                        )
                    }
                }

                // Skala BMI
                AnimatedVisibility(
                    visible = showBMIResult,
                    enter = fadeIn(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "Skala BMI",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )

                            BMIRangeRow("Poniżej 18.5", "Niedowaga", Color(0xFF2196F3), bmi < 18.5)
                            BMIRangeRow("18.5 - 24.9", "Norma", Color(0xFF4CAF50), bmi in 18.5..24.9)
                            BMIRangeRow("25.0 - 29.9", "Nadwaga", Color(0xFFFF9800), bmi in 25.0..29.9)
                            BMIRangeRow("30.0+", "Otyłość", Color(0xFFE57373), bmi >= 30.0)
                        }
                    }
                }
            } else {
                // Brak danych
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    ),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Brak danych",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                        Text(
                            text = "Aby obliczyć BMI, musisz najpierw wypełnić formularz z danymi osobistymi.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            textAlign = TextAlign.Center
                        )
                        Button(
                            onClick = { navController.navigate("userFormScreen") }
                        ) {
                            Text("Przejdź do formularza")
                        }
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
                        onClick = { navController.navigate("userFormScreen") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Powrót do formularza", fontSize = 16.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun BMIDataCard(label: String, value: String, color: Color) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.1f)
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = color,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                color = color,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun BMIRangeRow(range: String, category: String, color: Color, isCurrentRange: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(
                if (isCurrentRange) 
                    color.copy(alpha = 0.2f) 
                else 
                    MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
            )
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = range,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (isCurrentRange) FontWeight.Bold else FontWeight.Medium,
            color = if (isCurrentRange) color else MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = category,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (isCurrentRange) FontWeight.Bold else FontWeight.Medium,
            color = if (isCurrentRange) color else MaterialTheme.colorScheme.onSurface
        )
        if (isCurrentRange) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}