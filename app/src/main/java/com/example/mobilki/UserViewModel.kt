package com.example.mobilki

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class UiState(
    val userData: UserData = UserData(),
    val genderApiResponse: GenderApiResponse? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class UserViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun updateUserData(userData: UserData) {
        _uiState.value = _uiState.value.copy(userData = userData)
    }

    fun fetchGenderInfo(firstName: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            
            try {
                val response = ApiClient.genderApiService.getGenderByName(firstName)
                if (response.isSuccessful) {
                    _uiState.value = _uiState.value.copy(
                        genderApiResponse = response.body(),
                        isLoading = false
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "Nie udało się pobrać danych z API"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Błąd połączenia: ${e.message}"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
} 