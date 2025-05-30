package com.example.mobilki

data class UserData(
    val firstName: String = "",
    val lastName: String = "",
    val age: String = "",
    val height: String = "",
    val weight: String = "",
    val gender: String = ""
) {
    fun isValid(): Boolean {
        return firstName.isNotBlank() && 
               lastName.isNotBlank() && 
               age.toIntOrNull() != null && 
               height.toIntOrNull() != null && 
               weight.toIntOrNull() != null && 
               gender.isNotBlank()
    }

    fun getHeightInMeters(): Double {
        return (height.toIntOrNull() ?: 0) / 100.0
    }

    fun getWeightInKg(): Double {
        return weight.toDoubleOrNull() ?: 0.0
    }

    fun calculateBMI(): Double {
        val heightM = getHeightInMeters()
        val weightKg = getWeightInKg()
        return if (heightM > 0) weightKg / (heightM * heightM) else 0.0
    }

    fun getBMICategory(): String {
        val bmi = calculateBMI()
        return when {
            bmi < 18.5 -> "poniżej normy"
            bmi <= 24.9 -> "w świetnej formie"
            else -> "powyżej normy"
        }
    }

    fun getBMIMessage(): String {
        val bmi = String.format("%.1f", calculateBMI())
        return when {
            calculateBMI() < 18.5 -> "Hej $firstName, jesteś poniżej normy. Twoje BMI wynosi $bmi."
            calculateBMI() <= 24.9 -> "Hej $firstName, jesteś w świetnej formie! Twoje BMI wynosi $bmi."
            else -> "Hej $firstName, Twoje BMI wynosi $bmi, warto zadbać o zdrowie!"
        }
    }
}

data class GenderApiResponse(
    val name: String,
    val gender: String,
    val probability: Double,
    val count: Int
) 