package com.nexus.app.features.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexus.app.core.data.local.UserPreferencesDataStore
import com.nexus.app.core.data.local.UserPrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileUiState(
    val displayName: String = "Explorer",
    val fanLevel: Int = 1,
    val xpPoints: Int = 0,
    val streakDays: Int = 0,
    val publishers: List<String> = emptyList(),
    val mood: String = "",
    val completedQuizzes: Int = 0,
    val isLoading: Boolean = false
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val prefs: UserPreferencesDataStore
) : ViewModel() {
    private val _state = MutableStateFlow(ProfileUiState())
    val state: StateFlow<ProfileUiState> = _state

    init { load() }

    private fun load() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val profile = prefs.getUserProfile()
            _state.value = ProfileUiState(
                displayName = profile.displayName,
                fanLevel = profile.fanLevel,
                xpPoints = profile.xpPoints,
                streakDays = profile.streakDays,
                publishers = profile.publishers,
                mood = profile.mood,
                isLoading = false
            )
        }
    }
}
